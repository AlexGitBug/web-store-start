package com.dmdev.webStore.dao.repository;

import com.dmdev.webStore.entity.User;
import com.querydsl.jpa.impl.JPAQuery;
import org.hibernate.graph.GraphSemantic;
import org.springframework.stereotype.Repository;
import com.dmdev.webStore.dao.repository.filter.QPredicate;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

import static com.dmdev.webStore.entity.QOrder.order;
import static com.dmdev.webStore.entity.QUser.user;

@Repository
public class UserRepository extends RepositoryBase<Integer, User>{

    public UserRepository(EntityManager entityManager) {
        super(User.class, entityManager);
    }

    public List<User> findUsersWhoMadeOrderSpecificTime(LocalDate startDate, LocalDate endDate) {

        var predicate = QPredicate.builder()
                .add(startDate, order.deliveryDate::gt)
                .add(endDate, order.deliveryDate::lt)
                .buildAnd();

        return new JPAQuery<User>(getEntityManager())
                .select(user)
                .from(user)
                .join(user.orders, order)
                .where(predicate)
                .setHint(GraphSemantic.LOAD.getJpaHintName(), getEntityManager().getEntityGraph("findAllOrdersOfUsers"))
                .fetch();
    }

}
