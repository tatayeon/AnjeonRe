package com.example.anjeonRefactoring.report.controller;

import com.example.anjeonRefactoring.report.domain.Tag;
import com.example.anjeonRefactoring.global.exception.CustomException;
import com.example.anjeonRefactoring.report.dto.CreateTagDto;

import com.example.anjeonRefactoring.report.dto.CreateTagResponsDto;
import com.example.anjeonRefactoring.report.dto.ShowTagDto;
import com.example.anjeonRefactoring.report.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/show")
    public ResponseEntity<List<ShowTagDto>> showTagAll(){
        List<ShowTagDto> tags = tagService.showTagAll();
        return ResponseEntity.ok(tags);
    }

    @DeleteMapping("{tagId}")
    public ResponseEntity<List<ShowTagDto>> deleteTag(@PathVariable Long tagId){
        List<ShowTagDto> tags = tagService.delete(tagId);
        return ResponseEntity.ok(tags);
    }


}
