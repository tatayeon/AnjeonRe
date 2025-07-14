package com.example.anjeonRefactoring.Audio.repository;

import com.example.anjeonRefactoring.Audio.domain.Zone;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ZoneRepository extends JpaRepository<Zone, Long> {
}
