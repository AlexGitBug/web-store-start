package com.dmdev.webStore.repository;

import com.dmdev.webStore.repository.filter.PersonalInformationFilter;
import com.dmdev.webStore.repository.filter.QPredicate;
import com.dmdev.webStore.entity.Order;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.RequiredArgsConstructor;
import org.hibernate.graph.GraphSemantic;

import javax.persistence.EntityManager;
import java.util.List;

import static com.dmdev.webStore.entity.QOrder.order;
import static com.dmdev.webStore.entity.QProduct.product;
import static com.dmdev.webStore.entity.QShoppingCart.shoppingCart;
import static com.dmdev.webStore.entity.QUser.user;

@RequiredArgsConstructor
public class FilterOrderRepositoryImpl implements FilterOrderRepository {
    private final EntityManager entityManager;
    @Override
    public List<Order> findAllOrdersWithProductsOfOneUser(PersonalInformationFilter personalInformationFilter) {
        var predicate = QPredicate.builder()
                .add(personalInformationFilter.getEmail(), user.personalInformation.email::eq)
                .buildAnd();

        return new JPAQuery<Order>(entityManager)
                .select(order)
                .from(order)
                .join(order.user, user)
                .join(order.shoppingCarts, shoppingCart)
                .join(shoppingCart.product, product)
                .setHint(GraphSemantic.LOAD.getJpaHintName(), entityManager.getEntityGraph("findAllOrdersOfUsers"))
                .where(predicate)
                .fetch();
    }
}

