package com.example.trelloproject.user.service;


import com.example.trelloproject.dto.UpdateProfileRequestDto;
import com.example.trelloproject.global.refreshToken.RefreshTokenRepository;
import com.example.trelloproject.user.dto.*;
import com.example.trelloproject.user.entity.User;
import com.example.trelloproject.user.entity.UserRoleEnum;
import com.example.trelloproject.user.repository.UserRepository;
import com.example.trelloproject.user.util.UserValidUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserValidUtil userValidUtil;

    public UserResponseDto getInfo(User user) {
        return new UserResponseDto(user);
    }
    public UserResponseDto signup(UserSignupDto requestDto) {

        User user = userValidUtil.getValidNewUserByRequestDto(requestDto);
        user.changeRole(UserRoleEnum.USER);

        // 사용자 등록
        userRepository.save(user);
        return new UserResponseDto(user);
    }
}
