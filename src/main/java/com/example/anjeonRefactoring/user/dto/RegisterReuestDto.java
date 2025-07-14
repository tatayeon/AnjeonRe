package com.example.anjeonRefactoring.user.dto;

import com.example.anjeonRefactoring.user.domain.enumration.RoleType;
import lombok.Getter;

@Getter
public class RegisterReuestDto {

    private String userName;

    private String email;

    private String password;

    private RoleType role;

}
