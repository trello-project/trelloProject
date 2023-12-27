package com.example.trelloproject.user.Service;

import com.example.trelloproject.global.DTO.CommonResponseDTO;
import com.example.trelloproject.global.Exception.NotFoundElementException;
import com.example.trelloproject.user.DTO.LoginDTO;
import com.example.trelloproject.user.DTO.SignupDTO;
import com.example.trelloproject.user.Entity.User;
import com.example.trelloproject.user.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public CommonResponseDTO<?> signup(SignupDTO signupDTO) {
        userRepository.findByUsername(signupDTO.username()).orElseThrow(
                ()-> new NotFoundElementException("해당 유저 네임이 존재하지 않습니다.")
        );
        userRepository.findByEmail(signupDTO.email()).orElseThrow(
                ()-> new NotFoundElementException("해당 유저의 이메일이 존재하지 않습니다.")
        );

        String bcrytPassowrd = passwordEncoder.encode(signupDTO.password());
        User userEntity = User.builder()
                .username(signupDTO.username())
                .password(bcrytPassowrd)
                .email(signupDTO.email())
                .build();

        userRepository.save(userEntity);
        return new CommonResponseDTO<>("회원 가입에 성공하셨습니다.", 200);
    }

    public CommonResponseDTO<?> login(LoginDTO loginDTO) {
        // security 처리를 할지? 안할지?
        userRepository.findByUsername(loginDTO.getUsername()).orElseThrow();

        return new CommonResponseDTO<>("로그인에 성공하셨습니다.", 200);
    }

    // userinfo?
}
