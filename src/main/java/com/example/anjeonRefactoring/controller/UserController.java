package com.example.anjeonRefactoring.controller;

import com.example.anjeonRefactoring.repuestDto.LoginDto;
import com.example.anjeonRefactoring.repuestDto.RegisterReuestDto;
import com.example.anjeonRefactoring.responsDto.LoginResponseDto;
import com.example.anjeonRefactoring.responsDto.UserDto;
import com.example.anjeonRefactoring.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public String register(@RequestBody RegisterReuestDto dto){
        String result = userService.register(dto);
        return result;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginDto dto){
        System.out.println("dto = " + dto);
        String status = userService.login(dto);

        if (status.equals("user not found") || status.equals("password error")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new LoginResponseDto(status, null, null));
        }

        String token = insertToken(status);
        UserDto userInfo = userService.userInfo(dto.getEmail());

        return ResponseEntity.ok(new LoginResponseDto("success", token, userInfo));

    }

    public String insertToken(String token) {
        try {
            // 쿠키 값 UTF-8로 인코딩
            String cookieValue = URLEncoder.encode(token, "UTF-8");
            Cookie cookie = new Cookie("accessToken", cookieValue);

            cookie.setPath("/");
            cookie.setSecure(false); // 실제 배포 시 true로 설정
            cookie.setMaxAge(60 * 60 * 24 * 30); // 30일
            cookie.setHttpOnly(true);

            HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
            response.addCookie(cookie);
        } catch (UnsupportedEncodingException e) {
            // 인코딩 실패 시 로깅 또는 예외 처리
            System.err.println("쿠키 인코딩 오류: " + e.getMessage());
        }
        return token;
    }

}
