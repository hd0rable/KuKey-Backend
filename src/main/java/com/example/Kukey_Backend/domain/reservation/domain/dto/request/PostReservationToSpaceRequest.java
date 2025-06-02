package com.example.Kukey_Backend.domain.reservation.domain.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.time.LocalTime;

public record PostReservationToSpaceRequest(

        @NotNull(message = "예약 날짜는 필수입니다.")
        LocalDate reservationDate,

        @NotNull(message = "예약 시작 시간은 필수입니다.")
        LocalTime reservationStartTime,

        @NotNull(message = "예약 종료 시간은 필수입니다.")
        LocalTime reservationEndTime,

        @NotBlank(message = "학번은 필수입니다.")
        @Size(max=10,message = "학번은 10자 이하로 입력해주세요")
        String studentNumber,

        @NotBlank(message = "예약자 이름은 필수입니다.")
        @Size(max=30,message = "예약자 이름은 30자 이하로 입력해주세요")
        String studentName,

        @NotBlank(message = "소속 그룹은 필수입니다.")
        @Size(max=30,message = "소속 그룹은 30자 이하로 입력해주세요")
        String studentGroup,

        @NotBlank(message = "예약 목적은 필수입니다.")
        String reservationPurpose
) {
}
