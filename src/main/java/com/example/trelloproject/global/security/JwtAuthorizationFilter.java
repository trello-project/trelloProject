package com.example.trelloproject.global.security;


import com.example.trelloproject.global.entity.RefreshToken;
import com.example.trelloproject.global.exception.CustomHandleException;
import com.example.trelloproject.global.exception.TokenNotExistException;
import com.example.trelloproject.global.refreshToken.RefreshTokenService;
import com.example.trelloproject.user.entity.User;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j(topic = "JWT 검증 및 인가")
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    private final UserDetailsServiceImpl userDetailsService;

    private final RefreshTokenService refreshTokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain) throws ServletException, IOException {
        String accessTokenValue = jwtUtil.getJwtFromHeader(req);

        String refreshTokenValue = req.getHeader(jwtUtil.REFRESH_TOKEN_HEADER);

        try {
            if (StringUtils.hasText(accessTokenValue)) {
                // JWT 토큰
                if (jwtUtil.validateToken(accessTokenValue)) {
                    Claims info = jwtUtil.getUserInfoFromToken(accessTokenValue);
                    setAuthentication(info.getSubject());
                } else if (StringUtils.hasText(refreshTokenValue)) {
                    RefreshToken refreshToken = refreshTokenService.findByToken(refreshTokenValue).orElseThrow(
                            () -> new TokenNotExistException("토큰 정보가 없습니다.")
                    );

                    //TODO Access 토큰과 비교해 부정한 사용자인지 확인하면 더 좋을듯

                    //유효 검사
                    refreshToken = refreshTokenService.verifyExpiration(refreshToken);

                    // 유효하면 JWT 토큰 재발급
                    User user = refreshToken.getUser();
                    String accessToken = jwtUtil.createToken(user.getUsername(), user.getRole());
                    res.addHeader(JwtUtil.ACCESS_TOKEN_HEADER, accessToken);

                    setAuthentication(user.getUsername());
                }
            }
        } catch (UsernameNotFoundException | CustomHandleException e) {
            res.setContentType("application/json; charset=UTF-8");
            res.setStatus(HttpStatus.UNAUTHORIZED.value());
            res.getWriter().write(e.getMessage());
            return;
        } catch (Exception e) {
            res.setContentType("application/json; charset=UTF-8");
            res.setStatus(HttpStatus.UNAUTHORIZED.value());
            log.error(e.getMessage());
            return;
        }

        filterChain.doFilter(req, res);
    }

    public void setAuthentication(String username) throws UsernameNotFoundException, CustomHandleException {

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication = createAuthentication(username);
        context.setAuthentication(authentication);

        SecurityContextHolder.setContext(context);
    }

    private Authentication createAuthentication(String username) throws UsernameNotFoundException, CustomHandleException  {

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}