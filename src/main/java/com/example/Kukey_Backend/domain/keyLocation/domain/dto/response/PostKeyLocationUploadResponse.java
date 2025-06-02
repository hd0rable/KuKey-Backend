package com.example.Kukey_Backend.domain.keyLocation.domain.dto.response;

import lombok.Builder;

@Builder
public record PostKeyLocationUploadResponse(
        String buildingName,
        Long keyLocationId
) {
}
