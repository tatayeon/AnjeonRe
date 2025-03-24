package com.example.anjeonRefactoring.responsDto;

import com.example.anjeonRefactoring.domain.Tag;
import com.example.anjeonRefactoring.domain.enumration.ReportState;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class ShowMonthlyReportDto {

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime createdAt;

    private Long zoneName;

    private List<Tag> tags;

    private String content;

    private ReportState reportState;

    private Long userId;

}
