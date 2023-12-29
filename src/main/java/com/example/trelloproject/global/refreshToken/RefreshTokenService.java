package com.example.trelloproject.global.refreshToken;

import com.example.trelloproject.global.entity.RefreshToken;
import com.example.trelloproject.global.exception.TokenExpiredException;
import com.example.trelloproject.user.entity.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;

    // Refresh Token 만료시간
    @Value("${jwt.secret.refresh_token_expiry_ms}")
    private long REFRESH_TOKEN_TIME;

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public String createRefreshToken(User user) {
        RefreshToken refreshToken = refreshTokenRepository.findByUser(user).orElse(new RefreshToken());

        refreshToken.setUser(user);
        refreshToken.setExpiry(LocalDateTime.ofInstant(Instant.now().plusMillis(REFRESH_TOKEN_TIME), ZoneId.systemDefault()));
        refreshToken.setToken(UUID.randomUUID().toString());

        refreshTokenRepository.save(refreshToken);
        return refreshToken.getToken();
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiry().compareTo(LocalDateTime.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new TokenExpiredException("만료된 토큰입니다. 재인증이 필요합니다.");
        }

        return token;
    }

    @Transactional
    public int deleteByUserId(User user) {
        return refreshTokenRepository.deleteByUser(user);
    }
}