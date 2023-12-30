package com.example.trelloproject.user.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public record SignupDto(String username, String password, String email){

    @Builder
    public User toEntity(String username, String password, String email){
        return User.builder()
                .username(username)
                .password(password)
                .email(email)
                .build();
    }
}
