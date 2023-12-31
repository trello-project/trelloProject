package com.example.trelloproject.column.entity;

import com.example.trelloproject.board.entity.Board;
import com.example.trelloproject.card.entity.Card;
import com.example.trelloproject.column.dto.ColumnRequestDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Columns {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    @OneToMany(mappedBy = "columns", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Card> cards = new LinkedHashSet<>();

    @Builder
    public Columns(String title, Set<Card> cards){
        this.title = title;
        this.cards = cards;
    }

    public void addCard(Card newCard){
        cards.add(newCard);
    }

    public void updateTitle(ColumnRequestDto columnRequestDto){
        this.title = columnRequestDto.getTitle();
    }
}
