package com.example.anjeonRefactoring.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    // 400 - BAD_REQUEST : 잘못된 요청
    INVALID_INPUT_VALUE("40001", 400, "올바르지 않은 입력 값입니다."),
    UNSUPPORTED_AUTH_PROVIDER("40002", 400, "지원되지 않는 OAuth 소셜 타입입니다."),
    MEMBER_ALREADY_REGISTERED("40003", 400, "이미 가입된 사용자입니다."),

    // 401 - UNAUTHORIZED : 인가되지 않은 경우
    TOKEN_EXPIRED("40101", 401, "토큰이 만료되었습니다."),
    TOKEN_ERROR("40102", 401, "토큰이 일치하지 않습니다."),

    // 403 - FORBIDDEN : 권한이 없는 경우
    TOKEN_FORBIDDEN("40301", 403, "토큰에 권한 정보가 존재하지 않는 토큰"),

    // 404 - NOT_FOUND : 존재하지 않은 경우
    USER_NOT_FOUND("40401", 404, "회원 정보를 찾을 수 없습니다."),
    TOKEN_NOT_FOUND("40402", 404, "리프레시 토큰 정보를 찾을 수 없습니다."),
    TAG_NOT_FOUND("40403", 404, "존재하지 않는 태그입니다."),
    REPORT_NOT_FOUND("40404", 404, "존재하지 않는 리포트 입니다. 다시 확인해 주세요"),




    // 405 - METHOD_NOT_ALLOWED : 올바르지 않은 Request Method 호출
    METHOD_NOT_ALLOWED("40501", 405, "올바르지 않은 호출입니다."),

    // 500 - SERVER_ERROR : 내부 서버 오류
    INTERNAL_SERVER_ERROR("50001", 500, "내부 서버 오류입니다.");

    private final String customCode; // 커스텀 상태 코드
    private final int statusCode;    // HTTP 상태 코드
    private final String message;    // 에러 메시지
}
