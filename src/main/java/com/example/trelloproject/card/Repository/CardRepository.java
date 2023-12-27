package com.example.trelloproject.card.Repository;

import com.example.trelloproject.card.Entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, Long>, CardRepsitoryQuery {
}
