package com.example.anjeonRefactoring.report.dto;

import com.example.anjeonRefactoring.report.domain.enumration.ReportState;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Map;

@Builder
@Getter
public class ShowDetailReportDto {

    private LocalDateTime createdAt;

    private Long zoneId;

    private String content;

    @Enumerated(EnumType.STRING)
    private ReportState reportState;

    private Map<Long, String> tagMap;

}
