package com.example.trelloproject.user.service;


import com.example.trelloproject.global.refreshToken.RefreshTokenRepository;
import com.example.trelloproject.user.dto.UserResponseDto;
import com.example.trelloproject.user.dto.UserSignupDto;
import com.example.trelloproject.user.entity.User;
import com.example.trelloproject.user.entity.UserRoleEnum;
import com.example.trelloproject.user.repository.UserRepository;
import com.example.trelloproject.user.util.UserValidUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

//    public void acceptInvitation(User invitedUser, Board board) {
//        invitationService.acceptInvitation(invitedUser, board);
//    }
}
