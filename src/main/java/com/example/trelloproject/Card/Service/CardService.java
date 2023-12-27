package com.example.trelloproject.Card.Service;

import com.example.trelloproject.Card.DTO.CardDTO;
import com.example.trelloproject.Global.DTO.CommonResponseDTO;
import com.example.trelloproject.User.Entity.User;

public interface CardService {
    CommonResponseDTO<?> createCard(CardDTO cardDTO, User member/*UserDetails userDetails*/);
}
