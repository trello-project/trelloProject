package com.example.trelloproject.card.repository;

import com.example.trelloproject.card.entity.UserCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersCardsRepository extends JpaRepository<UserCard, Long> {

}
