package com.example.Kukey_Backend.domain.reservation.domain.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record GetSpacesReservationInfoResponse(
        List<SpaceReservationInfo> reservationList
) {
}
