package com.example.anjeonRefactoring.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "audio_analysis")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AudioAnalysis{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long zoneId;

    private String fileName;

    private double decibel;

    private String soundClass;

    private String transcription;

    private LocalDateTime createTime;

    @ElementCollection
    private List<String> detectedKeywords; // List로 변환된 값을 저장
}
