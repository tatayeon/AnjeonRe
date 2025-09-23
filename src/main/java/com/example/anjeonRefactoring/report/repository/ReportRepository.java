package com.example.anjeonRefactoring.report.repository;

import com.example.anjeonRefactoring.report.domain.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ReportRepository extends JpaRepository<Report, Long> {
    @Query("SELECT DISTINCT r FROM Report r " +
           "LEFT JOIN FETCH r.tags rtm " +
           "LEFT JOIN FETCH rtm.tag")
    List<Report> findAllWithTags();

    @Query("SELECT r FROM Report r " +
           "LEFT JOIN FETCH r.tags rtm " +
           "LEFT JOIN FETCH rtm.tag " +
           "WHERE r.id = :id")
    Optional<Report> findByIdWithTags(Long id);
}
