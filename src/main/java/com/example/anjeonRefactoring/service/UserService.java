package com.example.anjeonRefactoring.service;

import com.example.anjeonRefactoring.domain.User;
import com.example.anjeonRefactoring.exception.CustomException;
import com.example.anjeonRefactoring.repository.UserRepository;
import com.example.anjeonRefactoring.repuestDto.LoginDto;
import com.example.anjeonRefactoring.repuestDto.RegisterReuestDto;
import com.example.anjeonRefactoring.responsDto.UserDto;
import com.example.anjeonRefactoring.security.custom.CustomUserInfoDto;
import com.example.anjeonRefactoring.security.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final JwtUtil jwtUtil;

    public String register(RegisterReuestDto dto){

        if(CheckEmail(dto.getEmail())){
            User user2 = new User(
                    dto.getUserName(),
                    dto.getEmail(),
                    encoder.encode(dto.getPassword()),
                    dto.getRole()
            );
            userRepository.save(user2);
            return "good";
        }else{
            return "이미 존재하는 이메일 입니다.";
        }
    }

    @Transactional
    public String login(LoginDto dto) {
        System.out.println("service "+dto.getEmail() + " " + dto.getPassword());
        User user = userRepository.findUserByEmail(dto.getEmail());

        if(user == null || !encoder.matches(dto.getPassword(), user.getPassword())){
            throw new CustomException("Invalid email or password", HttpStatus.UNAUTHORIZED);
        }

        CustomUserInfoDto customUserInfoDto = new CustomUserInfoDto(user.getId(), user.getUsername(), user.getPassword(), user.getRole());
        return jwtUtil.createAccessToken(customUserInfoDto);
    }

    private boolean CheckEmail(String email) {
        User user = userRepository.findUserByEmail(email);
        if(user == null){
            return true;
        }
        return false;
    }

    public UserDto userInfo(String email) {
        User user = userRepository.findUserByEmail(email);
        return new UserDto(user.getId(), user.getUsername(), user.getEmail());
    }
}
