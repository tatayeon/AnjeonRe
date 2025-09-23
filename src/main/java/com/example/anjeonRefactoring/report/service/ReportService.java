package com.example.anjeonRefactoring.report.service;

import com.example.anjeonRefactoring.report.domain.Report;
import com.example.anjeonRefactoring.report.domain.ReportTagMap;
import com.example.anjeonRefactoring.report.domain.Tag;
import com.example.anjeonRefactoring.report.exception.ReportNotFoundException;
import com.example.anjeonRefactoring.report.exception.TagNotFoundException;
import com.example.anjeonRefactoring.user.domain.User;
import com.example.anjeonRefactoring.report.domain.enumration.ReportState;
import com.example.anjeonRefactoring.report.repository.ReportRepository;
import com.example.anjeonRefactoring.report.repository.ReportTagMapRepository;
import com.example.anjeonRefactoring.report.repository.TagRepository;
import com.example.anjeonRefactoring.user.exception.UserNotFoundException;
import com.example.anjeonRefactoring.user.repository.UserRepository;
import com.example.anjeonRefactoring.report.dto.CreateReportDto;
import com.example.anjeonRefactoring.report.dto.ShowDetailReportDto;
import com.example.anjeonRefactoring.report.dto.ShowMonthlyReportDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.example.anjeonRefactoring.report.domain.enumration.ReportState.BEFORE;
import static com.example.anjeonRefactoring.report.domain.enumration.ReportState.AFTER;

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
        User user = userRepository.findById(Long.parseLong(userName))
                .orElseThrow(UserNotFoundException::new);
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
                    Tag tag = tagRepository.findById(tagId)
                            .orElseThrow(TagNotFoundException::new);

                    return new ReportTagMap(report, tag);
                })
                .collect(Collectors.toList());

        reportTagMapRepository.saveAll(reportTagMaps);
        report.getTags().addAll(reportTagMaps);

        return "Report created successfully";

    }

    @Transactional
    public List<ShowMonthlyReportDto> showMonthlyReport(int year, int month) {

        List<Report> reports = reportRepository.findAllWithTags();

        return reports.stream()
                 .map(report -> {
                         List<Tag> tags = report.getTags().stream()
                                 .map(ReportTagMap::getTag)
                                 .collect(Collectors.toList());
                         return new ShowMonthlyReportDto(
                                 report.getCreatedAt(),
                                 report.getCreateZoneId(),
                                 tags,
                                 report.getContent(),
                                 report.getReportState(),
                                 report.getUser().getId()
                         );
                     })
                 .collect(Collectors.toList());
    }

    @Transactional
    public void changeState(Long reportId) {
        Report report = reportRepository.findById(reportId)
                .orElseThrow(ReportNotFoundException::new);

        if (report.getReportState() == ReportState.BEFORE) {
            report.setReportState(AFTER);
        }else {
            report.setReportState(BEFORE);
        }
    }

    public ShowDetailReportDto detailReport(Long reportId) {
        Report report = reportRepository.findByIdWithTags(reportId)
                .orElseThrow(ReportNotFoundException::new);

        Map<Long, String> tagMap = report.getTags().stream()
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
