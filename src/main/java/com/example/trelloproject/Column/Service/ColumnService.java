package com.example.trelloproject.column.service;

import com.example.trelloproject.Board.Entity.Board;
import com.example.trelloproject.board.repository.BoardRepository;
import com.example.trelloproject.column.dto.ColumnRequestDto;
import com.example.trelloproject.column.entity.Column;
import com.example.trelloproject.column.repository.ColumnRepository;
import com.example.trelloproject.global.exception.NotFoundBoardException;
import com.example.trelloproject.global.exception.NotFoundColumnException;
import com.example.trelloproject.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ColumnService {

    private final ColumnRepository columnRepository;
    private final BoardRepository boardRepository;
    // private final ColumnRepositoryQuery columnRepositoryQuery;

    // 컬럼 추가
    public Column addColumn(Long boardsId, Long listsId, ColumnRequestDto columnDTO, User user) {
        // 해당 리스트를 탐색을 할지? 게시판을 탐색을 할지?
        // 내 생각에는 리스트를 탐색을 하면 바로 boardRepository를 탐색할 이유가 없는데?
        Board board = boardRepository.findById(boardsId).orElseThrow(
                ()-> new NotFoundBoardException("해당 게시판이 존재하지 않습니다.")
        );

        Column newColumn = Column.builder()
                .title(columnDTO.getTitle())
                .build();

        board.addColumn(newColumn);
        boardRepository.save(board);

        return newColumn;
    }

    // #2 feedback
    // @Transacational에 대한 사용법 알아보기
    // 컬럼 삭제
    @Transactional
    public void deleteColumn(Long boardsId, Long listsId, User user) {
        columnRepository.deleteById(listsId);
    }

    // 컬럼 수정
    // modified 변경
    public Column updateColumn(Long boardsId, Long listsId, ColumnRequestDto columnDto, User user) {
        // 해당 게시글 찾기
        Board board = findBoard(boardsId);
        // 해당 컬럼 찾기
        Column column = findColumn(board, listsId);
        column.updateTitle(columnDto);
        columnRepository.save(column);
        // feedback #3 : statusCode를 어디다 둬야돼?
        return column;
    }

    // 컬럼 순서 변경
    @Transactional
    public Column changeColumnOrder(Long boardsId, Long listsId1, Long listsId2, Long commentId, User user) {
        /*
        // 게시판 찾기
        Board board = findBoard(boardsId);

        // 칼럼 찾기
        Column column1 = columnRepository.findById(listsId1).orElseThrow(
                () -> new NotFoundElementException("칼럼 "+listsId1+"에 대한 정보를 찾을 수 없습니다.")
        );
        Column column2 = columnRepository.findById(listsId2).orElseThrow(
                () -> new NotFoundElementException("칼럼 "+listsId2+"에 대한 정보를 찾을 수 없습니다.")
        );

        // 칼럼 순서 변경
        long position1 = column1.getPosition();
        long position2 = column2.getPosition();

        // 위치가 다를 때만 변경 작업 수행
        if (position1 != position2) {
            // 두 위치 사이의 칼럼들을 가져옴
            List<Column> columnsBetween = columnRepository
                    .findByBoardAndPositionBetween(board, position1, position2);

            // position1에서 position2까지의 칼럼들을 한 칸씩 뒤로 밀음 또는 앞으로 당김
            columnsBetween.forEach(c -> {
                if (c.getPosition() == position1) {
                    c.setPosition(position2);
                } else {
                    c.setPosition(position1 < position2 ? c.getPosition() - 1 : c.getPosition() + 1);
                }
            });

            // 변경된 칼럼들 저장
            columnRepository.saveAll(columnsBetween);
        }
        */
        return null;
    }

    // 모든 컬럼 조회
    public List<Column> getColumns(Long boardsId, Long listsId) {
        findBoard(boardsId);
        List<Column> columns = columnRepository.findAll();
        return columns;
    }

    // 게시글 찾는 메서드
    private com.example.trelloproject.Board.Entity.Board findBoard(Long boardsId){
        // 보드 찾기
        com.example.trelloproject.Board.Entity.Board board = boardRepository.findById(boardsId).orElseThrow(
                () -> new NotFoundBoardException("해당 보드가 존재하지 않습니다.")
        );
        return board;
    }

    // 칼럼 한 개를 찾는 메서드
    private Column findColumn(com.example.trelloproject.Board.Entity.Board board, Long listsId){
        Column column = board.getColumns()
                .stream()
                .filter(c -> c.getId().equals(listsId))
                .findFirst()
                .orElseThrow(
                        () -> new NotFoundColumnException("해당 컬럼이 존재하지 않습니다.")
                );
        return column;
    }
}
