package com.example.anjeonRefactoring.responsDto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@NoArgsConstructor
@Builder
public class ShowTagDto {

    private String category;

    private Map<Long, String> tagMap;

    public ShowTagDto(String category, Map<Long, String> tagMap) {
        this.category = category;
        this.tagMap = tagMap;
    }
}
