package com.example.trelloproject.card.Service;

import com.example.trelloproject.card.DTO.CardDTO;
import com.example.trelloproject.global.DTO.CommonResponseDTO;
import com.example.trelloproject.user.Entity.User;

public interface CardService {
    CommonResponseDTO<?> createCard(CardDTO cardDTO, User member/*UserDetails userDetails*/);
}
