package com.example.trelloproject.board.entity;

import com.example.trelloproject.board.dto.BoardRequestDto;
import com.example.trelloproject.column.entity.Columns;
import com.example.trelloproject.global.entity.Timestamped;
import com.example.trelloproject.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "board")
public class Board extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (nullable = false)
    private String title;

    private String content;

    @Enumerated(EnumType.STRING)
    private BackgroundColor backgroundColor;

    private enum BackgroundColor{
        PINK, GREEN, BLUE
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Columns> columns = new ArrayList<>();

    @OneToMany(mappedBy = "board")
    private List<UserBoard> invitedUsers = new ArrayList<>();

    public void setUser(User user) {
        this.user = user;
    }

    public Board(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void update(BoardRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
    }

    public void addColumn(Columns columns){
        this.columns.add(columns);
    }

    // 임시 테스트 메서드
    public List<UserBoard> getInvitedUsers() {
        return invitedUsers;
    }

    public void addInvitedUser(UserBoard userBoard) {
        invitedUsers.add(userBoard);
    }

    public void removeInvitedUser(UserBoard userBoard) {
        invitedUsers.remove(userBoard);
    }
}
