package com.example.anjeonRefactoring.repuestDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateTagDto {

    private String category;

    private String tagName;

}
