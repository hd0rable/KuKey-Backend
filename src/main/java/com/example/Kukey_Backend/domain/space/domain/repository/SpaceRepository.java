package com.example.Kukey_Backend.domain.space.domain.repository;

import com.example.Kukey_Backend.domain.space.domain.Space;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpaceRepository extends JpaRepository<Space, Long> {

}
