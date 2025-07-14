package com.example.anjeonRefactoring.global.annotation;

import com.example.anjeonRefactoring.global.exception.BaseCustomException;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiErrorCodeExamples {

    Class<? extends BaseCustomException>[] value();
}
