package com.example.trelloproject.column.entity;

import com.example.trelloproject.board.entity.Board;
import com.example.trelloproject.card.entity.Card;
import com.example.trelloproject.column.dto.ColumnsRequestDto;
import com.example.trelloproject.global.entity.Timestamped;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Columns extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(name = "order_column")
    private int order;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    @JsonManagedReference
    @OneToMany(mappedBy = "columns", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Card> cards = new ArrayList<>();

    @Builder
    public Columns(Board board, String title, List<Card> cards, int order){
        this.board = board;
        this.title = title;
        this.cards = cards;
        this.order = order;
    }

    public void addCard(Card newCard){
        cards.add(newCard);
    }

    public void updateTitle(ColumnsRequestDto columnRequestDto){
        this.title = columnRequestDto.getTitle();
    }

    public void decreaseOrder() {
        if (order > 0) {
            order--;
        }
    }

    public void saveOrder(Integer newSequence) {
    }
}
