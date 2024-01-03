package com.example.trelloproject.card.dto;

import com.example.trelloproject.global.constant.Color;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CardBackgroundColorModifyDto {
    private Color backgroundColor;

    public Color getBackgroundColor() {
        return backgroundColor;
    }
}
