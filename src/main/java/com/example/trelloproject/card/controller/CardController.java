package com.example.trelloproject.card.controller;

import com.example.trelloproject.card.dto.CardDto;
import com.example.trelloproject.card.entity.Card;
import com.example.trelloproject.card.service.CardService;
import com.example.trelloproject.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// v1는 무조건 위에
@RequestMapping("/v1/lists")
@RequiredArgsConstructor
@RestController
public class CardController {

    private final CardService cardService;

    // PathVariable은 무조건 아래쪽으로
    // 길더라도 내리겠다.
    @PostMapping("/{listsId}/cards")
    public ResponseEntity<Card> addCard(
            @PathVariable Long listsId,
            @RequestBody CardDto cardDto,
            User user){
        Card newCard = cardService.addCard(cardDto, listsId, user);
        return ResponseEntity.ok().body(newCard);
    }

    @GetMapping("/{listsId}/cards/{cardsId}")
    public ResponseEntity<Card> getCard(
            @PathVariable Long listsId,
            @PathVariable Long cardsId,
            User user) {
        Card card = cardService.getCard(listsId, cardsId, user);
        return ResponseEntity.ok().body(card);
    }

    @DeleteMapping("/{listsId}/cards/{cardsId}")
    public ResponseEntity<Void> deleteCard(
            @PathVariable Long listsId,
            @PathVariable Long cardsId,
            User user){
        cardService.deleteCard(listsId, cardsId, user);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{listsId}/cards/{cardsId}/cardTitle")
    public ResponseEntity<Card> modifyCardTitle(
            @RequestBody CardDto cardDto,
            @PathVariable Long listsId,
            @PathVariable Long cardsId,
            User user){
        Card card = cardService.modifyCardTitle(listsId, cardsId, cardDto, user);
        return ResponseEntity.ok().body(card);
    }

    @PatchMapping("/{listsId}/cards/{cardsId}/cardColor")
    public ResponseEntity<Card> modifyCardColor(
            @RequestBody CardDto cardDto,
            @PathVariable Long listsId,
            @PathVariable Long cardsId,
            User user){
        cardService.modifyCardColor(listsId, cardsId, cardDto, user);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{listsId}/cards/{cardsId}/assignee")
    public ResponseEntity<Card> addAssignee(
            @PathVariable Long listsId,
            @PathVariable Long cardsId,
            User user){
        cardService.addAssignee(listsId, cardsId, user);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{listsId}/cards/{cardsId}/assignee")
    public ResponseEntity<Card> revokeAssignee(
            @PathVariable Long listsId,
            @PathVariable Long cardsId,
            User user){
        cardService.revokeAssignee(listsId, cardsId, user);
        return ResponseEntity.noContent().build();
    }
}
