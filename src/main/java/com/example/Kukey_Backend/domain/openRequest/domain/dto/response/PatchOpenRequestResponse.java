package com.example.Kukey_Backend.domain.openRequest.domain.dto.response;

import lombok.Builder;

@Builder
public record PatchOpenRequestResponse(
        Long spaceId,
        String openRequestStatus
) {
}
