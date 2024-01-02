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
@IdClass(UserCardId.class)
public class UserCard {

    @Id
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name = "card_id")
    private Card card;

    @Builder
    public UserCard(User user, Card card){
        this.user = user;
        this.card = card;
    }
}