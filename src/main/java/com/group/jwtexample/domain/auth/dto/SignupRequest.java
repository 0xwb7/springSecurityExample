package com.group.jwtexample.domain.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SignupRequest(
        @NotBlank(message = "올바른 이메일을 입력해주세요.")
        @Email(message = "올바른 이메일을 입력해주세요.")
        @Size(max = 100, message = "이메일은 100자 이하로 입력해주세요.")
        String email,

        @NotBlank(message = "올바른 비밀번호를 입력해주세요.")
        String password
) {
}
