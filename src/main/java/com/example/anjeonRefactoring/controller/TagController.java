package com.example.anjeonRefactoring.controller;

import com.example.anjeonRefactoring.domain.Tag;
import com.example.anjeonRefactoring.exception.CustomException;
import com.example.anjeonRefactoring.repuestDto.CreateTagDto;

import com.example.anjeonRefactoring.responsDto.CreateTagResponsDto;
import com.example.anjeonRefactoring.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tag")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    @PostMapping("/create")
    public ResponseEntity<CreateTagResponsDto> createTag(@RequestBody CreateTagDto dto){
        Tag tag = tagService.createTag(dto);
        if(tag == null){
            throw new CustomException("fail to create tag", HttpStatus.BAD_REQUEST);
        }else {
            return ResponseEntity.ok(new CreateTagResponsDto("success", tag));
        }
    }


}
