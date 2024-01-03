package com.example.trelloproject.board.dto;

import com.example.trelloproject.board.entity.Board;
import com.example.trelloproject.global.constant.Color;

public class BoardResponseDto {

    private String title;
    private String content;
    private Color color;

    public BoardResponseDto(Board board) {
        this.title = board.getTitle();
        this.content = board.getContent();
        this.color =board.getColor();
    }
}
