package com.example.Kukey_Backend.domain.space.domain.repository;

import com.example.Kukey_Backend.domain.space.domain.BuildingName;
import com.example.Kukey_Backend.domain.space.domain.Space;
import com.example.Kukey_Backend.global.entity.RequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SpaceRepository extends JpaRepository<Space, Long> {

    Optional<List<Space>> findAllByBuildingName(BuildingName buildingName);
    Optional<Space> findFirstByBuildingNameOrderBySpaceIdAsc(BuildingName buildingName);
    List<Space> findByReservationStatus(RequestStatus reservationStatus);
}
