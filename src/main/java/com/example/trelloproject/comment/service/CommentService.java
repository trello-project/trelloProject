package com.example.trelloproject.comment.service;

import com.example.trelloproject.card.entity.Card;
import com.example.trelloproject.card.repository.CardRepository;
import com.example.trelloproject.comment.dto.CommentDto;
import com.example.trelloproject.comment.entity.Comment;
import com.example.trelloproject.comment.repository.CommentRepository;
import com.example.trelloproject.global.exception.NotFoundCardException;
import com.example.trelloproject.global.exception.NotFoundCommentException;
import com.example.trelloproject.global.exception.UnauthorizedAccessException;
import com.example.trelloproject.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CardRepository cardRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public Comment addComment(CommentDto commentDTO, Long cardsId, User loginUser) {
        Card card = findCard(cardsId);

        Comment comment = Comment.builder()
                .content(commentDTO.getContent())
                .writer(loginUser.getUsername())
                .build();

        card.addComment(comment);
        commentRepository.save(comment);
        return comment;
    }

    @Transactional
    public void removeComment(Long cardsId, Long commentId, User loginUser) {
        // 카드 찾기
        findCard(cardsId);
        // 댓글 찾기
        Comment comment = findComment(commentId);
        // 댓글의 작성자인지
        checkCommentOwnership(comment, loginUser);

        commentRepository.deleteById(commentId);
    }

    @Transactional
    public Comment modifyComment(CommentDto commentDto, Long cardsId, Long commentId, User loginUser) {
        // 카드를 찾으면 댓글의 정보를 알 수 있음
        Card card = findCard(cardsId);
        // 댓글을 찾으면 카드의 정보를 알 수 없음
        Comment comment = findComment(commentId);
        checkCommentOwnership(comment, loginUser);

        comment.modify(commentDto.getContent());
        commentRepository.save(comment);
        return comment;
    }

    private Card findCard(Long cardsId) {
        return cardRepository.findById(cardsId).orElseThrow(
                () -> new NotFoundCardException("해당 카드를 찾을 수 없습니다.")
        );
    }

    private Comment findComment(Long commentId){
        return commentRepository.findById(commentId).orElseThrow(
                () -> new NotFoundCommentException("해당 댓글은 존재하지 않습니다.")
        );
    }

    private void checkCommentOwnership(Comment comment, User loginUser) {
        if (!comment.getWriter().equals(loginUser.getUsername())) {
            throw new UnauthorizedAccessException("해당 사용자는 댓글을 삭제할 권한이 없습니다.");
        }
    }
}
