package com.example.anjeonRefactoring.report.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class CreateReportDto {

    private String content;

    private Long createZoneId;

    private List<Long> tagId;

}
