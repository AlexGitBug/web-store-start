package com.dmdev.webStore.query;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class QPredicate {

    private static final List<Predicate> predicates = new ArrayList<>();

    //инициализировать вместо конструктора
    public static QPredicate builder() {
        return new QPredicate();
    }

    //добавление Predicate в список predicates, где Т - может быть любой объект
    public <T> QPredicate add(T object, Function<T, Predicate> function) {
        if (object != null) {
            predicates.add(function.apply(object));
        }
        return this;
    }

//    автоматически вызывает "and" и получается один единственный Predicate
    public Predicate buildAnd() {
        return ExpressionUtils.allOf(predicates);
    }

    //автоматически вызывает "or" и получается один единственный Predicate
    public Predicate buildOr() {
        return ExpressionUtils.anyOf(predicates);
    }
}
