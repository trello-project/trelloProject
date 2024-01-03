package com.example.trelloproject.global.security;


import com.example.trelloproject.global.exception.BanUserException;
import com.example.trelloproject.user.entity.User;
import com.example.trelloproject.user.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, BanUserException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("사용자 정보가 없습니다. : " + username));

        if (user.getIsBanned() != null && user.getIsBanned() == true) {
            if (user.getUnbannedAt() == null) {
                throw new BanUserException("차단된 사용자입니다. 관리자에게 문의해주세요.");
            }

            if (user.getUnbannedAt().isBefore(LocalDateTime.now())) {
                user.unban();
            } else {
                throw new BanUserException("일시적으로 접근이 제한된 사용자입니다. " + user.getUnbannedAt().toString() + " 이후에 시도해주세요.");
            }
        }

        return new UserDetailsImpl(user);
    }
    
}