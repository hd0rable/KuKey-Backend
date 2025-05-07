package com.example.Kukey_Backend.domain.auth.domain.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record PostAuthVerifiedCodeRequest(
        @NotBlank(message = "이메일은 필수 입력 항목입니다.")
        @Email(message = "올바른 이메일 형식이 아닙니다.")
        String email,
        @NotBlank(message = "인증코드는 필수 입력 항목입니다.")
        @Pattern(regexp = "^[0-9]{4}$", message = "숫자 4자리를 입력해주세요.")
        String code
) {
}
