package com.example.trelloproject.user.dto;

import com.example.trelloproject.user.entity.UserRoleEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserSignupDto {
    @NotBlank(message = "아이디를 입력해주세요.")
    @Pattern(regexp = "^[a-z0-9]{4,10}")
    private final String username;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Pattern(regexp = "^[a-zA-Z0-9]{8,15}")
    private final String password;

    @Email
    @NotBlank(message = "이메일을 입력해주세요.")
    private String email;

    private String info;

    private UserRoleEnum role;

    @Builder
    public UserSignupDto(String username, String password, String email, String info, UserRoleEnum role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.info = info;
        this.role = role;
    }
}