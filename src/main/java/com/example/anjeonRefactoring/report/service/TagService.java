package com.example.anjeonRefactoring.report.service;

import com.example.anjeonRefactoring.report.domain.Tag;
import com.example.anjeonRefactoring.report.exception.TagNotFoundException;
import com.example.anjeonRefactoring.report.repository.ReportTagMapRepository;
import com.example.anjeonRefactoring.report.repository.TagRepository;
import com.example.anjeonRefactoring.report.dto.CreateTagDto;
import com.example.anjeonRefactoring.report.dto.ShowTagDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TagService {

    private final TagRepository tagRepository;
    private final ReportTagMapRepository reportTagMapRepository;

    @Transactional
    public Tag createTag(CreateTagDto dto) {
        Tag tag = Tag.builder()
                .tagName(dto.getTagName())
                .category(dto.getCategory())
                .build();
        tagRepository.save(tag);

        return tag;
    }

    public List<ShowTagDto> showTagAll() {

        Map<String, List<Tag>> groupedByCategory = tagRepository.findAll()
                .stream()
                .collect(Collectors.groupingBy(Tag::getCategory));

        return groupedByCategory.entrySet()
                .stream()
                .map(entry -> {
                    String category = entry.getKey();

                    // tagName과 tagId를 Map으로 묶음
        Map<Long, String> tagMap = entry.getValue()
                .stream()
                .collect(Collectors.toMap(Tag::getId, Tag::getTagName));

        return ShowTagDto.builder()
                .tagMap(tagMap) // tagMap을 저장
                .category(category)
                .build();
                })
                .collect(Collectors.toList());

    }

    @Transactional
    public List<ShowTagDto> delete(Long tagId) {
        Tag tag = tagRepository.findById(tagId)
                        .orElseThrow(TagNotFoundException::new);

        tagRepository.delete(tag);
//        reportTagMapRepository.deleteAllByTagId(tagId);

        Map<String, List<Tag>> groupedByCategory = tagRepository.findAll()
                .stream()
                .collect(Collectors.groupingBy(Tag::getCategory));

        return groupedByCategory.entrySet()
                .stream()
                .map(entry -> {
                    String category = entry.getKey();

                    Map<Long, String> tagMap = entry.getValue()
                            .stream()
                            .collect(Collectors.toMap(Tag::getId, Tag::getTagName));

                    return ShowTagDto.builder()
                            .tagMap(tagMap)
                            .category(category)
                            .build();
                })
                .collect(Collectors.toList());
    }

}
