package com.example.trelloproject.user.Controller;

import com.example.trelloproject.global.DTO.CommonResponseDTO;
import com.example.trelloproject.user.DTO.LoginDTO;
import com.example.trelloproject.user.DTO.SignupDTO;
import com.example.trelloproject.user.Service.UserService;
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
    public ResponseEntity<?> signup(@Valid SignupDTO signupDTO){
        CommonResponseDTO<?> responseDTO = userService.signup(signupDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid LoginDTO loginDTO){
        CommonResponseDTO<?> responseDTO = userService.login(loginDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
}
