package com.example.trelloproject.card.service;

import com.example.trelloproject.board.entity.Board;
import com.example.trelloproject.board.entity.UserBoard;
import com.example.trelloproject.board.repository.BoardRepository;
import com.example.trelloproject.board.repository.UserBoardRepository;
import com.example.trelloproject.board.service.BoardService;
import com.example.trelloproject.card.dto.*;
import com.example.trelloproject.card.entity.Card;
import com.example.trelloproject.card.entity.CardBackgroundColor;
import com.example.trelloproject.card.entity.UserCard;
import com.example.trelloproject.card.repository.CardRepository;
import com.example.trelloproject.card.repository.UserCardRepository;
import com.example.trelloproject.column.entity.Columns;
import com.example.trelloproject.column.repository.ColumnsRepository;
import com.example.trelloproject.global.exception.*;
import com.example.trelloproject.user.dto.UserResponseDto;
import com.example.trelloproject.user.entity.User;
import com.example.trelloproject.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
        List<Card> cards = column.getCards();

        Card newCard = Card.builder()
                .title(cardDto.getTitle())
                .content(cardDto.getContent())
                .writer(loginUser.getUsername())
                .columns(column)
                .order(cards.size()+1)
                .backgroundColor(CardBackgroundColor.WHITE)
                .build();

        // 리스트 내부에 카드를 생성할 수 있어야 합니다.
        column.addCard(newCard);
        columnsRepository.save(column);
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
        cardRepository.save(card);
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
    @Transactional
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

        validBackgroundColor(cardBackgroundColorModifyDto.getBackgroundColor().toString().toUpperCase());

        card.changeCardColor(cardBackgroundColorModifyDto);
        cardRepository.save(card);

        return new CardResponseDto(card);
    }

    // 잘 굴러가는지 확인해보고 싶은데...
    // 카드 협업자 추가
    @Transactional
    public void addAssignee(
            Long columnId, Long cardsId,
            AddAssigneeDto addAssigneeDto,
            User loginUser){
        // 없어도 상관이 없다.
        // 팀 <-> 기, 총, ...
        // 매핑 되는 연관 관계 수정?

        // 검증 로직 (1. 카드 찾기)
        Card card = findCard(cardsId);

        // 2. 로그인 유저의 아이디 탐색 후, 로그인 유저의 아이디와 다른 userBoard 리턴
        List<UserBoard> userBoardList = boardService.findUserBoard(loginUser);
        // boardId는 같고 userId는 다름
        // 자기 자신 또한 협력자의 명단으로 넣을지? 안 넣을지?
        List<String> asssigneeNames = new ArrayList<>();
        for(int i = 0; i<userBoardList.size(); i++){
            if(loginUser.getId() != userBoardList.get(i).getUser().getId()){
                asssigneeNames.add(userBoardList.get(i).getUser().getUsername());
                card.addAssignees(asssigneeNames);
            }
        }
        cardRepository.save(card);
    }

    /*@Transactional
    public void addAssignee(Long columnsId, Long cardsId, List<String> assigneeNames) {
        Card card = findCard(cardsId);

        // 중복된 협력자를 방지하기 위해 현재 할당된 협력자 목록을 가져옴
        List<String> currentAssignees = card.getAssignees();

        // 새로운 협력자 목록에서 중복된 것을 필터링
        List<String> newAssignees = assigneeNames.stream()
                .filter(assignee -> !currentAssignees.contains(assignee))
                .collect(Collectors.toList());

        // 중복을 방지한 협력자들을 추가
        card.addAssignees(newAssignees);

        // 변경된 카드를 저장
        cardRepository.save(card);
    }*/


    // 카드 협업자 추가 취소
    public void revokeAssignee(Long columnId, Long cardsId, User loginUser) {
        Card card = findCard(cardsId);
        if(!isCardInColumn(columnId, cardsId)){
            throw new NotFoundCardException("해당 컬럼에 카드가 존재하지 않습니다.");
        }
        checkCardOwnership(card, loginUser);

        // 협업자 제거
        // List<UserCard> assignees = card.getAssignees();

        // assignees.removeIf(usersCards -> usersCards.getUser().equals(loginUser));
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
    public void validBackgroundColor(String backgroundColor) {
        // 유효성 검사 로직을 여기에 추가
        try {
            CardBackgroundColor.valueOf(backgroundColor);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("유효하지 않은 배경 색상입니다.");
        }
    }

}
