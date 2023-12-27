package com.example.trelloproject.Column.Repository;

import com.example.trelloproject.Column.Entity.Column;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ColumnRepository extends JpaRepository<Column, Long> {
}
