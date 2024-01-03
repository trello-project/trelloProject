package com.example.trelloproject.card.controller;

import com.example.trelloproject.card.dto.*;
import com.example.trelloproject.card.entity.Card;
import com.example.trelloproject.card.service.CardService;
import com.example.trelloproject.global.security.UserDetailsImpl;
import com.example.trelloproject.user.dto.UserResponseDto;
import com.example.trelloproject.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// v1는 무조건 위에
@RequestMapping("/v1/columns")
@RequiredArgsConstructor
@RestController
public class CardController {

    private final CardService cardService;

    @PostMapping("/{columnsId}/cards")
    public ResponseEntity<CardResponseDto> addCard(
                @PathVariable Long columnsId,
                @RequestBody CardRequestDto cardDto,
                @AuthenticationPrincipal UserDetailsImpl userDetails){
            CardResponseDto cardResponseDto = cardService.addCard(cardDto, columnsId, userDetails.getUser());
            return ResponseEntity.ok().body(cardResponseDto);
    }

    @GetMapping("/{columnsId}/cards/{cardsId}")
    public ResponseEntity<CardResponseDto> getCard(
            @PathVariable Long columnsId,
            @PathVariable Long cardsId) {
        CardResponseDto cardResponseDto = cardService.getCard(columnsId, cardsId);
        return ResponseEntity.ok().body(cardResponseDto);
    }

    @DeleteMapping("/{columnsId}/cards/{cardsId}")
    public ResponseEntity removeCard(
            @PathVariable Long columnsId,
            @PathVariable Long cardsId,
            @AuthenticationPrincipal UserDetailsImpl userDetails){
        cardService.removeCard(columnsId, cardsId, userDetails.getUser());
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{columnsId}/cards/{cardsId}/cardTitle")
    public ResponseEntity<CardResponseDto> modifyCardTitle(
            @RequestBody CardTitleModifyDto cardTitleModifyDto,
            @PathVariable Long columnsId,
            @PathVariable Long cardsId,
            @AuthenticationPrincipal UserDetailsImpl userDetails){
        CardResponseDto cardResponseDto = cardService.modifyCardTitle(columnsId, cardsId, cardTitleModifyDto, userDetails.getUser());
        return ResponseEntity.ok().body(cardResponseDto);
    }

    @PatchMapping("/{columnsId}/cards/{cardsId}/cardContent")
    public ResponseEntity<CardResponseDto> modifyCardContent(
            @RequestBody CardContentModifyDto cardContentModifyDto,
            @PathVariable Long columnsId,
            @PathVariable Long cardsId,
            @AuthenticationPrincipal UserDetailsImpl userDetails){
        CardResponseDto cardResponseDto = cardService.modifyCardContent(columnsId, cardsId, cardContentModifyDto, userDetails.getUser());
        return ResponseEntity.ok().body(cardResponseDto);
    }

    @PatchMapping("/{columnsId}/cards/{cardsId}/cardColor")
    public ResponseEntity<CardResponseDto> modifyCardColor(
            @RequestBody CardBackgroundColorModifyDto cardBackgroundColorModifyDto,
            @PathVariable Long columnsId,
            @PathVariable Long cardsId,
            @AuthenticationPrincipal UserDetailsImpl userDetails){
        CardResponseDto cardResponseDto = cardService.modifyCardColor(columnsId, cardsId, cardBackgroundColorModifyDto, userDetails.getUser());
        return ResponseEntity.ok().body(cardResponseDto);
    }

    @PostMapping("/{columnsId}/cards/{cardsId}/assignee")
    public ResponseEntity addAssignee(
            @PathVariable Long columnsId,
            @PathVariable Long cardsId,
            @RequestBody AddAssigneeDto addAssigneeDto,
            User loginUser){
        cardService.addAssignee(columnsId, cardsId, addAssigneeDto, loginUser);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{columnsId}/cards/{cardsId}/assignee")
    public ResponseEntity revokeAssignee(
            @PathVariable Long columnsId,
            @PathVariable Long cardsId,
            @AuthenticationPrincipal UserDetailsImpl userDetails){
        cardService.revokeAssignee(columnsId, cardsId, userDetails.getUser());
        return ResponseEntity.noContent().build();
    }

    // 임시
    @PostMapping(("/{columnsId}/cards/{cardsId}/order/{newOrder}"))
    public ResponseEntity changeCardOrder(
            @PathVariable Long columnsId,
            @PathVariable Long cardsId,
            @PathVariable Integer newOrder,
            @AuthenticationPrincipal UserDetailsImpl userDetails){
        cardService.modifyCardOrder(columnsId, cardsId, newOrder, userDetails.getUser());
        return ResponseEntity.noContent().build();
    }


    // checkList
    // dao(access obj) method명을 jpa -> findBy
    // jdbc.method 정할 수 <=> 구분을 확실히(findBy...)
}
