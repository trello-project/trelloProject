package com.example.trelloproject.board.service;

import com.example.trelloproject.board.dto.BoardRequestDTO;
import com.example.trelloproject.board.dto.BoardResponseDto;
import com.example.trelloproject.board.entity.Board;
import com.example.trelloproject.board.entity.UserBoard;
import com.example.trelloproject.board.repository.BoardRepository;
import com.example.trelloproject.board.repository.UserBoardRepository;
import com.example.trelloproject.global.dto.CommonResponseDTO;
import com.example.trelloproject.user.entity.User;
import com.example.trelloproject.user.repository.UserRepository;
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

    public Board createBoard(BoardRequestDTO requestDTO, User user) {
        Board board = new Board(requestDTO.getTitle(), requestDTO.getContent());
        board.setUser(user);
        boardRepository.save(board);
        return board;
    }

    public Board getBoard(Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 보드 입니다."));
        return board;
    }

    public List<Board> getAllBoards() {
        List<Board> boardList = boardRepository.findAll();
        return boardList;
    }

    @Transactional
    public CommonResponseDTO<?> updateBoard(Long boardId, BoardRequestDTO requestDTO, User user) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 보드입니다."));

        if (!user.equals(board.getUser())) {
            throw new IllegalArgumentException("보드 생성자만 수정할 수 있습니다.");
        }

        board.update(requestDTO);
        boardRepository.save(board);
        return new CommonResponseDTO<>("message", board, HttpStatus.OK.value());


    }

    public void deleteBoard(Long boardId, User user) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 보드입니다."));

        if (!user.equals(board.getUser())) {
            throw new IllegalArgumentException("보드 생성자만 수정할 수 있습니다.");
        }
        boardRepository.delete(board);
    }

    public BoardResponseDto inviteUserToBoard(Long boardId, Long userId) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 보드입니다."));
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));

        UserBoard userBoard = new UserBoard(board, user);
        userBoardRepository.save(userBoard);
        return new BoardResponseDto(board);
    }
}
