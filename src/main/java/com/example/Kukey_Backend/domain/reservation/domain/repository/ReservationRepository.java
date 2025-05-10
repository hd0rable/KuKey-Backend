package com.example.Kukey_Backend.domain.reservation.domain.repository;

import com.example.Kukey_Backend.domain.reservation.domain.Reservation;
import com.example.Kukey_Backend.domain.space.domain.Space;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findAllBySpaceAndReservationDate(Space space, LocalDate localDate);
}
