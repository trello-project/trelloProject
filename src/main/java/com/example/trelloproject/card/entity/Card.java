package com.example.trelloproject.card.entity;

import com.example.trelloproject.card.dto.CardDto;
import com.example.trelloproject.comment.entity.Comment;
import jakarta.persistence.*;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.LinkedHashSet;
import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter

// Entity
@Entity
public class Card{

    // field
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String content;
    private String writer;

    // relation
    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private CardBackgroundColor backgroundColor;

    // 연관 관계의 주인 card -> card에서 해당 comment의 정보를 다 알 수 있어야하고
    // comment쪽에서는 몰라도 됨
    @OneToMany(mappedBy = "card", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Comment> comments = new LinkedHashSet<>();

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

    public void modifyCardTitle(CardDto cardDto){
        this.title = cardDto.getTitle();
    }

    public void changeCardColor(){

    }

    public void addAssignee(){

    }
}
