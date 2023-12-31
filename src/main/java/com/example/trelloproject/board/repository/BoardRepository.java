package com.example.trelloproject.board.repository;

import com.example.trelloproject.board.entity.Board;
import com.example.trelloproject.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.Set;

public interface BoardRepository extends JpaRepository<Board, Long> {
    Optional<Board> findByUser(User user);

    @Query("SELECT ub.user FROM UserBoard ub WHERE ub.board.id = :boardId")
    Set<User> findInvitedUsersByBoardId(@Param("boardId") Long boardId);

    Optional<Board> findByColumnsId(Long columnsId);
}
