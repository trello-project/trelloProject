package com.example.trelloproject.column.repository;

import com.example.trelloproject.column.entity.Columns;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ColumnsRepository extends JpaRepository<Columns, Long> {
}
