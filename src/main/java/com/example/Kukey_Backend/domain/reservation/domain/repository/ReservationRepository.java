package com.example.Kukey_Backend.domain.reservation.domain.repository;

import com.example.Kukey_Backend.domain.reservation.domain.Reservation;
import com.example.Kukey_Backend.domain.space.domain.Space;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findAllBySpaceAndReservationDate(Space space, LocalDate localDate);

    List<Reservation> findByStudentNumberAndStudentNameOrderByReservationDateAsc(String studentNumber, String studentName);

    // 활성 예약 조회 (시작 ≤ 현재 < 종료)
    @Query("SELECT r FROM Reservation r " +
            "WHERE r.reservationStartTime <= :now " +
            "AND r.reservationEndTime > :now ")
    List<Reservation> findByReservationStartTimeLessThanEqualAndReservationEndTimeAfter(@Param("now") LocalTime now);

    boolean existsBySpaceAndReservationStartTimeLessThanEqualAndReservationEndTimeAfter(Space space,
                                                                                                 LocalTime nowStart,
                                                                                                 LocalTime nowEnd);

    List<Reservation> findByReservationDateBefore(LocalDate date);
}
