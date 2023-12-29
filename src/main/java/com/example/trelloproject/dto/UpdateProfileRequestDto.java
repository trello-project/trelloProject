package com.example.trelloproject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateProfileRequestDto {
    private String username;
    private String email;
    private String info;
    private String changePassword;
    private String confirmPassword;
}
