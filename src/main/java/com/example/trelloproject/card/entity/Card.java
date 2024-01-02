package com.example.trelloproject.card.entity;

import com.example.trelloproject.card.dto.CardBackgroundColorModifyDto;
import com.example.trelloproject.card.dto.CardContentModifyDto;
import com.example.trelloproject.card.dto.CardTitleModifyDto;
import com.example.trelloproject.column.entity.Columns;
import com.example.trelloproject.comment.entity.Comment;
import com.example.trelloproject.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter

// Entity
@Entity
public class Card {

    // field
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    private String writer;

    @Column(name = "order_column")
    private int order;

    @ManyToOne
    @JoinColumn(name = "columns_id")
    private Columns columns;
    // relation
    // 헤당 빽그라운드 컬러에 대해서 조금 생각 해보기
    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private CardBackgroundColor backgroundColor;

    // 연관 관계의 주인 card -> card에서 해당 comment의 정보를 다 알 수 있어야하고
    // comment쪽에서는 몰라도 됨
    @OneToMany(mappedBy = "card", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "card", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserCard> assignees = new ArrayList<>();

    // constructor
    @Builder
    public Card(String title, String content, String writer){
        this.title = title;
        this.content = content;
        this.writer = writer;
    }
    public void addComment(Comment comment){
        comments.add(comment);
    }

    public void modifyCardTitle(CardTitleModifyDto cardTitleModifyDto){
        this.title = cardTitleModifyDto.getTitle();
    }

    public void modifyCardContent(CardContentModifyDto cardContentModifyDto){
        this.content = cardContentModifyDto.getContent();
    }

    public void addAssignees(List<UserCard> newAssignees){
        assignees.addAll(newAssignees);
    }

    public void removeAssignee(User user) {
        assignees.removeIf(usersCards -> usersCards.getUser().equals(user));
    }

    public void changeCardColor(CardBackgroundColorModifyDto requestDto){
        this.backgroundColor = requestDto.getBackgroundColor();
    }

    public void saveOrder(int i) {
        this.order = order+i;
    }
}
