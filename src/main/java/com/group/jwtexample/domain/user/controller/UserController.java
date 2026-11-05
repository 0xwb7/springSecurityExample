package com.group.jwtexample.domain.user.controller;

import com.group.jwtexample.domain.user.dto.UserResponse;
import com.group.jwtexample.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 토큰에서 꺼낸 userId가 @AuthenticationPrincipal로 들어옵니다.
    @GetMapping("/me")
    public UserResponse getMyInfo(@AuthenticationPrincipal Long userId) {
        return userService.getMyInfo(userId);
    }
}
