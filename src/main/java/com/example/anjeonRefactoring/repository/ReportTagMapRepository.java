package com.example.anjeonRefactoring.repository;

import com.example.anjeonRefactoring.domain.ReportTagMap;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReportTagMapRepository extends JpaRepository<ReportTagMap, Long> {
    List<ReportTagMap> findByReportId(Long reportId);
}
