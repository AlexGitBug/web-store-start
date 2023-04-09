package com.dmdev.webStore.dao.repository;

import com.dmdev.webStore.dao.repository.filter.QPredicate;
import com.dmdev.webStore.dao.repository.filter.UserFilter;
import com.dmdev.webStore.entity.User;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

import static com.dmdev.webStore.entity.QOrder.order;
import static com.dmdev.webStore.entity.QUser.user;


public class FilterUserRepositoryImpl implements FilterUserRepository {
    @Autowired
    private EntityManager entityManager;
    @Override
    public List<User> findUsersWhoMadeOrderSpecificTime(UserFilter filter) {

        var predicate = QPredicate.builder()
                .add(filter.getStartDate(), order.deliveryDate::gt)
                .add(filter.getEndDate(), order.deliveryDate::lt)
                .buildAnd();

        return new JPAQuery<User>(entityManager)
                .select(user)
                .from(user)
                .join(user.orders, order)
                .where(predicate)
                .fetch();
    }
}

