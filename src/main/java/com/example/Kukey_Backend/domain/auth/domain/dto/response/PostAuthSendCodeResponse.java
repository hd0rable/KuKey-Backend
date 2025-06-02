package com.example.Kukey_Backend.domain.auth.domain.dto.response;

public record PostAuthSendCodeResponse(
        boolean isVerified,
        String accessToken) {
}
