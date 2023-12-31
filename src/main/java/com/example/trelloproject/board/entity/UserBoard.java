package com.example.trelloproject.board.entity;


import com.example.trelloproject.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "users_boards")
public class UserBoard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    private Boolean isAccepted;

    public void setAccepted(Boolean accepted) {
        isAccepted = accepted;
    }

    public UserBoard(Board board, User user) {
        this.board = board;
        this.user = user;
        this.isAccepted = false;
    }
}
