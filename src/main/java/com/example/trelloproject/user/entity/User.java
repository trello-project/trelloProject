package com.example.trelloproject.user.entity;

import com.example.trelloproject.dto.UpdateProfileRequestDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "users_id")
    private Long id;

    @Column(length = 50, unique = true, nullable = false)
    private String username;

    @Column(length = 50, nullable = false)
    private String password;

    @Column(length = 50, unique = true, nullable = false)
    private String email;

    @Column(length = 100)
    private String info;

    @Column
    @Enumerated(EnumType.STRING)
    private UserRoleEnum role;

    @Builder
    public User(String username, String password, String email, String info, UserRoleEnum role) { // UserRoleEnum role
        this.username = username;
        this.password = password;
        this.email = email;
        this.info = info;
        this.role = role;
    }

    public void updateProfile (UpdateProfileRequestDto updateProfileRequestDto) {
        this.username = updateProfileRequestDto.getUsername();
        // 이따 꼭 적기~!
    }
}