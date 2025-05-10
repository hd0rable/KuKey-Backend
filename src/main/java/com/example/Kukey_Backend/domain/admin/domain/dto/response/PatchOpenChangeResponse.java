package com.example.Kukey_Backend.domain.admin.domain.dto.response;

import lombok.Builder;

@Builder
public record PatchOpenChangeResponse(
        Long spaceId,
        String openStatus
) {
}
