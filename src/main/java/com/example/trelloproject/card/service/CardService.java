package com.example.trelloproject.card.service;

import com.example.trelloproject.board.repository.UserBoardRepository;
import com.example.trelloproject.card.dto.CardBackgroundColorModifyDto;
import com.example.trelloproject.card.dto.CardContentModifyDto;
import com.example.trelloproject.card.dto.CardRequestDto;
import com.example.trelloproject.card.dto.CardTitleModifyDto;
import com.example.trelloproject.card.entity.Card;
import com.example.trelloproject.card.repository.CardRepository;
import com.example.trelloproject.column.entity.Columns;
import com.example.trelloproject.column.repository.ColumnRepository;
import com.example.trelloproject.global.exception.NotFoundCardException;
import com.example.trelloproject.global.exception.NotFoundColumnException;
import com.example.trelloproject.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class CardService{
    // board에 접근 했으니까 굳이 로그인 사용자 인증을 할 이유가 있어?
    // 해당 보드 접근에만 인증을 하고 해당 카드에 대한 할당자에 대한 메서드를ㄹ... 생각하자

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
    private final UserBoardRepository userBoardRepository;
    // 카드 생성
    public Card addCard(CardRequestDto cardDto, Long columnId, User user/*UserDetails userDetails*/){
        // 컬럼 찾기
        Columns columns = findColumn(columnId);

        Card newCard = Card.builder()
                .title(cardDto.getTitle())
                .content(cardDto.getContent())
                .writer(user.getUsername())
                .build();

        // 리스트 내부에 카드를 생성할 수 있어야 합니다.
        columns.addCard(newCard);
        cardRepository.save(newCard);
        return newCard;
    }

    // 카드 조회
    @Transactional(readOnly = true)
    public Card getCard(Long columnId, Long cardsId){
        Card card = findCard(cardsId);
        return card;
    }

    // 카드 삭제
    public void removeCard(Long columnId, Long cardsId, User loginUser) {
        // 카드 찾기
        Card card = findCard(cardsId);
        // 카드에 대한 소유자 찾기
        checkCardOwnership(card, loginUser);
        cardRepository.deleteById(cardsId);
    }

    // 카드 부분 수정
    @Transactional
    public Card modifyCardTitle(Long columnId, Long cardsId, CardTitleModifyDto cardTitleModifyDto, User loginUser) {
        // 카드 찾기
        Card card = findCard(cardsId);
        // 카드 소유자 찾기
        checkCardOwnership(card, loginUser);

        card.modifyCardTitle(cardTitleModifyDto);
        return card;
    }

    // 카드 내용 수정
    @Transactional
    public Card modifyCardContent(Long columnId, Long cardsId, CardContentModifyDto cardContentModifyDto, User loginUser) {
        // 카드 찾기
        Card card = findCard(cardsId);
        // 카드 소유자 찾기
        checkCardOwnership(card, loginUser);

        card.modifyCardContent(cardContentModifyDto);
        cardRepository.save(card);
        return card;
    }

    // 해당 CardColor
    public void modifyCardColor(
            Long listsId,
            Long cardsId,
            CardBackgroundColorModifyDto cardBackgroundColorModifyDto,
            User loginUser) {
        Card card = findCard(cardsId);
        checkCardOwnership(card, loginUser);

        card.changeCardColor(cardBackgroundColorModifyDto);
        cardRepository.save(card);
    }

    // 카드 협업자 추가
    @Transactional
    public void addAssignee(Long columnId, Long cardsId, User loginUser){
        // Q1 : 해당 addAssignee을 할 때, 협력자를 알기 위해 보드까지 올라가야하는지?
        // 이거 때문에 userCard 사용해야겠는걸?
        // 내가 추가할 멤버도 알아야겠는데?, 즉 보드에 초대되어진 멤버의 목록을 알아야함
        // 카드라는 Entity는 해당 작성자의 정보만 가지고 있기에
        // board까지 올라가야돼...?
        // 너무 과한데? 일단 구현 후 여쭤보기
        // userBoard의 인물들을 모두 뽑아와서 골라서 return하기

        // 작업자 할당을 위해서
        Card card = findCard(cardsId);
        // 해당 사용자가 카드의 소유자인지
        checkCardOwnership(card, loginUser);

    }

    // 카드 협업자 추가 취소
    public void revokeAssignee(Long listsId, Long cardsId, User user) {

    }

    private Columns findColumn(Long columnId){
        return columnRepository.findById(columnId).orElseThrow(
                ()-> new NotFoundColumnException("해당 컬럼은 존재하지 않습니다.")
        );
    }
    private Card findCard(long cardsId){
        return cardRepository.findById(cardsId).orElseThrow(
                ()-> new NotFoundCardException("해당 카드는 존재하지 않습니다.")
        );
    }

    private void checkCardOwnership(Card card, User loginUser) {
        if (!card.getWriter().equals(loginUser.getUsername())) {
            throw new com.example.trelloproject.global.exception.UnauthorizedAccessException("해당 사용자는 카드를 삭제할 권한이 없습니다.");
        }
    }
}
