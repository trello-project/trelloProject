package com.example.trelloproject.board.repository;

import com.example.trelloproject.board.entity.Board;
import com.example.trelloproject.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {
   //List<Board> findByUser(User user);

    // test
    Optional<Board> findByUserId(Long userId);

    List<Board> findAllByUser(User user);
}
