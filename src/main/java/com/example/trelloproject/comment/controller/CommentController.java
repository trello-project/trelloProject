package com.example.trelloproject.comment.controller;

import com.example.trelloproject.comment.dto.CommentRequestDto;
import com.example.trelloproject.comment.entity.Comment;
import com.example.trelloproject.comment.service.CommentService;
import com.example.trelloproject.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/v1/cards")
@RequiredArgsConstructor
@RestController
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{cardsId}/comment")
    public ResponseEntity<Comment> addComment(
            @PathVariable Long cardsId,
            CommentRequestDto commentRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails){
        Comment newComment = commentService.addComment(commentRequestDto, cardsId, userDetails.getUser());
        return ResponseEntity.ok().body(newComment);
    }

    @PutMapping("/{cardsId}/comment/{commentId}")
    public ResponseEntity<Comment> modifyComment(
            @RequestBody CommentRequestDto commentRequestDto,
            @PathVariable Long cardsId,
            @PathVariable Long commentId,
            @AuthenticationPrincipal UserDetailsImpl userDetails){
        Comment modifyComment = commentService.modifyComment(commentRequestDto, cardsId, commentId, userDetails.getUser());
        return ResponseEntity.ok().body(modifyComment);
    }

    @DeleteMapping("/cards/{cardsId}/comment/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable Long cardsId,
            @PathVariable Long commentId,
            @AuthenticationPrincipal UserDetailsImpl userDetails){
        commentService.removeComment(cardsId, commentId, userDetails.getUser());
        return ResponseEntity.noContent().build();
    }
}
