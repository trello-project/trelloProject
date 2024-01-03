package com.example.trelloproject.user.util;
import com.example.trelloproject.board.entity.Board;
import com.example.trelloproject.board.entity.UserBoard;
import com.example.trelloproject.global.exception.CustomHandleException;
import com.example.trelloproject.user.entity.User;
import jakarta.mail.SendFailedException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender javaMailSender;

    @Transactional
    public void sendInvitation (User invitee, Board board) {
        UserBoard invitation = new UserBoard(board, invitee);
        board.addInvitedUser(invitation);

        // Send invitation email

        String subject = "트렐로 보드에 초대합니당~!!😮❤️😮❤️😮❤️😮❤️😮";
        // message
        String message = "http://localhost:8080/v1/boards/emailcheck?email=" + invitee.getEmail();
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
