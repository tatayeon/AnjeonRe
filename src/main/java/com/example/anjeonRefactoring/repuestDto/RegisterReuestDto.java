package com.example.anjeonRefactoring.repuestDto;

import com.example.anjeonRefactoring.domain.enumration.RoleType;
import lombok.Getter;

@Getter
public class RegisterReuestDto {

    private String userName;

    private String email;

    private String password;

    private RoleType role;

}
