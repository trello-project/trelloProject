package com.example.trelloproject.card.service;

import com.example.trelloproject.board.service.BoardService;
import com.example.trelloproject.card.dto.*;
import com.example.trelloproject.card.entity.Card;
import com.example.trelloproject.card.entity.UserCard;
import com.example.trelloproject.card.repository.CardRepository;
import com.example.trelloproject.column.entity.Columns;
import com.example.trelloproject.column.repository.ColumnsRepository;
import com.example.trelloproject.global.exception.AssigneeAlreadyExistsException;
import com.example.trelloproject.global.exception.NotFoundCardException;
import com.example.trelloproject.global.exception.NotFoundColumnsException;
import com.example.trelloproject.global.exception.UnauthorizedAccessException;
import com.example.trelloproject.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class CardService{
    // board에 접근 했으니까 굳이 로그인 사용자 인증을 할 이유가 있어?
    // 해당 보드 접근에만 인증을 하고 해당 카드에 대한 할당자에 대한 메서드를ㄹ... 생각하자

    /*
        카드 생성 o
            리스트 내부에 카드를 생성할 수 있어야 합니다.
        카드 수정
            카드 이름       : title                 o
            카드 설명       : cotent                o
            카드 색상       : backgroundColor       o
            작업자 할당     : addAssignee           o
            작업자 할당 취소 : revokeAssignee        o
        카드 삭제 o
        카드 이동
            같은 컬럼 내에서 카드의 위치를 변경할 수 있어야 합니다.
            카드를 다른 리스트로 이동할 수 있어야 합니다.
            // board와 상의 후, 만들 예정           x
            // 그냥 내가 할께...
    */
    private final CardRepository cardRepository;
    private final ColumnsRepository columnsRepository;
    private final BoardService boardService;
    // 카드 생성
    public CardResponseDto addCard(CardRequestDto cardDto, Long columnId, User loginUser){
        // 컬럼 찾기
        Columns column = findColumn(columnId);

        // card 만들어줄게
        // 비지니스 로직 => 데이터 가공 => 데이터 렌더링..
        Card newCard = Card.builder()
                .title(cardDto.getTitle())
                .content(cardDto.getContent())
                .writer(loginUser.getUsername())
                .build();

        // 리스트 내부에 카드를 생성할 수 있어야 합니다.
        column.addCard(newCard);
        cardRepository.save(newCard);

        return new CardResponseDto(newCard);
    }

    // 카드 조회
    @Transactional(readOnly = true)
    public CardResponseDto getCard(Long columnId, Long cardsId){
        Card card = findCard(cardsId);
        if(!isCardInColumn(columnId, cardsId)){
            throw new NotFoundCardException("해당 카드는 컬럼 안에 존재하지 않습니다.");
        }
        return new CardResponseDto(card);
    }

    // 카드 삭제
    public void removeCard(Long columnId, Long cardsId, User loginUser) {
        // 카드 찾기
        Card card = findCard(cardsId);
        if(!isCardInColumn(columnId, cardsId)){
            throw new NotFoundCardException("해당 카드는 컬럼 안에 존재하지 않습니다.");
        }
        // 카드에 대한 소유자 찾기
        checkCardOwnership(card, loginUser);
        cardRepository.deleteById(cardsId);
    }

    // 카드 부분 수정
    @Transactional
    public CardResponseDto modifyCardTitle(Long columnId, Long cardsId, CardTitleModifyDto cardTitleModifyDto, User loginUser) {
        // 카드 찾기
        Card card = findCard(cardsId);

        // 해당 컬럼 안에 Card 찾기
        if(!isCardInColumn(columnId, cardsId)){
            throw new NotFoundCardException("해당 카드는 컬럼 안에 존재하지 않습니다.");
        }
        // 카드 소유자 찾기
        checkCardOwnership(card, loginUser);

        card.modifyCardTitle(cardTitleModifyDto);

        return new CardResponseDto(card);
    }

    // 카드 내용 수정
    @Transactional
    public CardResponseDto modifyCardContent(Long columnId, Long cardsId, CardContentModifyDto cardContentModifyDto, User loginUser) {
        // 카드 찾기
        Card card = findCard(cardsId);

        if(!isCardInColumn(columnId, cardsId)){
            throw new NotFoundCardException("해당 컬럼에 카드가 존재하지 않습니다.");
        }
        checkCardOwnership(card, loginUser);

        card.modifyCardContent(cardContentModifyDto);
        cardRepository.save(card);

        return new CardResponseDto(card);
    }

    // 해당 CardColor
    public CardResponseDto modifyCardColor(
            Long columnId,
            Long cardsId,
            CardBackgroundColorModifyDto cardBackgroundColorModifyDto,
            User loginUser) {
        Card card = findCard(cardsId);
        if(!isCardInColumn(columnId, cardsId)){
            throw new NotFoundCardException("해당 컬럼에 카드가 존재하지 않습니다.");
        }
        checkCardOwnership(card, loginUser);

        card.changeCardColor(cardBackgroundColorModifyDto);
        cardRepository.save(card);

        return new CardResponseDto(card);
    }

    // 잘 굴러가는지 확인해보고 싶은데...
    // 카드 협업자 추가
    @Transactional
    public void addAssignee(
            Long columnId, Long cardsId,
            CardAssigneeListDto assigneeListDto, User loginUser){
        // 검증 로직 (1. 카드 찾기, 2. 컬럼 안에 카드가 존재하는지, 3. 카드의 주인이 LoginUser인지)
        Card card = findCard(cardsId);
        if(!isCardInColumn(columnId, cardsId)){
            throw new NotFoundCardException("해당 컬럼에 카드가 존재하지 않습니다.");
        }
        checkCardOwnership(card, loginUser);

        // 이미 협업자로 등록되어 있는지 확인
        // 초기엔 당연히 없겠지?
        List<UserCard> existingAssignees = card.getAssignees();
        for (UserCard userCard : existingAssignees) {
            if (userCard.getUser().equals(loginUser)) {
                throw new AssigneeAlreadyExistsException("이미 협업자로 등록되어 있습니다.");
            }
        }

        List<UserCard> newAssignees = assigneeListDto.getAssignee().stream()
                .map(user -> UserCard.builder().user(user).card(card).build())
                .collect(Collectors.toList());

        card.addAssignees(newAssignees);
        cardRepository.save(card);

    }

    // 카드 협업자 추가 취소
    public void revokeAssignee(Long columnId, Long cardsId, User loginUser) {
        Card card = findCard(cardsId);
        if(!isCardInColumn(columnId, cardsId)){
            throw new NotFoundCardException("해당 컬럼에 카드가 존재하지 않습니다.");
        }
        checkCardOwnership(card, loginUser);

        // 협업자 제거
        List<UserCard> assignees = card.getAssignees();

        assignees.removeIf(usersCards -> usersCards.getUser().equals(loginUser));
        // 멤버 리스트를 지워야하니까 잘 못된듯?
        card.removeAssignee(loginUser);

        cardRepository.save(card);
    }

    // 칼럼에서 카드 이동
    public void modifyCardOrder(Long cardsId, Long columnId, int newOrder ,User loginUser){
        Columns column = findColumn(columnId);
        Card card = findCard(cardsId);

        if(!isCardInColumn(columnId, cardsId)){
            throw new NotFoundCardException("해당 컬럼에 카드가 존재하지 않습니다.");
        }
        checkCardOwnership(card, loginUser);

        // 현재 순서를 알아야함
        int currentOrder = getCardOrder(column, card);
        if(currentOrder != newOrder){
            modifyCardOrder(column, card, newOrder);
        }
    }

    private Columns findColumn(Long columnId){
        return columnsRepository.findById(columnId).orElseThrow(
                ()-> new NotFoundColumnsException("해당 컬럼은 존재하지 않습니다.")
        );
    }
    private Card findCard(long cardsId){
        return cardRepository.findById(cardsId).orElseThrow(
                ()-> new NotFoundCardException("해당 카드는 존재하지 않습니다.")
        );
    }

    private void checkCardOwnership(Card card, User loginUser) {
        if (!card.getWriter().equals(loginUser.getUsername())) {
            throw new UnauthorizedAccessException("해당 사용자는 카드를 사용할 권한이 없습니다.");
        }
    }
    private boolean isCardInColumn(Long columnId, Long cardsId){
        Columns column = findColumn(columnId);
        return column.getCards().stream().anyMatch(card -> card.getId().equals(cardsId));
    }

    private int getCardOrder(Columns column, Card card){
        List<Card> cardsInColumn = column.getCards();
        return cardsInColumn.indexOf(card);
    }

    @Transactional
    public void modifyCardOrder(Columns column, Card card, int newOrder) {
        List<Card> cardInColumn = column.getCards();
        // 해당 순서를 지우기 위해 card의 정보를 지움
        cardInColumn.remove(card);

        // card를 newOrder로 밀어내고 그 자리에 추가
        cardInColumn.add(newOrder, card);

        // 순서 업데이트
        for (int order = 0; order < cardInColumn.size(); order++) {
            Card tmpCard = cardInColumn.get(order);
            tmpCard.saveOrder(order + 1);
            cardRepository.save(tmpCard);
        }
    }
}
