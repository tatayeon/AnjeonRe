package com.example.anjeonRefactoring.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public abstract class BaseCustomException extends RuntimeException {
    private final ErrorCode errorCode;
}
