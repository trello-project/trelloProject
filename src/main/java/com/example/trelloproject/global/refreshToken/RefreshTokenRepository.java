package com.example.trelloproject.global.refreshToken;

import com.example.trelloproject.global.entity.RefreshToken;
import com.example.trelloproject.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
//    RefreshToken findByUsername(String username);
    Optional<RefreshToken> findByToken(String token);
    Optional<RefreshToken> findByUser(User user);
    int deleteByUser(User user);
}
