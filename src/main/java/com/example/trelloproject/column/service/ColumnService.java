package com.example.trelloproject.column.service;

import com.example.trelloproject.board.entity.Board;
import com.example.trelloproject.board.repository.BoardRepository;
import com.example.trelloproject.column.dto.ColumnsListResponseDto;
import com.example.trelloproject.column.dto.ColumnsRequestDto;
import com.example.trelloproject.column.dto.ColumnsResponseDto;
import com.example.trelloproject.column.entity.Columns;
import com.example.trelloproject.column.repository.ColumnsRepository;
import com.example.trelloproject.global.exception.NotFoundBoardException;
import com.example.trelloproject.global.exception.NotFoundColumnsException;
import com.example.trelloproject.global.exception.NotFoundElementException;
import com.example.trelloproject.global.exception.UnauthorizedAccessException;
import com.example.trelloproject.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ColumnService {

    private final ColumnsRepository columnRepository;
    private final BoardRepository boardRepository;
    // private final ColumnRepositoryQuery columnRepositoryQuery;

    // 컬럼 추가
    public ColumnsResponseDto addColumn(Long boardsId, ColumnsRequestDto columnDTO) {
        // 해당 리스트를 탐색을 할지? 게시판을 탐색을 할지?
        // 내 생각에는 리스트를 탐색을 하면 바로 boardRepository를 탐색할 이유가 없는데?
        Board board = boardRepository.findById(boardsId).orElseThrow(
                ()-> new NotFoundBoardException("해당 게시판이 존재하지 않습니다.")
        );

        Columns newColumns = Columns.builder()
                .board(board)
                .title(columnDTO.getTitle())
                .build();

        board.addColumn(newColumns);
        boardRepository.save(board);

        return new ColumnsResponseDto(newColumns);
    }

    // #2 feedback
    // @Transacational에 대한 사용법 알아보기
    // 컬럼 삭제
    @Transactional
    public void deleteColumn(Long boardsId, Long columnsId, User user) {
        boardRepository.findByUserId(user.getId()).orElseThrow(
                ()-> new UnauthorizedAccessException("해당 유저는 컬럼 삭제를 할 수 없습니다.")
        );

        columnRepository.deleteById(columnsId);
    }

    // 컬럼 수정
    // modified 변경
    public ColumnsResponseDto modifyColumn(Long boardsId, Long listsId, ColumnsRequestDto columnDto, User user) {
        // 해당 게시글 찾기
        Board board = findBoard(boardsId);
        // 해당 컬럼 찾기
        Columns columns = findColumn(board, listsId);
        columns.updateTitle(columnDto);
        columnRepository.save(columns);
        return new ColumnsResponseDto(columns);
    }

    // 컬럼 순서 변경
    // test
    @Transactional
    public Columns changeColumnOrder(Long boardsId, Long listsId1, Long listsId2, Long commentId, User user) {
        // 게시판 찾기
        Board board = findBoard(boardsId);

        // 칼럼 찾기
        Columns column1 = columnRepository.findById(listsId1).orElseThrow(
                () -> new NotFoundColumnsException("칼럼 "+listsId1+"에 대한 정보를 찾을 수 없습니다.")
        );
        Columns column2 = columnRepository.findById(listsId2).orElseThrow(
                () -> new NotFoundElementException("칼럼 "+listsId2+"에 대한 정보를 찾을 수 없습니다.")
        );
        /*
        // 칼럼 순서 변경
        long firstOrder = column1.getOrder();
        long secondOrder = column2.getOrder();

        // 위치가 다를 때만 변경 작업 수행
        if (firstOrder != secondOrder) {
            // 두 위치 사이의 칼럼들을 가져옴
            List<Columns> columnsBetween = columnRepository
                    .findByBoardAndPositionBetween(board, firstOrder, secondOrder);

            // position1에서 position2까지의 칼럼들을 한 칸씩 뒤로 밀음 또는 앞으로 당김
            columnsBetween.forEach(c -> {
                if (c.getOrder() == firstOrder) {
                    c.saveOrder(secondOrder);
                } else {
                    c.saveOrder(firstOrder < secondOrder ? c.getOrder() - 1 : c.getOrder() + 1);
                }
            });

            // 변경된 칼럼들 저장
            columnRepository.saveAll(columnsBetween);
        }*/
        return null;
    }

    // 모든 컬럼 조회
    @Transactional(readOnly = true)
    public List<ColumnsResponseDto> getColumns(Long boardsId) {
        findBoard(boardsId);
        List<Columns> columns = columnRepository.findAll();

        List<ColumnsResponseDto> responseDtoList = columns.stream()
                .map(ColumnsResponseDto::new)  // 각 컬럼을 DTO로 변환
                .collect(Collectors.toList());

        return responseDtoList;

    }

    // 게시글 찾는 메서드
    private Board findBoard(Long boardsId){
        // 보드 찾기
        Board board = boardRepository.findById(boardsId).orElseThrow(
                () -> new NotFoundBoardException("해당 보드가 존재하지 않습니다.")
        );
        return board;
    }

    // 칼럼 한 개를 찾는 메서드
    private Columns findColumn(Board board, Long listsId){
        Columns columns = board.getColumns()
                .stream()
                .filter(c -> c.getId().equals(listsId))
                .findFirst()
                .orElseThrow(
                        () -> new NotFoundColumnsException("해당 컬럼이 존재하지 않습니다.")
                );
        return columns;
    }
}
