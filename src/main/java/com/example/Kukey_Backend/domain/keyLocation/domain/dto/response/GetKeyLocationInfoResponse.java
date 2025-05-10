package com.example.Kukey_Backend.domain.keyLocation.domain.dto.response;

import lombok.Builder;

@Builder
public record GetKeyLocationInfoResponse(
        Long keyLocationId,
        String buildingName,
        String adminName,
        String imageUrl,
        String description
) {
}
