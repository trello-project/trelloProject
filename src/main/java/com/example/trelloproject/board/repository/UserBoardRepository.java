package com.example.trelloproject.board.repository;

import com.example.trelloproject.board.entity.Board;
import com.example.trelloproject.board.entity.UserBoard;
import com.example.trelloproject.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserBoardRepository extends JpaRepository<UserBoard,Long> {
    UserBoard findByUserAndBoardAndIsAccepted(User user, Board board, Boolean isAccepted);
//    void findByEmail
}
