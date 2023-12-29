package com.example.trelloproject.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSignupDto {
    @NotBlank(message = "이름을 입력해주세요.")
    @Size(min=4, max=10)
    @Pattern(regexp = "^[a-z0-9]*")
    private String username;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Size(min=8, max=15)
    @Pattern(regexp = "^[a-zA-Z0-9`~!@#$%^&*()\\-_=+\\|\\[\\]{};:'\",.<>/?]*")
    private String password;

    @Email
    @NotBlank(message = "이메일을 입력해주세요.")
    private String email;


    private boolean admin = false;

}
