package com.example.trelloproject.board.controller;

import com.example.trelloproject.board.dto.BoardRequestDto;
import com.example.trelloproject.board.dto.BoardResponseDto;
import com.example.trelloproject.board.entity.Board;
import com.example.trelloproject.board.service.BoardService;
import com.example.trelloproject.global.DTO.CommonResponseDTO;
import com.example.trelloproject.user.Entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;


    //보드 생성
    @PostMapping
    private ResponseEntity<CommonResponseDTO> createBoard(@RequestBody BoardRequestDto requestDto, @AuthenticationPrincipal User user){
        BoardResponseDto responseDto = boardService.createBoard(requestDto, user.getUser());
        return ResponseEntity.ok().body(responseDto);
    }

    //보드 단건 조회
    @GetMapping ("/{boardId}")
    private ResponseEntity<CommonResponseDTO> getBoard(@PathVariable Long boardId){
       BoardResponseDto responseDto= boardService.getBoard(boardId);
        return ResponseEntity.ok().body(responseDto);
    }

    //보드 전체 조회
    @GetMapping
    private ResponseEntity<List<Board>> getAllBoards(@PathVariable Long boardId){
        List<Board> boardList= boardService.getAllBoards();
        return ResponseEntity.ok().body(boardList);
    }

    //보드 수정
    @PutMapping("/{boardId}")
    private ResponseEntity<CommonResponseDTO> updateBoard(@PathVariable Long boardId ,@RequestBody BoardRequestDto requestDto, @AuthenticationPrincipal  User user){
        BoardResponseDto responseDto = boardService.updateBoard(boardId, requestDto, user);
        return ResponseEntity.ok().body(responseDto);
    }

    //보드 삭제
    @DeleteMapping ("/{boardId}")
    private void deleteBoard(@PathVariable Long boardId, @AuthenticationPrincipal  User user){
        boardService.deletBoard(boardId, user);
    }

    //보드 초대
    @GetMapping ("/{boardId}") ///{boardId}?user={userId}
    private void inviteUserToBoard(@PathVariable Long boardId, @RequestParam  ("user") Long userId){
        boardService.inviteUserToBoard (boardId, userId);


    }




}
