package com.example.trelloproject.comment.dto;

import com.example.trelloproject.comment.entity.Comment;
import lombok.Getter;

@Getter
public class CommentResponseDto {
    private String content;
    private String writer;

    public CommentResponseDto(Comment comment){
        this.content = comment.getContent();
        this.writer = comment.getWriter();
    }
}
