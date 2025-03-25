package com.example.anjeonRefactoring.responsDto;

import com.example.anjeonRefactoring.domain.Tag;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateTagResponsDto {

    private String statusMessage;

    private Tag tag;
}
