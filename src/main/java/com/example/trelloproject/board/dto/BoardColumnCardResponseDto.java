package com.example.trelloproject.board.dto;

import com.example.trelloproject.board.entity.Board;
import com.example.trelloproject.column.dto.ColumnRelatedCardResponseDto;
import com.example.trelloproject.column.entity.Columns;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class BoardColumnCardResponseDto {

    private String title;
    private String content;
    private String userName;
    private List<ColumnRelatedCardResponseDto> columnCardResponseDto;

    public BoardColumnCardResponseDto(Board board) {
        this.title = board.getTitle();
        this.content = board.getContent();
        this.userName = board.getUser().getUsername();
        this.columnCardResponseDto = columnsToColumnCardList(board);
    }


    public List<ColumnRelatedCardResponseDto> columnsToColumnCardList(Board board) {
        List<ColumnRelatedCardResponseDto> columnCardDtoList = new ArrayList<>();

        List<Columns> columnsList = board.getColumns();
        columnsList.forEach(columns -> {
            var columnsResponseDto = new ColumnRelatedCardResponseDto(columns);
            columnCardDtoList.add(columnsResponseDto);
        });
        return columnCardDtoList;
    }


}
