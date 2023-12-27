package com.example.trelloproject.Card.Service;

import com.example.trelloproject.Board.Repository.BoardRepository;
import com.example.trelloproject.Card.DTO.CardDTO;
import com.example.trelloproject.Card.Entity.Card;
import com.example.trelloproject.Card.Repository.CardRepository;
import com.example.trelloproject.Global.DTO.CommonResponseDTO;
import com.example.trelloproject.Global.Exception.NotFoundElementException;
import com.example.trelloproject.User.Entity.User;
import com.example.trelloproject.User.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService{

    private final CardRepository cardRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    @Override
    public CommonResponseDTO<?> createCard(CardDTO cardDTO, User member/*UserDetails userDetails*/){
        // 해당 멤버가 board에 초대된 멤버인지?
        // 멤버에게 권한을 있어서 createCard는 관리자에게?만 권한을 부여할지?
        boardRepository.findByMemberName(/*userDetails.getMember().getUsername()*/member.getUsername()).orElseThrow(
                ()-> new NotFoundElementException("해당 멤버는 초대 받지 못한 손님입니다.")
        );

        Card card = Card.builder()
                .title(cardDTO.getTitle())
                .content(cardDTO.getContent())
                .writer(member.getUsername())
                .build();

        cardRepository.save(card);
        return new CommonResponseDTO<>("카드를 완성하셨습니다.", 200);
    }
}
