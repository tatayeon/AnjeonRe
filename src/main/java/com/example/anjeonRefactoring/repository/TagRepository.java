package com.example.anjeonRefactoring.repository;

import com.example.anjeonRefactoring.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
}
