package com.example.Kukey_Backend.domain.reservation.domain.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record GetReservationInfoResponse(

        String studentNumber,
        String studentName,
        List<ReservationInfo> reservationList
) {
}
