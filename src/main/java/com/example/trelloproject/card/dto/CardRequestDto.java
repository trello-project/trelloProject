package com.example.trelloproject.card.dto;

import lombok.Getter;

@Getter
public class CardRequestDto {

    private String title;
    private String content;
    private String writer;
    private String backgroundColor;
}
