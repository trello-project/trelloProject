package com.example.trelloproject.user.dto;

import com.example.trelloproject.user.entity.User;
import lombok.Builder;
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
