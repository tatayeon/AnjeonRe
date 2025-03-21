package com.example.anjeonRefactoring.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@NoArgsConstructor
public class Noise {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "noise_id")
    private Long id;

    private Double max_decibel;

    private Double min_decibel;

    private Double average_decibel;

    private Double month_average_decibel;

    private LocalDateTime created_at;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "zone_id")
    private Zone zone;


}
