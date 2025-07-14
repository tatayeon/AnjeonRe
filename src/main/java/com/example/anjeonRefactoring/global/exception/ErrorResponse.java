package com.example.anjeonRefactoring.global.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class ErrorResponse {
    private final LocalDateTime timestamp = LocalDateTime.now();
    private final String customCode;
    private final String message;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    List<FieldErrorDetail> validation;

    public ErrorResponse(ErrorCode errorCode) {
        this.customCode = errorCode.getCustomCode();
        this.message = errorCode.getMessage();
        this.validation = new ArrayList<>();
    }

    // 유효성 검사 에러 정보를 추가하는 메서드
    public void addValidation(String field, String rejectedValue, String reason) {
        this.validation.add(new FieldErrorDetail(field, rejectedValue, reason));
    }

    @Getter
    @AllArgsConstructor
    public static class FieldErrorDetail {
        private String field;
        private String rejectedValue;
        private String reason;
    }
}
