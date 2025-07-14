package com.example.anjeonRefactoring.report.repository;

import com.example.anjeonRefactoring.report.domain.Report;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ReportRepository extends JpaRepository<Report, Long> {
    List<Report> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);
}
