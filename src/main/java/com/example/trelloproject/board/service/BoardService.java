package com.example.trelloproject.board.service;

import com.example.trelloproject.board.dto.BoardRequestDto;
import com.example.trelloproject.board.entity.Board;
import com.example.trelloproject.board.entity.UserBoard;
import com.example.trelloproject.board.repository.BoardRepository;
import com.example.trelloproject.board.repository.UserBoardRepository;
import com.example.trelloproject.global.dto.CommonResponseDto;
import com.example.trelloproject.user.entity.User;
import com.example.trelloproject.user.repository.UserRepository;
import com.example.trelloproject.user.util.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BoardService {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final UserBoardRepository userBoardRepository;
    private final MailService mailService;

    public Board createBoard(BoardRequestDto requestDto, User user) {
        Board board = new Board(requestDto.getTitle(), requestDto.getContent());
        board.setUser(user);
        boardRepository.save(board);
        return board;
    }

    public Board getBoard(Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 보드입니다.")
        );
        return board;
    }

    public List<Board> getAllBoards() {
        List<Board> boardList = boardRepository.findAll();
        return boardList;
    }

    @Transactional
    public CommonResponseDto<?> updateBoard(Long boardId, BoardRequestDto requestDto, User user) {
        Board board = boardRepository.findById(boardId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 보드입니다.")
        );

        if (!user.equals(board.getUser())) {
            throw new IllegalArgumentException("보드 생성자만 수정할 수 있습니다.");
        }

        board.update(requestDto);
        boardRepository.save(board);
        return new CommonResponseDto<>("message", board, HttpStatus.OK.value());
    }

    public void deleteBoard(Long boardId, User user) {
        Board board = boardRepository.findById(boardId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 보드입니다.")
        );

        if (!user.equals(board.getUser())) {
            throw new IllegalArgumentException("보드 생성자만 수정할 수 있습니다.");
        }
        boardRepository.delete(board);
    }

    public void inviteUserToBoard(Long boardId, Long userId, User inviter) throws IllegalAccessException {
        Board board = boardRepository.findById(boardId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 보드입니다.")
        );

        if (!inviter.getUsername().equals(board.getUser().getUsername())) {
            throw new IllegalAccessException("보드 생성자가 아닙니다.");
        }

        User invitee = userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 유저입니다.")
        );
        mailService.sendInvitation(inviter, invitee, board);
        UserBoard userBoard = new UserBoard(board, invitee);
        userBoardRepository.save(userBoard);
    }

    @Transactional
    public void inviteConfirmation(String email) throws IllegalAccessException {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new IllegalAccessException("초대한 유저가 아닙니다.")
        );

        UserBoard userBoard = userBoardRepository.findByUserId(user.getId()).orElseThrow(
                () -> new IllegalArgumentException("초대 수락 처리 중 에러가 발생했습니다.")
        );

        userBoard.setAccepted(true);
    }
}