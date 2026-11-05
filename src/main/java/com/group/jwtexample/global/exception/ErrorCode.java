package com.group.jwtexample.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    // Common
    INTERNAL_SERVER_ERROR("COMMON_001", HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류가 발생했습니다."),
    INVALID_INPUT_VALUE("COMMON_002", HttpStatus.BAD_REQUEST, "입력값이 올바르지 않습니다."),
    INVALID_REQUEST_BODY("COMMON_003", HttpStatus.BAD_REQUEST, "요청 본문의 형식이 올바르지 않습니다."),
    RESOURCE_NOT_FOUND("COMMON_004", HttpStatus.NOT_FOUND, "요청한 경로를 찾을 수 없습니다."),
    METHOD_NOT_ALLOWED("COMMON_005", HttpStatus.METHOD_NOT_ALLOWED, "지원하지 않는 요청 방식입니다."),
    UNSUPPORTED_MEDIA_TYPE("COMMON_006", HttpStatus.UNSUPPORTED_MEDIA_TYPE, "지원하지 않는 요청 형식입니다."),

    // User
    USER_NOT_FOUND("USER_001", HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."),
    DUPLICATE_EMAIL("USER_002", HttpStatus.CONFLICT, "이미 가입된 이메일입니다."),

    // Auth
    AUTH_REQUIRED("AUTH_001", HttpStatus.UNAUTHORIZED, "로그인이 필요합니다."),
    INVALID_CREDENTIALS("AUTH_002", HttpStatus.UNAUTHORIZED, "이메일 또는 비밀번호가 틀렸습니다."),
    INVALID_REFRESH_TOKEN("AUTH_003", HttpStatus.UNAUTHORIZED, "유효하지 않은 Refresh Token입니다."),
    REFRESH_TOKEN_NOT_FOUND("AUTH_004", HttpStatus.UNAUTHORIZED, "로그인 기록이 없습니다."),
    REFRESH_TOKEN_REUSED("AUTH_005", HttpStatus.UNAUTHORIZED, "이미 사용한 Refresh Token입니다."),

    // Post
    POST_NOT_FOUND("POST_001", HttpStatus.NOT_FOUND, "게시글을 찾을 수 없습니다."),
    POST_FORBIDDEN("POST_002", HttpStatus.FORBIDDEN, "본인이 작성한 게시글만 삭제할 수 있습니다.");

    private final String code;
    private final HttpStatus status;
    private final String message;
}
