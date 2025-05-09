package com.example.Kukey_Backend.domain.space.domain.dto.response;

import lombok.Builder;

@Builder
public record spaceOpenInfo(
        Long spaceId,
        String buildingName,
        String spaceDisplayName,
        String openStatus,
        String openRequestStatus,
        String ReservationStatus
)
{
}
