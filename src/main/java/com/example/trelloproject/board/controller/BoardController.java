package com.example.trelloproject.board.controller;

import com.example.trelloproject.board.dto.BoardRequestDto;
import com.example.trelloproject.board.entity.Board;
import com.example.trelloproject.board.service.BoardService;
import com.example.trelloproject.global.dto.CommonResponseDto;
import com.example.trelloproject.global.security.UserDetailsImpl;
import com.example.trelloproject.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    //보드 생성
    @PostMapping
    private ResponseEntity<Board> createBoard(
            @RequestBody BoardRequestDto requestDto,
            @AuthenticationPrincipal User user) {
        Board board = boardService.createBoard(requestDto, user);
        return ResponseEntity.ok().body(board);
    }

    //보드 단건 조회
    @GetMapping("/{boardId}")
    private ResponseEntity<Board> getBoard(
            @PathVariable Long boardId) {
        Board board = boardService.getBoard(boardId);
        return ResponseEntity.ok().body(board);
    }

    //보드 전체 조회
    @GetMapping
    private ResponseEntity<CommonResponseDto<?>> getAllBoards(
            @PathVariable Long boardId) {
        List<Board> boardList = boardService.getAllBoards();
        return new ResponseEntity<>(new CommonResponseDto<>("message", boardList, HttpStatus.OK.value()), HttpStatus.OK);
    }

    //보드 수정
    @PutMapping("/{boardId}")
    private ResponseEntity<CommonResponseDto<?>> updateBoard(
            @PathVariable Long boardId,
            @RequestBody BoardRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        CommonResponseDto<?> responseDto = boardService.updateBoard(boardId, requestDto, userDetails.getUser());
        return ResponseEntity.ok().body(responseDto);
    }

    //보드 삭제
    @DeleteMapping("/{boardId}")
    private void deleteBoard(
            @PathVariable Long boardId,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        boardService.deleteBoard(boardId, userDetails.getUser());
    }

    // 보드 초대
    @PostMapping("/{boardId}") // /{boardId}?user={userId}
    private ResponseEntity<Void> inviteUserToBoard
    (@PathVariable Long boardId,
     @RequestParam("user") Long userId,
     @AuthenticationPrincipal UserDetailsImpl userDetails) throws IllegalAccessException {
        boardService.inviteUserToBoard(boardId, userId, userDetails.getUser());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 보드 초대 이메일 수락
    @GetMapping("/emailcheck")
    public ResponseEntity<Void> emailCheck(
            @RequestParam String email) throws IllegalAccessException {
        boardService.inviteConfirmation(email);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}