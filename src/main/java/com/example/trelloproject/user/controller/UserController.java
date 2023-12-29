package com.example.trelloproject.user.controller;

import com.example.trelloproject.global.exception.RequestValidationFailException;
import com.example.trelloproject.user.dto.UserSignupDto;
import com.example.trelloproject.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/users")
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity signup(
            @Valid @RequestBody UserSignupDto userSignupDto,
            BindingResult bindingResult
    ) {
        // Validation 예외처리
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        if (!fieldErrors.isEmpty()) {
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                throw new RequestValidationFailException(
                        fieldError.getField() + " 필드 : " + fieldError.getDefaultMessage()
                );
            }
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(userService.signup(userSignupDto));
    }
}
