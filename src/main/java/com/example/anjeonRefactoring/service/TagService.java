package com.example.anjeonRefactoring.service;

import com.example.anjeonRefactoring.domain.Tag;
import com.example.anjeonRefactoring.repository.TagRepository;
import com.example.anjeonRefactoring.repuestDto.CreateTagDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
