package com.example.trelloproject.board.entity;

import com.example.trelloproject.user.entity.User;
import com.example.trelloproject.board.dto.BoardRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "boards")
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (nullable = false)
    private String title;

    @Column
    private String content;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "users_id")
    private User user;

    public void setUser(User user) {
        this.user = user;
    }

    public Board(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void update(BoardRequestDto requestDTO) {
        this.title = requestDTO.getTitle();
        this.content = requestDTO.getContent();
    }
}
