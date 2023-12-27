package com.example.trelloproject.Board.Repository;

import com.example.trelloproject.Board.Entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {

    Optional<Board> findByMemberName(String memberName);
}
