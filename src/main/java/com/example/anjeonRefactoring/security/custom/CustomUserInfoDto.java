package com.example.anjeonRefactoring.security.custom;

import com.example.anjeonRefactoring.domain.enumration.RoleType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomUserInfoDto {
    private Long userId;

    private String username;

    private String password;

    private RoleType role;

}
