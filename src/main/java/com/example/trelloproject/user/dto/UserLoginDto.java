package com.example.trelloproject.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserLoginDto {
    @NotBlank(message = "이메일을 입력해주세요.")
//    @Pattern(regexp = "^[a-z0-9]{4,10}")
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Pattern(regexp = "^[a-zA-Z0-9]{8,15}")
    private final String password;

    @Builder
    public UserLoginDto(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
