package com.example.anjeonRefactoring.report.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateTagDto {

    private String category;

    private String tagName;

}
