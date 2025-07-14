package com.example.anjeonRefactoring.Audio.repository;


import com.example.anjeonRefactoring.Audio.domain.AudioAnalysis;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AudioRepository extends JpaRepository<AudioAnalysis, Long> {
}
