package com.example.trelloproject.card.controller;

import com.example.trelloproject.card.dto.CardDto;
import com.example.trelloproject.card.service.CardServiceImpl;
import com.example.trelloproject.global.dto.CommonResponseDto;
import com.example.trelloproject.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class CardController {

    private final CardServiceImpl cardService;

    @GetMapping("/v1/card")
    public CommonResponseDto<?> showCard(
            CardDto cardDto,
            /*@AuthenticationPrincipal UserDetails userDetails*/
            User member){
        cardService.createCard(cardDto, member);
        return new CommonResponseDto<>();
    }
}
