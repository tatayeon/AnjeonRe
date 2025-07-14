package com.example.anjeonRefactoring.Audio.controller;

import com.example.anjeonRefactoring.Audio.dto.EmergencyDecibelResponseDTO;
import com.example.anjeonRefactoring.Audio.service.AudioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@Slf4j
@RestController
@RequestMapping("/audio")
@RequiredArgsConstructor
public class AudioContoller {

    private final AudioService audioService;

    @PostMapping("/upload")
    public ResponseEntity<EmergencyDecibelResponseDTO> uploadAudio(
            @RequestParam("file") MultipartFile file,
            @RequestParam("decibel") double decibel,
            @RequestParam("zoneId") Long zoneId
    ) {
        log.info("class: {}", file.getClass());
        log.info("content type: {}", file.getContentType());
        log.info("original file name: {}", file.getOriginalFilename());
        log.info("resource: {}", file.getResource());
        try {
            EmergencyDecibelResponseDTO result = audioService.analyzeAudio(file, decibel, zoneId);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

}
