package com.dmdev.webStore.repository;

import com.dmdev.webStore.entity.Order;
import com.dmdev.webStore.repository.filter.PersonalInformationFilter;
import com.dmdev.webStore.repository.filter.ProductFilter;
import com.dmdev.webStore.repository.filter.QPredicate;
import com.dmdev.webStore.entity.ShoppingCart;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.RequiredArgsConstructor;
import org.hibernate.graph.GraphSemantic;

import javax.persistence.EntityManager;
import java.util.List;

import static com.dmdev.webStore.entity.QCatalog.catalog;
import static com.dmdev.webStore.entity.QOrder.order;
import static com.dmdev.webStore.entity.QProduct.product;
import static com.dmdev.webStore.entity.QShoppingCart.shoppingCart;
import static com.dmdev.webStore.entity.QUser.user;

@RequiredArgsConstructor
public class FilterShoppingCartRepositoryImpl implements FilterShoppingCartRepository {
    private final EntityManager entityManager;

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
                .add(productFilter.getCatalogId(), catalog.id::eq)
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

    @Override
    public List<ShoppingCart> findAllOrdersWithProductsOfOneUser(PersonalInformationFilter personalInformationFilter) {
        var predicate = QPredicate.builder()
                .add(personalInformationFilter.getEmail(), user.personalInformation.email::eq)
                .buildAnd();

        return new JPAQuery<ShoppingCart>(entityManager)
                .select(shoppingCart)
                .from(shoppingCart)
                .join(shoppingCart.order, order)
                .join(shoppingCart.product, product)
                .join(order.user, user)
                .setHint(GraphSemantic.LOAD.getJpaHintName(), entityManager.getEntityGraph("findAllOrdersOfUsers"))
                .where(predicate)
                .fetch();
    }
}
