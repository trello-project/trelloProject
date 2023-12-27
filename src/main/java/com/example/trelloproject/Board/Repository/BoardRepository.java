package com.example.trelloproject.board.Repository;

import com.example.trelloproject.board.Entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {

    Optional<Board> findByUserId(Long userId);
}
