package com.example.anjeonRefactoring.global.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {

    // BaseCustomException을 상속하는 예외 발생 시 처리
    @ExceptionHandler(BaseCustomException.class)
    public ResponseEntity<ErrorResponse> expirationDateException(BaseCustomException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getErrorCode());

        return ResponseEntity.status(e.getErrorCode().getStatusCode()).body(errorResponse);
    }

    // 유효성 검사 시 발생하는 에러 처리
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> invalidRequestException(MethodArgumentNotValidException e) {
        ErrorCode errorCode = ErrorCode.INVALID_INPUT_VALUE;
        ErrorResponse response = new ErrorResponse(errorCode);

        for (FieldError fieldError : e.getFieldErrors()) {
            String rejectedValue = fieldError.getRejectedValue() == null
                    ? ""
                    : fieldError.getRejectedValue().toString();

            response.addValidation(fieldError.getField(), rejectedValue, fieldError.getDefaultMessage());
        }

        return ResponseEntity.status(errorCode.getStatusCode()).body(response);
    }
}