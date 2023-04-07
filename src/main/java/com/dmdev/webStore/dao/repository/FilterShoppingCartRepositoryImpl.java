package com.dmdev.webStore.dao.repository;

import com.dmdev.webStore.dao.repository.filter.ProductFilter;
import com.dmdev.webStore.dao.repository.filter.QPredicate;
import com.dmdev.webStore.entity.ShoppingCart;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.RequiredArgsConstructor;
import org.hibernate.graph.GraphSemantic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.EntityGraph;

import javax.persistence.EntityManager;
import java.util.List;

import static com.dmdev.webStore.entity.QCatalog.catalog;
import static com.dmdev.webStore.entity.QOrder.order;
import static com.dmdev.webStore.entity.QProduct.product;
import static com.dmdev.webStore.entity.QShoppingCart.shoppingCart;
import static com.dmdev.webStore.entity.QUser.user;

public class FilterShoppingCartRepositoryImpl implements FilterShoppingCartRepository {
    @Autowired
    private EntityManager entityManager;
    @Override
    public List<Tuple> getStatisticOfEachOrdersWithSum() {
        return new JPAQuery<Tuple>(entityManager)
                .select(shoppingCart)
                .from(shoppingCart)
                .join(shoppingCart.product, product)
                .join(shoppingCart.order, order)
                .groupBy(order.id)
                .select(order.id, product.price.sum())
                .fetch();
    }

    @Override
    public List<ShoppingCart> findUsersWhoMadeOrderOfSpecificProduct(ProductFilter productFilter) {
        var predicate = QPredicate.builder()
                .add(productFilter.getBrand(), product.brand::eq)
                .add(productFilter.getCatalog().getCategory(), catalog.category::eq)
                .add(productFilter.getModel(), product.model::eq)
                .buildAnd();

        return new JPAQuery<ShoppingCart>(entityManager)
                .select(shoppingCart)
                .from(shoppingCart)
                .join(shoppingCart.order, order)
                .join(order.user, user)
                .join(shoppingCart.product, product)
                .join(product.catalog, catalog)
                .setHint(GraphSemantic.LOAD.getJpaHintName(), entityManager.getEntityGraph("findAllOrdersOfUsers"))
                .where(predicate)
                .fetch();
    }
}
