package com.example.anjeonRefactoring.user.exception;

import com.example.anjeonRefactoring.global.exception.BaseCustomException;
import com.example.anjeonRefactoring.global.exception.ErrorCode;

public class UserNotFoundException extends BaseCustomException {
    public UserNotFoundException() {
        super(ErrorCode.USER_NOT_FOUND);
    }
}
