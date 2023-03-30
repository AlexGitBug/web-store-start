package com.dmdev.webStore.dao.repository;

import com.dmdev.webStore.dao.repository.filter.UserFilter;
import com.dmdev.webStore.entity.Order;
import com.querydsl.jpa.impl.JPAQuery;
import org.hibernate.graph.GraphSemantic;
import org.springframework.stereotype.Repository;
import com.dmdev.webStore.dao.repository.filter.QPredicate;

import javax.persistence.EntityManager;
import java.util.List;

import static com.dmdev.webStore.entity.QOrder.order;
import static com.dmdev.webStore.entity.QProduct.product;
import static com.dmdev.webStore.entity.QShoppingCart.shoppingCart;
import static com.dmdev.webStore.entity.QUser.user;

@Repository
public class OrderRepository extends  RepositoryBase<Integer, Order>{
    public OrderRepository(EntityManager entityManager) {
        super(Order.class, entityManager);
    }

    public List<Order> findAllOrdersWithProductsOfOneUser(UserFilter userFilter) {

        var predicate = QPredicate.builder()
                .add(userFilter.getPersonalInformation().getEmail(), user.personalInformation.email::eq)
                .buildAnd();

        return new JPAQuery<Order>(getEntityManager())
                .select(order)
                .from(order)
                .join(order.user, user)
                .join(order.shoppingCarts, shoppingCart)
                .join(shoppingCart.product, product)
                .setHint(GraphSemantic.LOAD.getJpaHintName(), getEntityManager().getEntityGraph("findAllOrdersOfUsers"))
                .where(predicate)
                .fetch();
    }

}
