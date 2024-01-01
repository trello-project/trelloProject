package com.example.trelloproject.card.entity;

import com.example.trelloproject.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@IdClass(UsersCardsId.class)
public class UsersCards {

//    @Getter(AccessLevel.NONE)
//    @Column(name = "id", nullable = false)
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;

    @Id
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userId;

    @Id
    @ManyToOne
    @JoinColumn(name = "card_id")
    private Card cardId;

    @Builder
    public UsersCards(User user, Card card){
        this.userId = user;
        this.cardId = card;
    }
}