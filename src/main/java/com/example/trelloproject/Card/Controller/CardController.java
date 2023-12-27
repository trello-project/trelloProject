package com.example.trelloproject.Card.Controller;

import com.example.trelloproject.Card.DTO.CardDTO;
import com.example.trelloproject.Card.Service.CardServiceImpl;
import com.example.trelloproject.Global.DTO.CommonResponseDTO;
import com.example.trelloproject.User.Entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class CardController {

    private final CardServiceImpl cardService;

    @GetMapping("/v1/card")
    public CommonResponseDTO<?> showCard(
            CardDTO cardDTO,
            /*@AuthenticationPrincipal UserDetails userDetails*/
            User member){
        cardService.createCard(cardDTO, member);
        return new CommonResponseDTO<>();
    }
}
