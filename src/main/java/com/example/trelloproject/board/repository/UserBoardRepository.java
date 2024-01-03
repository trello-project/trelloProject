package com.example.trelloproject.board.repository;

import com.example.trelloproject.board.entity.UserBoard;
import com.example.trelloproject.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserBoardRepository extends JpaRepository<UserBoard,Long> {
    Optional<UserBoard> findByUserId(Long id);

    Optional<List<UserBoard>> findAllByUser(User user);

    Optional<UserBoard> findByUserIdAndBoardId(Long id, Long boardId);
    List<User> findByBoardId(Long boardId);

// UserBoard findByUserId(Long id);

    List<User> findByBoardIdAndIsAccepted(Long boardId, Boolean isAccepted);

}
