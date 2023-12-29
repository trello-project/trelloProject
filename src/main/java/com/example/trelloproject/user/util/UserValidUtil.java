package com.example.trelloproject.user.util;

import com.example.trelloproject.global.exception.DuplicateEmailException;
import com.example.trelloproject.global.exception.DuplicateUserException;
import com.example.trelloproject.user.dto.UserSignupDto;
import com.example.trelloproject.user.entity.User;
import com.example.trelloproject.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserValidUtil {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder; //SecurityConfig -> 필터에서 사용 금지

    public String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    public User getValidNewUserByRequestDto(UserSignupDto requestDto) {
        String username = requestDto.getUsername();
        String password = encodePassword(requestDto.getPassword());
        String email = requestDto.getEmail();

        // 회원 중복 확인
        Optional<User> checkUsername = userRepository.findByUsername(username);
        if (checkUsername.isPresent()) {
            throw new DuplicateUserException("중복된 사용자가 존재합니다.");
        }

        // email 중복확인
        Optional<User> checkEmail = userRepository.findByEmail(email);
        if (checkEmail.isPresent()) {
            throw new DuplicateEmailException("중복된 Email 입니다.");
        }

        return User.builder()
                .username(username)
                .password(password)
                .email(email)
                .build();
    }

    public boolean matchesPassword(String encodedPassword, String password) {
        return passwordEncoder.matches(password, encodedPassword);
    }


}
