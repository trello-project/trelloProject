package com.example.trelloproject.card.dto;

import com.example.trelloproject.card.entity.Card;
import com.example.trelloproject.card.entity.CardBackgroundColor;
import com.example.trelloproject.card.entity.UserCard;
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
    private CardBackgroundColor backgroundColor;
    private List<Comment> comments;
    private List<UserCard> assignees;

    public CardResponseDto(Card card){
        this.title = card.getTitle();
        this.writer = card.getWriter();
        this.content = card.getContent();
    }
}
