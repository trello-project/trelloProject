package com.example.trelloproject.User.DTO;

import com.example.trelloproject.User.Entity.User;
import lombok.Builder;
import lombok.Getter;

@Getter
public record SignupDTO(String username, String password, String email){

    @Builder
    public User toEntity(String username, String password, String email){
        return User.builder()
                .username(username)
                .password(password)
                .email(email)
                .build();
    }
}
