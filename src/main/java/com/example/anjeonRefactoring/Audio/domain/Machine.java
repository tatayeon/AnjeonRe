package com.example.anjeonRefactoring.Audio.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@NoArgsConstructor
public class Machine {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "machine_id")
    private Long id;

    private String machineName;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "zone_id")
    private Zone zone;

}
