package com.example.trelloproject.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import com.example.trelloproject.board.entity.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {

    Optional<Board> findByMemberName(String memberName);
}
