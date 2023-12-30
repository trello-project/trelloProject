package com.example.trelloproject.user.controller;

import com.example.trelloproject.Global.DTO.CommonResponseDTO;
import com.example.trelloproject.User.Service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid com.example.trelloproject.User.DTO.SignupDto signupDTO){
        CommonResponseDTO<?> responseDTO = userService.signup(signupDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid com.example.trelloproject.User.DTO.LoginDto loginDTO){
        CommonResponseDTO<?> responseDTO = userService.login(loginDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
}
