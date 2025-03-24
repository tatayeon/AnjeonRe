package com.example.anjeonRefactoring.repuestDto;

import com.example.anjeonRefactoring.domain.Zone;
import lombok.Getter;

import java.util.List;

@Getter
public class CreateReportDto {

    private String content;

    private Long createZoneId;

    private List<Long> tagId;

}
