package com.example.anjeonRefactoring.Audio.domain;

import com.example.anjeonRefactoring.Audio.domain.enumration.ZoneState;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@NoArgsConstructor
public class Zone {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "zone_id")
    private Long id;

    private String zoneName;

    @Enumerated(EnumType.STRING)
    private ZoneState zoneState;

    @Setter
    private int threshold;

    @OneToMany(mappedBy = "zone")
    private List<Machine> machines = new ArrayList<>();

    @OneToMany(mappedBy = "zone")
    private List<Noise> noises = new ArrayList<>();


}
