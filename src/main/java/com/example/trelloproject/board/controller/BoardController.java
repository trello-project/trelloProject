package com.example.trelloproject.board.controller;

import com.example.trelloproject.board.dto.BoardRequestDto;
import com.example.trelloproject.board.entity.Board;
import com.example.trelloproject.board.service.BoardService;
import com.example.trelloproject.global.dto.CommonResponseDTO;
import com.example.trelloproject.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    private ResponseEntity<CommonResponseDTO<?>> createBoard(@RequestBody BoardRequestDto requestDto,User user /*@AuthenticationPrincipal User user*/) {
        CommonResponseDTO<?> responseDto = boardService.createBoard(requestDto, user);
        return ResponseEntity.ok().body(responseDto);
    }

    //보드 단건 조회
    @GetMapping("/{boardId}")
    private ResponseEntity<CommonResponseDTO<?>> getBoard(@PathVariable Long boardId) {
        CommonResponseDTO<?> responseDto = boardService.getBoard(boardId);
        return ResponseEntity.ok().body(responseDto);
    }

    //보드 전체 조회
    @GetMapping
    private ResponseEntity<CommonResponseDTO<?>> getAllBoards(@PathVariable Long boardId) {
        List<Board> boardList = boardService.getAllBoards();
        return new ResponseEntity<>(new CommonResponseDTO<>("message",boardList,HttpStatus.OK.value()), HttpStatus.OK);
    }

    //보드 수정
    @PutMapping("/{boardId}")
    private ResponseEntity<CommonResponseDTO<?>> updateBoard(@PathVariable Long boardId, @RequestBody BoardRequestDto requestDto,User user /*@AuthenticationPrincipal User user*/) {
        CommonResponseDTO<?> responseDto = boardService.updateBoard(boardId, requestDto, user);
        return ResponseEntity.ok().body(responseDto);
    }

    //보드 삭제
    @DeleteMapping("/{boardId}")
    private void deleteBoard(@PathVariable Long boardId, User user/*@AuthenticationPrincipal User user*/) {
        boardService.deletBoard(boardId, user);
    }

    //보드 초대
    @PostMapping("/{boardId}") ///{boardId}?user={userId}
    private ResponseEntity<CommonResponseDTO<?>> inviteUserToBoard(@PathVariable Long boardId, @RequestParam("user") Long userId) {
        CommonResponseDTO<?> responseDTO = boardService.inviteUserToBoard(boardId, userId);
        return ResponseEntity.ok().body(responseDTO);
    }

}
