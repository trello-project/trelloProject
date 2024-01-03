package com.example.trelloproject.column.service;

import com.example.trelloproject.board.entity.Board;
import com.example.trelloproject.board.repository.BoardRepository;
import com.example.trelloproject.column.dto.ColumnsRequestDto;
import com.example.trelloproject.column.dto.ColumnsResponseDto;
import com.example.trelloproject.column.entity.Columns;
import com.example.trelloproject.column.entity.QColumns;
import com.example.trelloproject.column.repository.ColumnsRepository;
import com.example.trelloproject.global.exception.NotFoundBoardException;
import com.example.trelloproject.global.exception.NotFoundColumnsException;
import com.example.trelloproject.user.entity.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ColumnService {

    private final ColumnsRepository columnRepository;
    private final BoardRepository boardRepository;
    private final JPAQueryFactory queryFactory;

    // 컬럼 추가
    @Transactional
    public ColumnsResponseDto addColumn(Long boardsId, ColumnsRequestDto columnDTO) {
        // 해당 리스트를 탐색을 할지? 게시판을 탐색을 할지?
        // 내 생각에는 리스트를 탐색을 하면 바로 boardRepository를 탐색할 이유가 없는데?
        Board board = boardRepository.findById(boardsId).orElseThrow(
                ()-> new NotFoundBoardException("해당 게시판이 존재하지 않습니다.")
        );

        // 현재 게시판에 존재하는 컬럼의 리스트를 확인
        List<Columns> columnsList = board.getColumns();
        Columns newColumns = Columns.builder()
                .board(board)
                .order(columnsList.size()+1)
                .title(columnDTO.getTitle())
                .build();

        board.addColumn(newColumns);
        boardRepository.save(board);

        return new ColumnsResponseDto(newColumns);
    }

    // 컬럼 삭제
    @Transactional
    public void deleteColumn(Long boardsId, Long columnsId, User user) {

        Board board = findBoard(boardsId);
        Columns deleteColumns = findColumn(board, columnsId);
        // 주인 메서드
        int deletedColumnOrder = deleteColumns.getOrder();

        columnRepository.deleteById(columnsId);

        // 삭제한 칼럼 이후의 모든 칼럼들의 순서를 하나씩 당김
        List<Columns> remainingColumns = columnRepository.findByBoardIdOrderByOrderAsc(board.getId());
        for (Columns column : remainingColumns) {
            if (column.getOrder() > deletedColumnOrder) {
                column.decreaseOrder();
            }
        }
    }

    // 컬럼 수정
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
    public ColumnsResponseDto updateColumnSequence(Long boardId, Long columnId, Integer newSequence) {
        findBoard(boardId);
        Columns columns = columnRepository.findById(columnId)
                .orElseThrow(() -> new RuntimeException("Column not found"));
        columns.saveOrder(newSequence);

        // 다른 컬럼들의 순서 업데이트
        updateOtherColumnsSequence(columns.getBoard().getId(), columnId, newSequence);

        Columns updatedColumn = columnRepository.save(columns);
        return new ColumnsResponseDto(updatedColumn);
    }

    // 컬럼 순서 변경
    private void updateOtherColumnsSequence(Long boardId, Long columnId, Integer newSequence) {
        QColumns qColumn = QColumns.columns;
        List<Columns> columns = queryFactory
                .selectFrom(qColumn)
                .where(qColumn.board.id.eq(boardId)
                        .and(qColumn.id.ne(columnId))
                        .and(qColumn.order.goe(newSequence)))
                // test
                .fetch();

        for (Columns otherColumn : columns) {
            otherColumn.saveOrder(otherColumn.getOrder() + 1);
            columnRepository.save(otherColumn);
        }
    }

    // 모든 컬럼 조회
    public List<ColumnsResponseDto> getColumns(Long boardsId) {
        findBoard(boardsId);

        List<Columns> columns = columnRepository.findAll();

        return columns.stream()
                .map(ColumnsResponseDto::new)
                .collect(Collectors.toList());
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
    private Columns findColumn(Board board, Long columnId){
        Columns columns = board.getColumns()
                .stream()
                .filter(c -> c.getId().equals(columnId))
                .findFirst()
                .orElseThrow(
                        () -> new NotFoundColumnsException("해당 컬럼이 존재하지 않습니다.")
                );
        return columns;
    }
}
