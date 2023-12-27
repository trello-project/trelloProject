package com.example.trelloproject.Board.Entity;

import com.pjh.trello.Column.Entity.Column;
import com.pjh.trello.Member.Entity.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Columns;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "member_id")
    private Member member;

    //@OneToMany(mappedBy = "board")
    //private Set<Column> columns = new LinkedHashSet<>();
}
