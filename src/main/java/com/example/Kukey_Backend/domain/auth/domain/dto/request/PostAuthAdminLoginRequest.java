package com.example.Kukey_Backend.domain.auth.domain.dto.request;

import jakarta.validation.constraints.NotBlank;

public record PostAuthAdminLoginRequest(
        @NotBlank(message = "아이디는 필수 입력 항목입니다.")
        String id,
        @NotBlank(message = "비밀번호는 필수 입력 항목입니다.")
        String password
) {
}
