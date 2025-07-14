package com.example.anjeonRefactoring.global.security.custom;

import com.example.anjeonRefactoring.user.domain.enumration.RoleType;
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
