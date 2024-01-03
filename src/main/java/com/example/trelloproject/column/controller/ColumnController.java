package com.example.trelloproject.column.controller;

import com.example.trelloproject.column.dto.ColumnsRequestDto;
import com.example.trelloproject.column.dto.ColumnsResponseDto;
import com.example.trelloproject.column.entity.Columns;
import com.example.trelloproject.column.service.ColumnService;
import com.example.trelloproject.global.security.UserDetailsImpl;
import com.example.trelloproject.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/boards")
public class ColumnController {

    private final ColumnService columnService;

    // 해당 User는 @AuthenticationPrincipal UserDetails userDetails가 될 예정
    @PostMapping("{boardsId}/columns")
    public ResponseEntity<ColumnsResponseDto> addColumn(
            @PathVariable Long boardsId,
            @RequestBody ColumnsRequestDto columnDTO){
        ColumnsResponseDto responseDto = columnService.addColumn(boardsId,columnDTO);
        return ResponseEntity.ok().body(responseDto);
    }

    @DeleteMapping("{boardsId}/columns/{columnsId}")
    public ResponseEntity deleteColumn(
            @PathVariable Long boardsId,
            @PathVariable Long columnsId,
            @AuthenticationPrincipal UserDetailsImpl userDetails){
        columnService.deleteColumn(boardsId, columnsId, userDetails.getUser());
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{boardsId}/columns/{columnsId}")
    public ResponseEntity<ColumnsResponseDto> modifyColumn(
            @PathVariable Long boardsId,
            @PathVariable Long columnsId,
            @RequestBody ColumnsRequestDto columnDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails){
        ColumnsResponseDto responseDto = columnService.modifyColumn(boardsId, columnsId, columnDto, userDetails.getUser());
        return ResponseEntity.ok().body(responseDto);
    }

    @PostMapping("{boardsId}/columns/{columnsId}/order/{newOrder}")
    public ResponseEntity<ColumnsResponseDto> changeColumnOrder(
            @PathVariable Long boardsId,
            @PathVariable Long columnsId,
            @PathVariable int newOrder){
        ColumnsResponseDto responseDto = columnService.updateColumnSequence(boardsId, columnsId, newOrder);
        return ResponseEntity.ok().body(responseDto);
    }

    @GetMapping("{boardsId}/columns")
    public ResponseEntity<List<ColumnsResponseDto>> getColumns(
            @PathVariable Long boardsId){
        List<ColumnsResponseDto> columns = columnService.getColumns(boardsId);
        // 숨길 필요가 없다.
        return new ResponseEntity<>(columns, HttpStatus.OK);
    }
}
