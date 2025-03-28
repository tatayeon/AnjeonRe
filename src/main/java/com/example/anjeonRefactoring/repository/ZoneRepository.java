package com.example.anjeonRefactoring.repository;

import com.example.anjeonRefactoring.domain.Zone;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ZoneRepository extends JpaRepository<Zone, Long> {
}
