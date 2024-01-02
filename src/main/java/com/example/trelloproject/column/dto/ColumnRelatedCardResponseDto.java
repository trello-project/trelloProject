package com.example.trelloproject.column.dto;

import com.example.trelloproject.card.entity.Card;
import com.example.trelloproject.column.entity.Columns;
import lombok.Getter;

import java.util.List;

@Getter
public class ColumnRelatedCardResponseDto {
    private String title;
    private List<Card> cards;

    public ColumnRelatedCardResponseDto(Columns columns) {
        this.title = columns.getTitle();
        this.cards = columns.getCards();
    }
}
