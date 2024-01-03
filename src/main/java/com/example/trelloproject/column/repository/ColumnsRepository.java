package com.example.trelloproject.column.repository;

import com.example.trelloproject.column.entity.Columns;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ColumnsRepository extends JpaRepository<Columns, Long> {
    List<Columns> findByBoardIdOrderByOrderAsc(Long id);
}
