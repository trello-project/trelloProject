package com.example.trelloproject.board.entity;


import com.example.trelloproject.user.entity.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "user_board")
public class UserBoard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;

    // check #1 : isPresented
    private Boolean isAccepted = false;

    public void setAccepted(Boolean accepted) {
        isAccepted = accepted;
    }

    @Builder
    public UserBoard(Board board, User user) {
        this.board = board;
        this.user = user;
    }
}
