package com.example.trelloproject.card.dto;

import com.example.trelloproject.card.entity.CardBackgroundColor;
import lombok.Getter;

@Getter
public class CardBackgroundColorModifyDto {
    private CardBackgroundColor backgroundColor;

    public CardBackgroundColorModifyDto(CardBackgroundColor backgroundColor) {
        this.backgroundColor = backgroundColor;
    }
    public CardBackgroundColor getBackgroundColor() {
        return backgroundColor;
    }
}
