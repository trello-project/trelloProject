package com.example.trelloproject.card.controller;

import com.example.trelloproject.card.dto.CardRequestDto;
import com.example.trelloproject.card.entity.Card;
import com.example.trelloproject.card.service.CardService;
import com.example.trelloproject.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
            @RequestBody CardRequestDto cardDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails){
        Card newCard = cardService.addCard(cardDto, listsId, userDetails.getUser());
        return ResponseEntity.ok().body(newCard);
    }

    @GetMapping("/{listsId}/cards/{cardsId}")
    public ResponseEntity<Card> getCard(
            @PathVariable Long listsId,
            @PathVariable Long cardsId) {
        Card card = cardService.getCard(listsId, cardsId);
        return ResponseEntity.ok().body(card);
    }

    @DeleteMapping("/{listsId}/cards/{cardsId}")
    public ResponseEntity<Void> deleteCard(
            @PathVariable Long listsId,
            @PathVariable Long cardsId,
            @AuthenticationPrincipal UserDetailsImpl userDetails){
        cardService.removeCard(listsId, cardsId, userDetails.getUser());
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{listsId}/cards/{cardsId}/cardTitle")
    public ResponseEntity<Card> modifyCardTitle(
            @RequestBody CardRequestDto cardDto,
            @PathVariable Long listsId,
            @PathVariable Long cardsId,
            @AuthenticationPrincipal UserDetailsImpl userDetails){
        Card card = cardService.modifyCardTitle(listsId, cardsId, cardDto, userDetails.getUser());
        return ResponseEntity.ok().body(card);
    }

    @PatchMapping("/{listsId}/cards/{cardsId}/cardContent")
    public ResponseEntity<Card> modifyCardContent(
            @RequestBody CardRequestDto cardDto,
            @PathVariable Long listsId,
            @PathVariable Long cardsId,
            @AuthenticationPrincipal UserDetailsImpl userDetails){
        Card card = cardService.modifyCardContent(listsId, cardsId, cardDto, userDetails.getUser());
        return ResponseEntity.ok().body(card);
    }

    @PatchMapping("/{listsId}/cards/{cardsId}/cardColor")
    public ResponseEntity<Card> modifyCardColor(
            @RequestBody CardRequestDto cardDto,
            @PathVariable Long listsId,
            @PathVariable Long cardsId,
            @AuthenticationPrincipal UserDetailsImpl userDetails){
        cardService.modifyCardColor(listsId, cardsId, cardDto, userDetails.getUser());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{listsId}/cards/{cardsId}/assignee")
    public ResponseEntity<Card> addAssignee(
            @PathVariable Long listsId,
            @PathVariable Long cardsId,
            @AuthenticationPrincipal UserDetailsImpl userDetails){
        cardService.addAssignee(listsId, cardsId, userDetails.getUser());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{listsId}/cards/{cardsId}/assignee")
    public ResponseEntity<Card> revokeAssignee(
            @PathVariable Long listsId,
            @PathVariable Long cardsId,
            @AuthenticationPrincipal UserDetailsImpl userDetails){
        cardService.revokeAssignee(listsId, cardsId, userDetails.getUser());
        return ResponseEntity.noContent().build();
    }
}
