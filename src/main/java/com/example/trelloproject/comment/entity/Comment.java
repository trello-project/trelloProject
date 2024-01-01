package com.example.trelloproject.comment.entity;

import com.example.trelloproject.card.entity.Card;
import com.example.trelloproject.global.entity.Timestamped;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Comment extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    private String writer;

    @ManyToOne
    @JoinColumn(name = "card_id")
    private Card card;

    @Builder
    public Comment(String content, String writer){
        this.content = content;
        this.writer = writer;
    }

    public void modify(String content){
        this.content = content;
    }
}
