package com.example.anjeonRefactoring.domain;

import com.example.anjeonRefactoring.domain.enumration.ZoneState;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.jdbc.datasource.SmartDataSource;

import javax.crypto.Mac;
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

    private int threshold;

    @OneToMany(mappedBy = "zone")
    private List<Machine> machines = new ArrayList<>();

    @OneToMany(mappedBy = "zone")
    private List<Noise> noises = new ArrayList<>();
}
