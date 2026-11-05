package com.group.jwtexample.domain.auth.dto;

import jakarta.validation.constraints.NotBlank;

public record RefreshRequest(
        @NotBlank(message = "Refresh Token을 입력해주세요.")
        String refreshToken
) {
}
