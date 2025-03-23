package com.example.anjeonRefactoring.responsDto;

import com.example.anjeonRefactoring.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponseDto {
    private String status;

    private String token;

    private UserDto user;
}
