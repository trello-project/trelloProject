package com.example.trelloproject.column.entity;

import com.example.trelloproject.card.entity.Card;
import com.example.trelloproject.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "users_cards")
public class UserCard {

    @EmbeddedId
    private UserCardId userCardId;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("users_id")
    @JoinColumn(name = "users_id", referencedColumnName = "id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("cards_id")
    @JoinColumn(name = "cards_id", referencedColumnName = "id")
    private Card card;

}
