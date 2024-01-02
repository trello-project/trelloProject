package com.example.trelloproject.card.dto;

import com.example.trelloproject.card.entity.CardBackgroundColor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CardBackgroundColorModifyDto {
    private CardBackgroundColor backgroundColor;

    public CardBackgroundColor getBackgroundColor() {
        return backgroundColor;
    }
}
