package com.example.anjeonRefactoring.repository;


import com.example.anjeonRefactoring.domain.AudioAnalysis;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AudioRepository extends JpaRepository<AudioAnalysis, Long> {
}
