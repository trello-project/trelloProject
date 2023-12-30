package com.example.trelloproject.comment.controller;

import com.example.trelloproject.comment.dto.CommentDto;
import com.example.trelloproject.comment.entity.Comment;
import com.example.trelloproject.comment.service.CommentService;
import com.example.trelloproject.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/v1/cards")
@RequiredArgsConstructor
@RestController
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{cardsId}/comment")
    public ResponseEntity<Comment> addComment(
            @PathVariable Long cardsId,
            CommentDto commentDTO,
            User user){
        Comment newComment = commentService.addComment(commentDTO, cardsId, user);
        return ResponseEntity.ok().body(newComment);
    }

    @PutMapping("/{cardsId}/comment/{commentId}")
    public ResponseEntity<Comment> modifyComment(
            @RequestBody CommentDto commentDto,
            @PathVariable Long cardsId,
            @PathVariable Long commentId,
            User user){
        Comment modifyComment = commentService.modifyComment(commentDto, cardsId, commentId, user);
        return ResponseEntity.ok().body(modifyComment);
    }

    @DeleteMapping("/cards/{cardsId}/comment/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable Long cardsId,
            @PathVariable Long commentId,
            User user){
        commentService.removeComment(cardsId, commentId, user);
        return ResponseEntity.noContent().build();
    }
}
