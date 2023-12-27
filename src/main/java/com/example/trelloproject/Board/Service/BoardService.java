package com.example.trelloproject.board.Service;

import com.example.trelloproject.board.Repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BoardService {
    private final BoardRepository boardRepository;
}
