package com.example.trelloproject.user.controller;

import com.example.trelloproject.global.dto.ApiResponseDto;
import com.example.trelloproject.jwt.JwtUtil;
import com.example.trelloproject.user.dto.UserLoginDto;
import com.example.trelloproject.user.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/users")
public class UserController {
    private final UserService userService;
    private final JwtUtil jwtUtil;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponseDto> signup(@Valid @RequestBody UserLoginDto userLoginDto) {
        try {
            userService.signup(userLoginDto);
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.badRequest().body(new ApiResponseDto("중복된 username 입니다.", HttpStatus.BAD_REQUEST.value()));
        }
        return ResponseEntity.status(HttpStatus.CREATED.value())
                .body(new ApiResponseDto("회원가입 성공", HttpStatus.CREATED.value()));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponseDto> login(@RequestBody UserLoginDto userLoginDto, HttpServletResponse response) {
        try {
            userService.login(userLoginDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ApiResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
        response.setHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(userLoginDto.getUsername()));
        return ResponseEntity.ok().body(new ApiResponseDto("로그인 성공", HttpStatus.OK.value()));
    }
}
