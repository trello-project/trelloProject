package com.example.trelloproject.card.service;

import com.example.trelloproject.card.dto.CardDto;
import com.example.trelloproject.card.entity.Card;
import com.example.trelloproject.card.repository.CardRepository;
import com.example.trelloproject.column.entity.Column;
import com.example.trelloproject.column.repository.ColumnRepository;
import com.example.trelloproject.global.exception.*;
import com.example.trelloproject.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CardService{
    /*
        카드 생성 o
            리스트 내부에 카드를 생성할 수 있어야 합니다.
        카드 수정
            카드 이름   : title
            카드 설명   : cotent
            카드 색상   : backgroundColor
            작업자 할당 : addAssignee
            작업자 변경 : modifyAssignee
        카드 삭제 o
        카드 이동
            같은 컬럼 내에서 카드의 위치를 변경할 수 있어야 합니다.
            카드를 다른 리스트로 이동할 수 있어야 합니다.
    */
    private final CardRepository cardRepository;
    private final ColumnRepository columnRepository;

    // 카드 생성
    public Card addCard(CardDto cardDto, Long columnId, User user/*UserDetails userDetails*/){
        Column column = findColumn(columnId);

        Card newCard = Card.builder()
                .title(cardDto.getTitle())
                .content(cardDto.getContent())
                .writer(user.getUsername())
                .build();

        // 리스트 내부에 카드를 생성할 수 있어야 합니다.
        column.addCard(newCard);
        cardRepository.save(newCard);
        return newCard;
    }

    // 카드 조회
    @Transactional(readOnly = true)
    public Card getCard(Long columnId, Long cardsId, User user){
        findColumn(columnId);
        Card card = findCard(cardsId);
        return card;
    }

    // 카드 삭제
    public void deleteCard(Long columnId, Long cardsId, User user) {
        findColumn(columnId);
        findCard(cardsId);
        cardRepository.deleteById(cardsId);
    }

    // 카드 부분 수정
    @Transactional
    public Card modifyCardTitle(Long columnId, Long cardsId, CardDto cardDTO, User loginUser) {
        // 칼럼 찾기
        findColumn(columnId);
        // 카드 찾기
        Card card = findCard(cardsId);
        // 카드 소유자 찾기
        checkCardOwnership(card, loginUser);

        card.modifyCardTitle(cardDTO);
        return card;
    }

    public void addAssignee(Long columnId, Long cardsId, User loginUser){
        // 작업자 할당을 위해서
        findCard(cardsId);

        // 유저를 알아야함
    }

    public void revokeAssignee(Long listsId, Long cardsId, User user) {

    }
    public void modifyCardColor(Long listsId, Long cardsId, CardDto cardDto, User user) {

    }
    // 작성 예정
    public Card changeCardColor() {

        return null;
    }

    private Card findCard(long cardsId){
        return cardRepository.findById(cardsId).orElseThrow(
                ()-> new NotFoundElementException("해당 카드는 존재하지 않습니다.")
        );
    }

    private Column findColumn(Long columnId){
        return columnRepository.findById(columnId).orElseThrow(
                ()-> new NotFoundElementException("해당 컬럼은 존재하지 않습니다.")
        );
    }

    private void checkCardOwnership(Card card, User loginUser) {
        if (!card.getWriter().equals(loginUser.getUsername())) {
            throw new UnauthorizedAccessException("해당 사용자는 카드를 삭제할 권한이 없습니다.");
        }
    }
}
