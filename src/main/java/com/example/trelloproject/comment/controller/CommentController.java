package com.example.trelloproject.comment.controller;

import com.example.trelloproject.comment.dto.CommentRequestDto;
import com.example.trelloproject.comment.dto.CommentResponseDto;
import com.example.trelloproject.comment.entity.Comment;
import com.example.trelloproject.comment.service.CommentService;
import com.example.trelloproject.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

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

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .pathSegment("{id}")
                .buildAndExpand(newComment.getId())
                .toUri();

        return ResponseEntity.created(location).body(newComment);
        // 변경해야될듯?
    }

    @PutMapping("/{cardsId}/comment/{commentId}")
    public ResponseEntity<CommentResponseDto> modifyComment(
            @RequestBody CommentRequestDto commentRequestDto,
            @PathVariable Long cardsId,
            @PathVariable Long commentId,
            @AuthenticationPrincipal UserDetailsImpl userDetails){
        CommentResponseDto modifyCommentDto = commentService.modifyComment(commentRequestDto, cardsId, commentId, userDetails.getUser());
        return ResponseEntity.ok().body(modifyCommentDto);
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
