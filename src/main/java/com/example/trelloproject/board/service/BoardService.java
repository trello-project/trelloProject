package com.example.trelloproject.board.service;

import com.example.trelloproject.board.dto.BoardBackgroundColorModifyDto;
import com.example.trelloproject.board.dto.BoardColumnCardResponseDto;
import com.example.trelloproject.board.dto.BoardRequestDto;
import com.example.trelloproject.board.dto.BoardResponseDto;
import com.example.trelloproject.board.entity.Board;
import com.example.trelloproject.board.entity.UserBoard;
import com.example.trelloproject.board.repository.BoardRepository;
import com.example.trelloproject.board.repository.UserBoardRepository;
import com.example.trelloproject.card.dto.CardBackgroundColorModifyDto;
import com.example.trelloproject.global.dto.CommonResponseDto;
import com.example.trelloproject.global.exception.NotFoundUserException;
import com.example.trelloproject.user.entity.User;
import com.example.trelloproject.user.repository.UserRepository;
import com.example.trelloproject.user.util.MailService;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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

    public BoardColumnCardResponseDto getBoard(Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 보드 입니다."));
        BoardColumnCardResponseDto responseDto = new BoardColumnCardResponseDto(board);
        return responseDto;
    }

    public List<Board> getMyBoards(User user) {
        List<Board> myBoardList = boardRepository.findByUser(user);
      return myBoardList;
    }



    @Transactional
    public CommonResponseDto<?> updateBoard(Long boardId, BoardRequestDto requestDto, User user) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 보드입니다."));

        if (!user.equals(board.getUser())) {
            throw new IllegalArgumentException("보드 생성자만 수정할 수 있습니다.");
        }

        board.update(requestDto);
        boardRepository.save(board);
        return new CommonResponseDto<>("message", board, HttpStatus.OK.value());
    }

    public void deleteBoard(Long boardId, User user) {
        Board board1 = boardRepository.findById(boardId).orElse(null);

        Board board = boardRepository.findById(boardId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 보드입니다."));

        if (!user.equals(board.getUser())) {
            throw new IllegalArgumentException("보드 생성자만 수정할 수 있습니다.");
        }
        boardRepository.delete(board);
    }

    public void inviteUserToBoard(Long boardId, Long userId) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 보드입니다."));
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));

        // 찾기 잘하고 있어?
        mailService.sendInvitation(user, board); // 이메일 보내기

        UserBoard userBoard = new UserBoard(board, user);
        userBoardRepository.save(userBoard);
        // return new BoardResponseDto(board);
    }

    @Transactional
    public void inviteConfirmation (String email) {
        // userboard
        // email같은 user찾아..
        // 회원가입을 하고 로그인을 해 -> 보드를 만들어요?
        // 내가 아닌 타인을 초대해야돼
        // 내가 나를 초대하고 있어

        User user = userRepository.findByEmail(email).orElse(null);
        if(user == null){

        }

        // 찾았어. userBoard에서
        UserBoard userBoard = userBoardRepository.findByUserId(user.getId()).orElseThrow(
                ()-> new IllegalArgumentException("테스트")
        );

        // 여기서 찾았어
        userBoard.setAccepted(true);
        userBoardRepository.save(userBoard);
    }


    //보드 초대 멤버 확인
    public List<User> checkBoardMembers(Long boardId) {
        List<User> acceptedUserList = userBoardRepository.findByBoardIdAndIsAccepted(boardId,true);
        return acceptedUserList;
    }

    //보드 배경색 수정
    @Transactional
    public BoardResponseDto modifyBoardColor(Long boardId, BoardBackgroundColorModifyDto boardBackgroundColorModifyDto){
        Board board = boardRepository.findById(boardId).orElseThrow(()->new IllegalArgumentException("존재하지 않는 보드입니다."));
        board.updateColor(boardBackgroundColorModifyDto);
        boardRepository.save(board);
        return new BoardResponseDto(board);
    }


}
