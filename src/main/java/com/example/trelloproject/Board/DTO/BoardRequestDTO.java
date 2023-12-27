package com.example.trelloproject.board.DTO;

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
