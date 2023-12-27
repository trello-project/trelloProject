package com.example.trelloproject.board.dto;

import com.example.trelloproject.board.entity.Board;
import com.example.trelloproject.global.DTO.CommonResponseDTO;
import com.example.trelloproject.user.Entity.User;
import lombok.Getter;

@Getter
public class BoardResponseDto extends CommonResponseDTO {

    private String title;
    private String content;
    private String userName;

    public BoardResponseDto(Board board) {
        this.title = board.getTitle();
        this.content = board.getContent();
        this.userName = board.getUser().getUsername();
    }
}
