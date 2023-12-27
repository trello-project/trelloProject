package com.example.trelloproject.Board.DTO;

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
