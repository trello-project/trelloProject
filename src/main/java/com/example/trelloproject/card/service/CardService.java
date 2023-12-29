package com.example.trelloproject.card.service;

import com.example.trelloproject.card.dto.CardDto;
import com.example.trelloproject.global.dto.CommonResponseDto;
import com.example.trelloproject.user.entity.User;

public interface CardService {
    CommonResponseDto<?> createCard(CardDto cardDto, User member/*UserDetails userDetails*/);
}
