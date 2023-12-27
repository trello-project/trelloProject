package com.example.trelloproject.column.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class UserCardId implements Serializable {

    @Serial
    private static final long serialVersionUID = -9022340478239047368L;

    @Column(name = "users_id")
    private Long userId;

    @Column(name = "cards_id")
    private Long cardId;

}
