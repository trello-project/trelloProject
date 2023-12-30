package com.example.trelloproject.column.entity;

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
public class Column {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @OneToMany(mappedBy = "card", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Card> cards = new LinkedHashSet<>();

    @Builder
    public Column(String title, Set<Card> cards){
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
