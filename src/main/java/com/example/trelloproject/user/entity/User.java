package com.example.trelloproject.user.entity;


import com.example.trelloproject.dto.UpdateProfileRequestDto;
import com.example.trelloproject.global.util.StringListConverter;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, unique = true, nullable = false)
    private String username;

    @Column(length = 50, nullable = false)
    private String password;

    @Column(length = 50, unique = true, nullable = false)
    private String email;

    @Column(length = 300)
    private String info;

    private Long loginFailCount = 0L;

    private Boolean isBanned = false;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime unbannedAt;

    @Convert(converter = StringListConverter.class)
    private List<String> beforePassword = new ArrayList<>(); // 지금 암호 + 기존 3개암호

    @Column(nullable = false)
    @NotNull
    @Enumerated(EnumType.STRING)
    private UserRoleEnum role = UserRoleEnum.USER;

    @Transient
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private final int MAX_BEFORE_PASSWORD_SIZE = 4;

    @Builder
    public User(String username, String password, String email, String info, UserRoleEnum role) { // UserRoleEnum role
        this.username = username;
        this.password = password;
        this.email = email;
        this.info = info;
        this.role = role;
    }

    public void changeRole(UserRoleEnum role) {
        this.role = role;
    }

    public void modifyProfile(UpdateProfileRequestDto updateProfileRequestDto) {
        this.username = updateProfileRequestDto.getUsername();
        this.info = updateProfileRequestDto.getInfo();
        // 이따 꼭 적기~!
    }

    public Long updateLoginFailInfo() {
        if (loginFailCount == null) {
            return loginFailCount = 1L;
        }
        return loginFailCount++;
    }

    public void resetLoginCount() {
        this.loginFailCount = 0L;
    }

    public void updatePassword(String password) {
        if (beforePassword.size() == MAX_BEFORE_PASSWORD_SIZE) {
            beforePassword.remove(0);
        } else if (beforePassword.size() > MAX_BEFORE_PASSWORD_SIZE) {
            beforePassword = beforePassword.subList(beforePassword.size() - MAX_BEFORE_PASSWORD_SIZE + 1, beforePassword.size());
        }
        this.beforePassword.add(password);
        this.password = password;
    }

    public void ban() {
        this.isBanned = true;
        this.unbannedAt = null;
    }

    public void banTemporary(LocalDateTime banTime) {
        this.unbannedAt = banTime;
        this.isBanned = true;
    }

    public void unban() {
        this.isBanned = false;
    }

}