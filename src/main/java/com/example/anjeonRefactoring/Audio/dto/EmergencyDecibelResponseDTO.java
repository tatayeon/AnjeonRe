package com.example.anjeonRefactoring.Audio.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Builder
public class EmergencyDecibelResponseDTO {

    private LocalDateTime timestamp;

    private Long zoneId;

    private double decibel;

    private String soundClass;

    private String transcription;

    public EmergencyDecibelResponseDTO(LocalDateTime timestamp, Long zoneId, double decibel, String soundClass, String transcription) {
        this.timestamp = timestamp;
        this.zoneId = zoneId;
        this.decibel = decibel;
        this.soundClass = soundClass;
        this.transcription = transcription;
    }
}
