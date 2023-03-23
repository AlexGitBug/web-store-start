package com.dmdev.webStore.dao.repository.filter;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.Builder;
import lombok.Value;


import java.util.ArrayList;
import java.util.List;

@Value
@Builder
public class FilterPredicate {

    public static List<Predicate> combinePredicates(BooleanExpression... expressions) {
        List<Predicate> predicates = new ArrayList<>();
        for (BooleanExpression expression : expressions) {
            if (expression != null) {
                predicates.add(expression);
            }
        }
        return predicates;
    }

}
