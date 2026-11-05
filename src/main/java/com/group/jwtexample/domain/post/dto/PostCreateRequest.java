package com.group.jwtexample.domain.post.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

// 작성자는 요청으로 받지 않고 토큰에서 꺼냅니다.
public record PostCreateRequest(
        @NotBlank(message = "제목은 비어있을 수 없습니다.")
        @Size(max = 255, message = "제목은 255자 이하로 입력해주세요.")
        String title,

        @NotBlank(message = "본문은 비어있을 수 없습니다")
        @Size(max = 255, message = "본문은 255자 이하로 입력해주세요.")
        String content
) {
}
