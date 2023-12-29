package com.example.trelloproject.user.service;

import com.example.trelloproject.user.dto.UserLoginDto;
import com.example.trelloproject.user.dto.UserSignupDto;
import com.example.trelloproject.user.entity.User;
import com.example.trelloproject.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;

    public void signup(UserSignupDto userSignupRequestDto) {
        String username = userSignupRequestDto.getUsername();
        String email = userSignupRequestDto.getEmail();
        String password = bCryptPasswordEncoder.encode(userSignupRequestDto.getPassword());
        // String password = userRequestDto.getPassword();

        // 회원 중복 확인
        if (userRepository.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 유저입니다.");
        }

        // 이메일 중복 확인
        if (userRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }

        // 한 줄 소개
        String info = userSignupRequestDto.getInfo();

        User user = new User(username, password, email, info);
        userRepository.save(user);
    }

    public void login(UserLoginDto userLoginDto) {
        String email = userLoginDto.getEmail();
        String password = userLoginDto.getPassword();

        User user = userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("등록된 유저가 없습니다."));

        if (!bCryptPasswordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // 사용자 등록
        User user1 = User.builder()
                .email(email)
                .password(password)
                .build();
        userRepository.save(user1);
    }
}
