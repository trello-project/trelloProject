package com.example.trelloproject.board.dto;

import com.example.trelloproject.board.entity.Board;
import lombok.Getter;

@Getter
public class BoardResponseDto {

    private String title;
    private String content;
    private String userName;

    public BoardResponseDto(Board board) {
        this.title = board.getTitle();
        this.content = board.getContent();
        this.userName = board.getUser().getUsername();
    }
}
