package com.example.anjeonRefactoring.Audio.service;

import com.example.anjeonRefactoring.Audio.domain.AudioAnalysis;
import com.example.anjeonRefactoring.Audio.domain.Zone;
import com.example.anjeonRefactoring.Audio.repository.AudioRepository;
import com.example.anjeonRefactoring.Audio.repository.ZoneRepository;
import com.example.anjeonRefactoring.Audio.dto.EmergencyDecibelResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.entity.mime.MultipartEntityBuilder;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AudioService {

    private final AudioRepository audioRepository;
    private final ZoneRepository zoneRepositoty;
    private static final String DJANGO_API_URL = "http://localhost:8000/api/upload/";

    @Transactional
    public EmergencyDecibelResponseDTO analyzeAudio(MultipartFile file, double decibel, Long workerZone) throws Exception {
        System.out.println(workerZone);
        Optional<Zone> zone = zoneRepositoty.findById(workerZone);
        zone.get().setThreshold(zone.get().getThreshold() + 1);

        // 파일 저장 (WebM 파일 변환 로직 포함)
        Path tempFile = Files.createTempFile("audio_", file.getOriginalFilename());
        Files.copy(file.getInputStream(), tempFile, StandardCopyOption.REPLACE_EXISTING);
        File processedFile = tempFile.toFile();

        // WebM 파일이면 MP3로 변환
        if (file.getOriginalFilename().endsWith(".webm")) {
            File mp3File = convertWebmToMp3(processedFile);
            if (mp3File != null) {
                processedFile = mp3File; // 변환된 MP3 파일로 변경
            } else {
                throw new RuntimeException("WebM 변환 실패");
            }
        }

        // Django 서버로 파일 전송 및 응답 수신
        JSONObject responseJson = sendToDjangoServer(processedFile);

        // JSON 데이터를 Java 객체로 변환 (Jackson 사용)
        ObjectMapper objectMapper = new ObjectMapper();
        TypeFactory typeFactory = objectMapper.getTypeFactory();
        List<String> detectedKeywords = objectMapper.readValue(
                responseJson.getJSONArray("detected_keywords").toString(),
                typeFactory.constructCollectionType(List.class, String.class)
        );

        if (detectedKeywords == null) {
            detectedKeywords = new ArrayList<>();
        }

        // 결과 저장
        AudioAnalysis audioAnalysis = AudioAnalysis.builder()
                .zoneId(workerZone)
                .fileName(processedFile.getName())
                .soundClass(responseJson.optString("sound_class", "Unknown")) // 예외 방지
                .transcription(responseJson.optString("transcription", ""))
                .detectedKeywords(detectedKeywords)
                .decibel(decibel)
                .createTime(LocalDateTime.now())
                .build();

        audioRepository.save(audioAnalysis);

        EmergencyDecibelResponseDTO responseDTO = new EmergencyDecibelResponseDTO(
                audioAnalysis.getCreateTime(),
                audioAnalysis.getZoneId(),
                audioAnalysis.getDecibel(),
                audioAnalysis.getSoundClass(),
                audioAnalysis.getTranscription());

        // 변환된 파일 삭제 (임시 파일 정리)
        Files.deleteIfExists(tempFile);
        if (processedFile != null && processedFile.exists()) {
            processedFile.delete();
        }

        return responseDTO;
    }

    private File convertWebmToMp3(File webmFile) {
        // 출력 파일명: 확장자를 .mp3로 변경 (대소문자 구분 없이)
        File mp3File = new File(webmFile.getAbsolutePath().replaceAll("(?i)\\.webm$", ".mp3"));

        // 1. ffprobe로 오디오 스트림 여부 확인
        boolean hasAudio = false;
        try {
            ProcessBuilder pbProbe = new ProcessBuilder(
                    "ffprobe",
                    "-v", "error",
                    "-select_streams", "a",
                    "-show_entries", "stream=codec_type",
                    "-of", "csv=p=0",
                    webmFile.getAbsolutePath()
            );
            Process probeProcess = pbProbe.start();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(probeProcess.getInputStream()))) {
                String line = reader.readLine();
                if (line != null && !line.trim().isEmpty()) {
                    hasAudio = true;
                }
            }
            probeProcess.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
            // 오류 발생 시 오디오 스트림이 없는 것으로 간주
        }

        // 2. 변환 명령어 구성
        String[] command;
        if (hasAudio) {
            // 오디오 스트림이 있을 경우: 일반 변환 명령어 사용
            command = new String[] {
                    "ffmpeg",
                    "-i", webmFile.getAbsolutePath(),
                    "-vn",                           // 영상 스트림 무시
                    "-ar", "44100",                  // 샘플링 레이트 44100Hz
                    "-ac", "2",                      // 2채널(스테레오)
                    "-b:a", "192k",                  // 오디오 비트레이트 192kbps
                    "-y",                            // 기존 파일 덮어쓰기
                    mp3File.getAbsolutePath()
            };
        } else {
            // 오디오 스트림이 없을 경우: 무음(silent) MP3를 생성

            // 2-1. ffprobe로 파일 길이(duration) 가져오기
            String duration = "0";
            try {
                ProcessBuilder pbDuration = new ProcessBuilder(
                        "ffprobe",
                        "-v", "error",
                        "-show_entries", "format=duration",
                        "-of", "default=noprint_wrappers=1:nokey=1",
                        webmFile.getAbsolutePath()
                );
                Process durationProcess = pbDuration.start();
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(durationProcess.getInputStream()))) {
                    duration = reader.readLine();
                }
                durationProcess.waitFor();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (duration == null || duration.trim().isEmpty()) {
                duration = "0";
            }

            // 2-2. 무음 MP3 생성 명령어: anullsrc로 무음 오디오 생성
            command = new String[] {
                    "ffmpeg",
                    "-f", "lavfi",
                    "-i", "anullsrc=r=44100:cl=stereo",
                    "-t", duration,                  // 입력 파일의 길이만큼 무음 생성
                    "-c:a", "libmp3lame",
                    "-b:a", "192k",
                    "-y",
                    mp3File.getAbsolutePath()
            };
        }

        // 3. ffmpeg 변환 실행 및 로그 출력
        try {
            ProcessBuilder pbConvert = new ProcessBuilder(command);
            pbConvert.redirectErrorStream(true); // stderr와 stdout을 합침
            Process convertProcess = pbConvert.start();

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(convertProcess.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
            }

            int exitCode = convertProcess.waitFor();
            if (exitCode != 0) {
                System.err.println("❌ ffmpeg 변환 실패. 종료 코드: " + exitCode);
                return null;
            }

            if (mp3File.exists()) {
                System.out.println("✅ WebM → MP3 변환 성공: " + mp3File.getAbsolutePath());
                return mp3File;
            } else {
                System.err.println("❌ 변환 후 MP3 파일이 생성되지 않음");
                return null;
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    private JSONObject sendToDjangoServer(File file) throws Exception {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost uploadRequest = new HttpPost(DJANGO_API_URL);
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.addBinaryBody("file", file);
            uploadRequest.setEntity(builder.build());

            try (CloseableHttpResponse response = httpClient.execute(uploadRequest)) {
                HttpEntity entity = response.getEntity();
                String jsonResponse = EntityUtils.toString(entity);

                System.out.println("💡 Django 응답: " + jsonResponse);

                return new JSONObject(jsonResponse);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Django 서버 통신 중 오류 발생: " + e.getMessage());
        }
    }
}
