package com.example.Kukey_Backend.domain.reservation.domain.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record SpaceReservationInfo(
        Long spaceId,
        String buildingName,
        String spaceDisplayName,
        List<TimeSlot> unavailableReservationTimeList
) {
}
