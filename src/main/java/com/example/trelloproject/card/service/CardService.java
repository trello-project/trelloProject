package com.example.trelloproject.card.service;

import com.example.trelloproject.card.dto.CardDTO;
import com.example.trelloproject.global.dto.CommonResponseDTO;
import com.example.trelloproject.user.entity.User;

public interface CardService {
    CommonResponseDTO<?> createCard(CardDTO cardDTO, User member/*UserDetails userDetails*/);
}
