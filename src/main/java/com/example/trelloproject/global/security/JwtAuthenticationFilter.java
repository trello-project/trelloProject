package com.example.trelloproject.global.security;

import com.example.trelloproject.global.exception.CustomHandleException;
import com.example.trelloproject.global.refreshToken.RefreshTokenService;
import com.example.trelloproject.user.dto.UserLoginDto;
import com.example.trelloproject.user.entity.User;
import com.example.trelloproject.user.entity.UserRoleEnum;
import com.example.trelloproject.user.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.time.LocalDateTime;

//로그인 필터
@Slf4j(topic = "로그인 및 JWT 생성")
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final int ALLOW_LOGIN_ATTEMPT_COUNT = 4;
    private final int LOGIN_BAN_MINUTES = 30;


    private final JwtUtil jwtUtil;

    private final UserRepository userRepository;

    private final RefreshTokenService refreshTokenService;


    public JwtAuthenticationFilter(JwtUtil jwtUtil, UserRepository userRepository, RefreshTokenService refreshTokenService) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.refreshTokenService = refreshTokenService;
        setFilterProcessesUrl("/v1/users/login");
    }

    //로그인 시도 메서드
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            UserLoginDto requestDto = new ObjectMapper().readValue(request.getInputStream(), UserLoginDto.class);
            log.info("로그인 시도 :" + requestDto.getUsername());

            try {
                return getAuthenticationManager().authenticate(
                        new UsernamePasswordAuthenticationToken(
                                requestDto.getUsername(),
                                requestDto.getPassword(),
                                null
                        )
                );
            } catch (BadCredentialsException e) {
                setFailInfoInUser(requestDto.getUsername());
                throw e;
            }
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }


    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {
        User user = ((UserDetailsImpl) authResult.getPrincipal()).getUser();
        String username = user.getUsername();
        UserRoleEnum role = user.getRole();

        user.resetLoginCount();
        userRepository.save(user);

        String accessToken = jwtUtil.createToken(username, role);
        String refreshToken = refreshTokenService.createRefreshToken(user);
        response.addHeader(JwtUtil.ACCESS_TOKEN_HEADER, accessToken);
        response.addHeader(JwtUtil.REFRESH_TOKEN_HEADER, refreshToken);

        response.setContentType("application/json; charset=UTF-8");
        response.getWriter().write("로그인에 성공하였습니다.");
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
        log.info("로그인 실패");
        response.setContentType("application/json; charset=UTF-8");
        response.getWriter().write("로그인에 실패하였습니다.");
        response.setStatus(401);
        if (failed.getCause() != null && failed.getCause().getClass().equals(CustomHandleException.class)) {
            response.getWriter().write(failed.getMessage());
//            response.setStatus(((CustomHandleException)failed.getCause()));
        }
    }

    private void setFailInfoInUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Not Found " + username));

        if (user.getLoginFailCount() >= ALLOW_LOGIN_ATTEMPT_COUNT) {
            user.resetLoginCount();
            user.banTemporary(LocalDateTime.now().plusMinutes(LOGIN_BAN_MINUTES));
        } else {
            user.updateLoginFailInfo();
        }

        userRepository.save(user);

    }
}