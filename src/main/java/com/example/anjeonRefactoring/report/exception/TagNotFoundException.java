package com.example.anjeonRefactoring.report.exception;

import com.example.anjeonRefactoring.global.exception.BaseCustomException;
import com.example.anjeonRefactoring.global.exception.ErrorCode;

public class TagNotFoundException extends BaseCustomException {
    public TagNotFoundException() {
        super(ErrorCode.TAG_NOT_FOUND);
    }
}
