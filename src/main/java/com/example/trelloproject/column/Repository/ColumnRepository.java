package com.example.trelloproject.column.Repository;

import com.example.trelloproject.column.Entity.Column;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ColumnRepository extends JpaRepository<Column, Long> {
}
