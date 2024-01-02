package com.example.trelloproject.board.dto;

import com.example.trelloproject.board.entity.Board;
import com.example.trelloproject.global.entity.BackgroundColor;

public class BoardResponseDto {


    private String title;
    private String content;
    private BackgroundColor backgroundColor;

    public BoardResponseDto(Board board) {
        this.title = board.getTitle();
        this.content = board.getContent();
        this.backgroundColor =board.getBackgroundColor();
    }
}
