package com.example.anjeonRefactoring.report.dto;

import com.example.anjeonRefactoring.report.domain.Tag;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateTagResponsDto {

    private String statusMessage;

    private Tag tag;
}
