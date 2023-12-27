package com.example.trelloproject.Card.Repository;

import com.example.trelloproject.Card.Entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, Long>, CardRepsitoryQuery {
}
