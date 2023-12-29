package com.example.trelloproject.board.repository;

import com.example.trelloproject.board.entity.UserBoard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserBoardRepository extends JpaRepository<UserBoard,Long> {
}
