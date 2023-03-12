package query;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class CritetiaPredicate {
    private static final List<Predicate> predicates = new ArrayList<>();

    //инициализировать вместо конструктора
    public static CritetiaPredicate builder() {
        return new CritetiaPredicate();
    }

    //добавление Predicate в список predicates, где Т - может быть любой объект
    public <T> CritetiaPredicate add(T object, Function<T, Predicate> function) {
        if (object != null) {
            predicates.add(function.apply(object));
        }
        return this;
    }

    //автоматически вызывает "and" и получается один единственный Predicate
//    public Predicate buildAnd() {
//        return ExpressionUtils.allOf(predicates);
//    }
//
//    //автоматически вызывает "or" и получается один единственный Predicate
//    public Predicate buildOr() {
//        return ExpressionUtils.anyOf(predicates);
//    }
}
