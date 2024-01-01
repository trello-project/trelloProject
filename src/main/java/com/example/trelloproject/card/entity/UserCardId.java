package com.example.trelloproject.card.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserCardId implements Serializable {

    @Serial
    private static final long serialVersionUID = 932813899396663626L;

    private Long user;

    private Long card;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserCardId usersCardsId = (UserCardId) o;
        return Objects.equals(getUser(), usersCardsId.getUser()) && Objects.equals(getCard(), usersCardsId.getUser());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUser(), getCard());
    }
}