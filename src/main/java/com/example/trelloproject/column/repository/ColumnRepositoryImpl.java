package com.example.trelloproject.column.repository;

import com.example.trelloproject.card.entity.QCard;
import com.querydsl.core.types.Expression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ColumnRepositoryImpl {
    private final JPAQueryFactory jpaQueryFactory;

    public void test(){

    }

    private <T> JPAQuery<T> query(Expression<T> expr, Long cardsId){
        // select * from card;
        jpaQueryFactory.selectFrom(QCard.card);
        return null;
    }
}
