package com.example.trelloproject.column.controller;

import com.example.trelloproject.column.dto.ColumnRequestDto;
import com.example.trelloproject.column.entity.Columns;
import com.example.trelloproject.column.service.ColumnService;
import com.example.trelloproject.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/boards")
@RequiredArgsConstructor
public class ColumnController {

    private final ColumnService columnService;

    // 해당 User는 @AuthenticationPrincipal UserDetails userDetails가 될 예정
    @PostMapping("{boardsId}/lists/{listsId}/comments")
    public ResponseEntity<Columns> addColumn(
            @PathVariable Long boardsId,
            @PathVariable Long listsId,
            @RequestBody ColumnRequestDto columnDTO,
            User user){
        Columns column = columnService.addColumn(boardsId,listsId,columnDTO,user);
        return new ResponseEntity<>(column, HttpStatus.OK);
    }

    @DeleteMapping("{boardsId}/lists/{listsId}/columns/{commentId}")
    public ResponseEntity<Void> deleteColumn(
            @PathVariable Long boardsId,
            @PathVariable Long listsId,
            User user){
        columnService.deleteColumn(boardsId, listsId, user);
        return null;
    }

    @PutMapping("{boardsId}/lists/{listsId}/columns/{commentId}")
    public ResponseEntity<Columns> updateColumn(
            @PathVariable Long boardsId,
            @PathVariable Long listsId,
            @RequestBody ColumnRequestDto columnDto,
            User user
            /*@AuthenticationPrincipal UserDetails userDetails*/){
        Columns modifyColumn = columnService.updateColumn(boardsId, listsId, columnDto, user);
        return new ResponseEntity<>(modifyColumn, HttpStatus.OK);
    }

    @PutMapping("{boardsId}/lists/{firstListsId}/{secondListsId}/columns/{commentId}")
    public ResponseEntity<Columns> changeColumnOrder(
            @PathVariable Long boardsId,
            @PathVariable Long firstListsId,
            @PathVariable Long secondListsId,
            @PathVariable Long commentId,
            User user){
        Columns column = columnService.changeColumnOrder(boardsId, firstListsId, secondListsId, commentId, user);
        return new ResponseEntity<>(column, HttpStatus.OK);
    }

    @GetMapping("{boardsId}/lists/{listsId}/lists")
    public ResponseEntity<List<Columns>> getColumns(
            @PathVariable Long boardsId,
            @PathVariable Long listsId){
        List<Columns> columns = columnService.getColumns(boardsId, listsId);
        // 숨길 필요가 없다.
        return new ResponseEntity<>(columns, HttpStatus.OK);
    }
}
