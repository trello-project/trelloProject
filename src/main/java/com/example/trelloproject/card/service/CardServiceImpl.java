package com.example.trelloproject.card.service;

import com.example.trelloproject.board.repository.BoardRepository;
import com.example.trelloproject.card.dto.CardDTO;
import com.example.trelloproject.card.entity.Card;
import com.example.trelloproject.card.repository.CardRepository;
import com.example.trelloproject.global.dto.CommonResponseDTO;
import com.example.trelloproject.global.exception.NotFoundElementException;
import com.example.trelloproject.user.entity.User;
import com.example.trelloproject.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;

    private final BoardRepository boardRepository;

    private final UserRepository userRepository;

    @Override
    public CommonResponseDTO<?> createCard(CardDTO cardDTO, User user/*UserDetails userDetails*/){
        // 해당 멤버가 board에 초대된 멤버인지?
        // 멤버에게 권한을 있어서 createCard는 관리자에게?만 권한을 부여할지?
        boardRepository.findByUser(/*userDetails.getMember().getUsername()*/user).orElseThrow(
                ()-> new NotFoundElementException("해당 멤버는 초대 받지 못한 손님입니다.")
        );

        Card card = Card.builder()
                .title(cardDTO.getTitle())
                .content(cardDTO.getContent())
                .writer(user.getUsername())
                .build();

        cardRepository.save(card);
        return new CommonResponseDTO<>("카드를 완성하셨습니다.", 200);
    }
}
