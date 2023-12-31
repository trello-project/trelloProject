package com.example.trelloproject.card.repository;

import com.example.trelloproject.card.entity.UsersCards;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersCardsRepository extends JpaRepository<UsersCards, Long> {

}
