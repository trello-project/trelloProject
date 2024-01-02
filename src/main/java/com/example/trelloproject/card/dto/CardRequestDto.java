package com.example.trelloproject.card.dto;

import com.example.trelloproject.card.entity.Card;
import com.example.trelloproject.card.entity.CardBackgroundColor;
import lombok.Getter;

@Getter
public class CardRequestDto {

    private String title;
    private String content;

}
