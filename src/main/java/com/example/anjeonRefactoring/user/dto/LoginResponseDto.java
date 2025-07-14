package com.example.anjeonRefactoring.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponseDto {
    private String status;

    private String token;

    private UserDto user;
}
