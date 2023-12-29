package com.examp
import com.example.trelloproject.card.entity.Card;
import com.example.trelloproject.user.entity.User;
import jakarta.persistence.*;

@Entity
@Table(name = "Comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;


    @ManyToOne
    @JoinColumn(name = "cards_id")
    private Card card;

    @ManyToOne
    @JoinColumn(name = "users_id")
    private User user;
}
