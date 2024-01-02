package com.example.trelloproject.board.dto;

import com.example.trelloproject.board.entity.Board;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class BoardResDto {

    private String title;
    private String content;

    public BoardResDto(Board board) {
        this.title = board.getTitle();
        this.content = board.getContent();
    }
}
