package com.example.anjeonRefactoring.report.exception;

import com.example.anjeonRefactoring.global.exception.BaseCustomException;
import com.example.anjeonRefactoring.global.exception.ErrorCode;

public class ReportNotFoundException extends BaseCustomException {
    public ReportNotFoundException() {
        super(ErrorCode.REPORT_NOT_FOUND);
    }
}
