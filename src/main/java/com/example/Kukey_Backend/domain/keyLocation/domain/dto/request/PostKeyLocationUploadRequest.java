package com.example.Kukey_Backend.domain.keyLocation.domain.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PostKeyLocationUploadRequest(
        @NotBlank(message = "건물명은 필수입니다.")
        String buildingName,
        @NotBlank(message = "관리자명은 필수입니다.")
        @Size(max = 30, message = "관리자명은 30자 이하로 입력해주세요")
        String adminName,
        @NotBlank(message = "이미지 URL은 필수입니다.")
        String imageUrl,
        @NotBlank(message = "설명은 필수입니다.")
        String description
) {
}
