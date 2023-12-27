package com.example.trelloproject.board.service;

import com.example.trelloproject.board.repository.BoardRepository;
import com.example.trelloproject.board.entity.Board;
import com.example.trelloproject.user.Entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.trelloproject.board.dto.BoardRequestDto;
import com.example.trelloproject.board.dto.BoardResponseDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@RequiredArgsConstructor
@Service
public class BoardService {
    private final BoardRepository boardRepository;

    private BoardResponseDto createBoard(BoardRequestDto requestDTO, User user){
        Board board = new Board(requestDTO.getTitle(), requestDTO.getContent());
        board.setUser(user);
        boardRepository.save(board);
        return new BoardResponseDto(board);
    }

    public BoardResponseDto getBoard(Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(()->new IllegalArgumentException("존재하지 않는 보드 입니다."));
        return new BoardResponseDto(board);
    }

    public List<Board> getAllBoards() {
        List<Board> boardList = boardRepository.findAll();
        return boardList;
    }

    @Transactional
    public BoardResponseDto updateBoard(Long boardId ,BoardRequestDto requestDTO, User user) {
        Board board = boardRepository.findById(boardId).orElseThrow(()->new IllegalArgumentException("존재하지 않는 보드입니다."));

        if (!user.equals(board.getUser())){
            throw new IllegalArgumentException("보드 생성자만 수정할 수 있습니다.");
        }

        board.update(requestDTO);
        boardRepository.save(board);
        return new BoardResponseDto(board);


    }

    public void deletBoard(Long boardId, User user) {
        Board board = boardRepository.findById(boardId).orElseThrow(()->new IllegalArgumentException("존재하지 않는 보드입니다."));

        if (!user.equals(board.getUser())){
            throw new IllegalArgumentException("보드 생성자만 수정할 수 있습니다.");
        }
        boardRepository.delete(board);
    }

    public void inviteUserToBoard(Long boardId, Long userId) {
        Board board = boardRepository.findById(boardId).orElseThrow(()->new IllegalArgumentException("존재하지 않는 보드입니다."));
        User user = userRepositoory.findById(userId).orElseThrow(()->new IllegalArgumentException("존재하지 않는 유저입니다."));

        Invite invite = new Invite(board, user);
        inviteRepository.save(invite);


    }
}
