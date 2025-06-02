package com.example.Kukey_Backend.domain.reservation.domain.dto.response;

import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalTime;

@Builder
public record ReservationInfo(
        Long spaceId,
        String buildingName,
        String spaceDisplayName,
        Long reservationId,
        LocalDate reservationDate,
        LocalTime reservationStartTime,
        LocalTime reservationEndTime,
        String studentGroup,
        String reservationPurpose
) {
}
