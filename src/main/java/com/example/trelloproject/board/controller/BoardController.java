package com.example.trelloproject.board.controller;

import com.example.trelloproject.board.dto.BoardRequestDto;
import com.example.trelloproject.board.dto.BoardResponseDto;
import com.example.trelloproject.board.entity.Board;
import com.example.trelloproject.board.service.BoardService;
import com.example.trelloproject.global.dto.CommonResponseDto;
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

}