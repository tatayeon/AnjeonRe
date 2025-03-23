package com.example.anjeonRefactoring.responsDto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserDto {

    private Long id;

    private String username;

    private String email;

}
