package com.example.anjeonRefactoring.report.repository;

import com.example.anjeonRefactoring.report.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
}
