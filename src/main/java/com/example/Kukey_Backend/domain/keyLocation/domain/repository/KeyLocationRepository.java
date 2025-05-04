package com.example.Kukey_Backend.domain.keyLocation.domain.repository;

import com.example.Kukey_Backend.domain.keyLocation.domain.KeyLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KeyLocationRepository extends JpaRepository<KeyLocation, Long> {
}
