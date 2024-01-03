package com.example.trelloproject.board.dto;

import com.example.trelloproject.global.constant.Color;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BoardBackgroundColorModifyDto {
    private Color color;

    public BoardBackgroundColorModifyDto(Color color) {
        this.color = color;
    }
    public Color getColor() {
        return color;
    }
}
