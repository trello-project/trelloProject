package com.example.trelloproject.board.entity;

import com.example.trelloproject.board.dto.BoardBackgroundColorModifyDto;
import com.example.trelloproject.board.dto.BoardRequestDto;
import com.example.trelloproject.global.entity.BackgroundColor;
import com.example.trelloproject.column.entity.Columns;
import com.example.trelloproject.global.entity.Timestamped;
import com.example.trelloproject.user.entity.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

// 유저A가 유저B, C 를 보드에 초대
// 유저C는 수락
// 초대 연락을 이메일로 받고 이메일에서 링크를 클릭했을 때 수락이 되도록?
@Entity
@Getter
@NoArgsConstructor
@Table(name = "board")
public class Board extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String content;

    @Enumerated(EnumType.STRING)
    private BackgroundColor backgroundColor;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    // title content, backgroundColor ....
    // user...

    // {

    //  }
    //
    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Columns> columns = new ArrayList<>();

    @OneToMany(mappedBy = "board")
    private List<UserBoard> invitedUsers = new ArrayList<>();

    public void setUser(User user) {
        this.user = user;
    }

    @Builder
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

    public void updateColor(BoardBackgroundColorModifyDto boardBackgroundColorModifyDto) {
        this.backgroundColor = boardBackgroundColorModifyDto.getBackgroundColor();
    }
}
