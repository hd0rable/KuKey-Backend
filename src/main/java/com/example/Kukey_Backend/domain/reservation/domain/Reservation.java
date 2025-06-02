package com.example.Kukey_Backend.domain.reservation.domain;

import com.example.Kukey_Backend.domain.space.domain.Space;
import com.example.Kukey_Backend.global.entity.BaseEntity;
import com.example.Kukey_Backend.global.entity.RequestStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "reservations")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reservation extends BaseEntity {

    @Id
    @Column(name = "reservation_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reservationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "space_id", nullable = false)
    private Space space;

    @Column(name = "reservation_date", nullable = false)
    private LocalDate reservationDate;

    @Column(name = "reservation_start_time", nullable = false)
    private LocalTime reservationStartTime;

    @Column(name = "reservation_end_time", nullable = false)
    private LocalTime reservationEndTime;

    @Column(name = "student_email", nullable = false)
    private String studentEmail;

    @Column(name = "student_number", length = 10, nullable = false)
    private String studentNumber;

    @Column(name = "student_name", length = 30, nullable = false)
    private String studentName;

    @Column(name = "student_group", length = 30, nullable = false)
    private String studentGroup;

    @Column(name = "reservation_purpose", nullable = false)
    private String reservationPurpose;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private RequestStatus status;
}

