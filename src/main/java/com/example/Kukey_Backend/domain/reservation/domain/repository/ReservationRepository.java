package com.example.Kukey_Backend.domain.reservation.domain.repository;

import com.example.Kukey_Backend.domain.reservation.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

}
