package com.example.trelloproject.board.entity;

import com.example.trelloproject.board.dto.BoardRequestDto;
import com.example.trelloproject.global.entity.Timestamped;
import com.example.trelloproject.user.entity.User;
import com.example.trelloproject.column.entity.Column;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "boards")
public class Board extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //@Column(nullable = false)
    private String title;

    private String content;

    @Enumerated(EnumType.STRING)
    private BackgroundColor backgroundColor;

    private enum BackgroundColor{
        PINK, GREEN, BLUE
    }

    // 이거 중간 테이블 아님???
    // 일단 냅둬
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "users_id")
    private User user;

    @OneToMany(mappedBy = "card", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Column> columns = new LinkedHashSet<>();

    @OneToMany(mappedBy = "board")
    private Set<UserBoard> invitedUsers = new LinkedHashSet<>();

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

    public void addColumn(Column column){
        columns.add(column);
    }

    // 임시 테스트 메서드
    public Set<UserBoard> getInvitedUsers() {
        return invitedUsers;
    }

    public void addInvitedUser(UserBoard userBoard) {
        invitedUsers.add(userBoard);
    }

    public void removeInvitedUser(UserBoard userBoard) {
        invitedUsers.remove(userBoard);
    }
}
