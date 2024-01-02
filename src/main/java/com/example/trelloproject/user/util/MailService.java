package com.example.trelloproject.user.util;

import com.example.trelloproject.board.entity.Board;
import com.example.trelloproject.board.entity.UserBoard;
import com.example.trelloproject.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MailService extends RuntimeException {

    private final JavaMailSender javaMailSender;

    @Transactional
    public void sendInvitation (User inviter, User invitee, Board board) {
        UserBoard invitation = new UserBoard(board, invitee);
        board.addInvitedUser(invitation);

        String subject = "짜잔~! 놀랐지!!!!!! 트렐로 보드에 초대합니당~!!😮❤️😮❤️😮❤️😮❤️😮";
        String message = inviter.getUsername() + " 님이 " + invitee.getUsername() + " 님을 보드에 초대했습니당! 축하드려요~!!😮🎉 \n" +
                         "초대를 수락하려면 링크를 클릭해주세요~!! \n" +
                         "[ " + "http://localhost:8080/v1/boards/emailcheck?email=" + invitee.getEmail() + " ]";
        sendInvitationEmail(invitee.getEmail(), subject, message);
    }
    private void sendInvitationEmail (String to, String subject, String message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(to);
        mailMessage.setSubject(subject); // 메일 제목
        mailMessage.setText(message); // 메일 본문
        javaMailSender.send(mailMessage);
    }
}
