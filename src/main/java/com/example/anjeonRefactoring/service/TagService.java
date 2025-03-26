package com.example.anjeonRefactoring.service;

import com.example.anjeonRefactoring.domain.Tag;
import com.example.anjeonRefactoring.repository.TagRepository;
import com.example.anjeonRefactoring.repuestDto.CreateTagDto;
import com.example.anjeonRefactoring.responsDto.ShowTagDto;
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
}
