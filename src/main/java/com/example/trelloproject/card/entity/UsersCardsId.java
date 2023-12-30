package com.example.trelloproject.card.entity;

import jakarta.persistence.Column;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UsersCardsId implements Serializable {

    @Serial
    private static final long serialVersionUID = 932813899396663626L;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "card_id")
    private Long cardId;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UsersCardsId usersCardsId = (UsersCardsId) o;
        return Objects.equals(getUserId(), usersCardsId.getUserId()) && Objects.equals(getCardId(), usersCardsId.getUserId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserId(), getCardId());
    }
}
