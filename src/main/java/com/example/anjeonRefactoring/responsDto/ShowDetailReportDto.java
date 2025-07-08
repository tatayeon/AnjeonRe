package com.example.anjeonRefactoring.responsDto;

import com.example.anjeonRefactoring.domain.Tag;
import com.example.anjeonRefactoring.domain.enumration.ReportState;
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
