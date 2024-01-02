package com.example.trelloproject.board.dto;

import com.example.trelloproject.global.entity.BackgroundColor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BoardBackgroundColorModifyDto {
    private BackgroundColor backgroundColor;

    public BoardBackgroundColorModifyDto(BackgroundColor backgroundColor) {
        this.backgroundColor = backgroundColor;
    }
    public BackgroundColor getBackgroundColor() {
        return backgroundColor;
    }
}
