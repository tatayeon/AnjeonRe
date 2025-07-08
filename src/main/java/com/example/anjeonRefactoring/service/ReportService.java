package com.example.anjeonRefactoring.service;

import com.example.anjeonRefactoring.domain.Report;
import com.example.anjeonRefactoring.domain.ReportTagMap;
import com.example.anjeonRefactoring.domain.Tag;
import com.example.anjeonRefactoring.domain.User;
import com.example.anjeonRefactoring.domain.enumration.ReportState;
import com.example.anjeonRefactoring.exception.CustomException;
import com.example.anjeonRefactoring.repository.ReportRepository;
import com.example.anjeonRefactoring.repository.ReportTagMapRepository;
import com.example.anjeonRefactoring.repository.TagRepository;
import com.example.anjeonRefactoring.repository.UserRepository;
import com.example.anjeonRefactoring.repuestDto.CreateReportDto;
import com.example.anjeonRefactoring.responsDto.ShowDetailReportDto;
import com.example.anjeonRefactoring.responsDto.ShowMonthlyReportDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.anjeonRefactoring.domain.enumration.ReportState.BEFORE;
import static com.example.anjeonRefactoring.domain.enumration.ReportState.AFTER;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReportService {

    private final ReportRepository reportRepository;
    private final UserRepository userRepository;
    private final TagRepository tagRepository;
    private final ReportTagMapRepository reportTagMapRepository;

    @Transactional
    public String createReport(CreateReportDto dto, String userName) {
        User user = userRepository.findById(Long.parseLong(userName)).orElseThrow(
                () -> new CustomException("User not found", HttpStatus.NOT_FOUND)
        );

        Report report = Report.builder()
                .createZoneId(dto.getCreateZoneId())
                .content(dto.getContent())
                .createdAt(LocalDateTime.now())
                .reportState(BEFORE)
                .user(user)
                .build();

        reportRepository.save(report);

        // 태그 ID 리스트를 기반으로 ReportTagMap 생성
        List<ReportTagMap> reportTagMaps = dto.getTagId().stream()
                .map(tagId -> {
                    Tag tag = tagRepository.findById(tagId).orElseThrow(
                            () -> new CustomException("Tag not found", HttpStatus.NOT_FOUND)
                    );
                    return new ReportTagMap(report, tag);
                })
                .collect(Collectors.toList());

        reportTagMapRepository.saveAll(reportTagMaps);
        report.getTags().addAll(reportTagMaps);

        return "Report created successfully";

    }

    @Transactional
    public List<ShowMonthlyReportDto> showMonthlyReport(int year, int month) {
        // 1. 해당 월에 해당하는 Report 데이터 조회
        List<Report> reports = reportRepository.findByCreatedAtBetween(
                LocalDate.of(year, month, 1).atStartOfDay(),
                LocalDate.of(year, month, 1).plusMonths(1).atStartOfDay().minusSeconds(1)
        );

        // 2. Report 데이터를 ShowMonthlyReportDto로 변환
        return reports.stream()
                .map(report -> new ShowMonthlyReportDto(
                        report.getCreatedAt().withSecond(0),
                        report.getCreateZoneId(),  // Zone Name 대신 ID가 있을 수도 있음
                        report.getTags().stream()
                                .map(ReportTagMap::getTag) // ReportTagMap에서 Tag 추출
                                .collect(Collectors.toList()),
                        report.getContent(),
                        report.getReportState(),
                        report.getUser().getId()
                ))
                .collect(Collectors.toList());
    }

    @Transactional
    public void changeState(Long reportId) {
        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new CustomException("Report not found", HttpStatus.NOT_FOUND));

        if (report.getReportState() == ReportState.BEFORE) {
            report.setReportState(AFTER);
        }else {
            report.setReportState(BEFORE);
        }
    }

    public ShowDetailReportDto detailReport(Long reportId) {
        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new CustomException("Report not found", HttpStatus.NOT_FOUND));

        List<ReportTagMap> tagMaps = reportTagMapRepository.findByReportId(reportId);

        Map<Long, String> tagMap = tagMaps.stream()
                .collect(Collectors.toMap(
                        m -> m.getTag().getId(),
                        m -> m.getTag().getTagName()
                ));

        ShowDetailReportDto detailReportDto = ShowDetailReportDto.builder()
                .content(report.getContent())
                .reportState(report.getReportState())
                .createdAt(report.getCreatedAt())
                .zoneId(report.getCreateZoneId())
                .tagMap(tagMap)
                .build();
        return detailReportDto;
    }
}
