package com.example.trelloproject.card.dto;

import com.example.trelloproject.card.entity.Card;
import com.example.trelloproject.global.constant.Color;
import com.example.trelloproject.comment.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CardResponseDto {
    private String title;
    private String content;
    private String writer;
    private Color backgroundColor;
    private List<Comment> comments;

    public CardResponseDto(Card card){
        this.title = card.getTitle();
        this.writer = card.getWriter();
        this.content = card.getContent();
        this.backgroundColor = card.getBackgroundColor();
        this.comments = card.getComments();
    }
}
