package com.example.trelloproject.comment.repository;

import com.example.trelloproject.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
