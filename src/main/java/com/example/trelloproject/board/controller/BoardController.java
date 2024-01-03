package com.example.trelloproject.board.controller;

import com.example.trelloproject.board.dto.*;
import com.example.trelloproject.board.entity.Board;
import com.example.trelloproject.board.service.BoardService;
import com.example.trelloproject.card.dto.CardBackgroundColorModifyDto;
import com.example.trelloproject.card.dto.CardResponseDto;
import com.example.trelloproject.global.dto.CommonResponseDto;
import com.example.trelloproject.global.security.UserDetailsImpl;
import com.example.trelloproject.global.security.UserDetailsServiceImpl;
import com.example.trelloproject.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Board board = boardService.createBoard(requestDto, userDetails.getUser());
        return ResponseEntity.ok().body(board);
    }

    //보드 단건 조회
    @GetMapping("/{boardId}")
    private ResponseEntity<BoardColumnCardResponseDto> getBoard(@PathVariable Long boardId) {
        BoardColumnCardResponseDto responseDto = boardService.getBoard(boardId);
        return ResponseEntity.ok().body(responseDto);
    }

    //본인이 만든 보드 조회
    @GetMapping
    private ResponseEntity<List<BoardResDto>> getMyBoards(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<BoardResDto> myBoardList = boardService.getMyBoards(userDetails.getUser());
        return ResponseEntity.ok().body(myBoardList);
    }

    //보드 수정
    @PutMapping("/{boardId}")
    private ResponseEntity<CommonResponseDto<?>> updateBoard(@PathVariable Long boardId, @RequestBody BoardRequestDto requestDto, User user /*@AuthenticationPrincipal User user*/) {
        CommonResponseDto<?> responseDto = boardService.updateBoard(boardId, requestDto, user);
        return ResponseEntity.ok().body(responseDto);
    }

    //보드 삭제
    @DeleteMapping("/{boardId}")
    private void deleteBoard(@PathVariable Long boardId, User user/*@AuthenticationPrincipal User user*/) {
        boardService.deleteBoard(boardId, user);
    }

    // 보드 초대
    @PostMapping("/{boardId}") ///{boardId}?user={userId}
    private ResponseEntity<Void> inviteUserToBoard(@PathVariable Long boardId, @RequestParam("user") Long userId) {
        boardService.inviteUserToBoard(boardId, userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //보드 수락한 유저 확인
    @GetMapping("/{boardId}/invitedMember")
    private ResponseEntity<List<User>> checkMembers(@PathVariable Long boardId ) {
        List<User> userList = boardService.checkBoardMembers(boardId);
        return ResponseEntity.ok().body(userList);
    }

    @GetMapping("/emailcheck")
    public ResponseEntity<Void> emailCheck(@RequestParam String email) {
        boardService.inviteConfirmation(email);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //보드 배경색 변경
    @PatchMapping ("/{boardId}/boardColor")
    public ResponseEntity<BoardResponseDto> modifyBoardBackgroundColor(
            @RequestBody BoardBackgroundColorModifyDto cardBackgroundColorModifyDto,
            @PathVariable Long boardId) {
        BoardResponseDto responseDto = boardService.modifyBoardColor(boardId,cardBackgroundColorModifyDto);
        return ResponseEntity.ok().body(responseDto);
    }

}