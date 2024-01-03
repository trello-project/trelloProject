package com.example.trelloproject.user.dto;

import com.example.trelloproject.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {
    private Long id;
    private String username;
    private String email;
    private String info;


    public UserResponseDto(User user) {
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.info = user.getInfo();
    }

    public UserResponseDto(String username, String email, String info) {
        this.username = username;
        this.email = email;
        this.info = info;
    }
}
