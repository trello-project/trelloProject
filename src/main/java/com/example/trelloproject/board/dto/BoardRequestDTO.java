package com.example.trelloproject.board.dto;

import lombok.Getter;

@Getter
public class BoardRequestDTO {
    private String title;
    private String content;

    public BoardRequestDTO(String title, String content){
        this.title = title;
        this.content = content;
    }
}
