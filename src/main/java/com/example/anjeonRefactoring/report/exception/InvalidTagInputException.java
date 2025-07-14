package com.example.anjeonRefactoring.report.exception;

import com.example.anjeonRefactoring.global.exception.BaseCustomException;
import com.example.anjeonRefactoring.global.exception.ErrorCode;

public class InvalidTagInputException extends BaseCustomException {
    public InvalidTagInputException() {
        super(ErrorCode.INVALID_INPUT_VALUE);
    }
}
