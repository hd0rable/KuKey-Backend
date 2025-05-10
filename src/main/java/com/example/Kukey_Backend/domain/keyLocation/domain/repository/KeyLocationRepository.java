package com.example.Kukey_Backend.domain.keyLocation.domain.repository;

import com.example.Kukey_Backend.domain.keyLocation.domain.KeyLocation;
import com.example.Kukey_Backend.domain.space.domain.Space;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface KeyLocationRepository extends JpaRepository<KeyLocation, Long> {
    Optional<KeyLocation> findBySpace(Space space);
}
