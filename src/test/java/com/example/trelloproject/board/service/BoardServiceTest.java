package com.example.trelloproject.board.service;

import com.example.trelloproject.board.dto.BoardColumnCardResponseDto;
import com.example.trelloproject.board.dto.BoardRequestDto;
import com.example.trelloproject.board.entity.Board;
import com.example.trelloproject.board.repository.BoardRepository;
import com.example.trelloproject.board.repository.UserBoardRepository;
import com.example.trelloproject.user.entity.User;
import com.example.trelloproject.user.repository.UserRepository;
import com.example.trelloproject.user.util.MailService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BoardServiceTest {

    @Mock
    BoardRepository boardRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    UserBoardRepository userBoardRepository;

    @Mock
    MailService mailService;

    @Test
    void createBoard(){
        //given
        User user = User.builder().username("User1").password("12345").build();
        BoardRequestDto requestDto = new BoardRequestDto("첫 보드","보드 테스트");

        BoardService boardService = new BoardService(boardRepository,userRepository,userBoardRepository,mailService);

        //when
        Board board = boardService.createBoard(requestDto,user);

        //then
        assertEquals(user.getUsername(), board.getUser().getUsername());
        assertEquals(requestDto.getTitle(), board.getTitle());
    }

    @Test
    void getBoard(){
        //given
        Board board1 = new Board("test1","test1111");
        User user = User.builder().username("User1").password("12345").build();
        board1.setUser(user);

        Long boardId = 1L;

        BoardService boardService = new BoardService(boardRepository,userRepository,userBoardRepository,mailService);

        //when

        when(boardRepository.findById(boardId)).thenReturn(Optional.of(board1));

        // then
        BoardColumnCardResponseDto retrievedBoard = boardService.getBoard(boardId);

        assertEquals(user.getUsername(), retrievedBoard.getUserName());
        assertEquals(board1.getTitle(), retrievedBoard.getTitle());

    }


}