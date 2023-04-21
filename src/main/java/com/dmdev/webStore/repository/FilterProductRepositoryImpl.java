package com.dmdev.webStore.repository;

import com.dmdev.webStore.repository.filter.OrderFilter;
import com.dmdev.webStore.repository.filter.ProductFilter;
import com.dmdev.webStore.repository.filter.QPredicate;
import com.dmdev.webStore.entity.Product;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import java.util.List;

import static com.dmdev.webStore.entity.QCatalog.catalog;
import static com.dmdev.webStore.entity.QOrder.order;
import static com.dmdev.webStore.entity.QProduct.product;
import static com.dmdev.webStore.entity.QShoppingCart.shoppingCart;

@RequiredArgsConstructor
public class FilterProductRepositoryImpl implements FilterProductRepository {
    private final EntityManager entityManager;

    @Override
    public List<Product> findAllProductsFromOrder(OrderFilter orderFilter) {

        var predicate = QPredicate.builder()
                .add(orderFilter.getDeliveryDate(), order.deliveryDate::eq)
                .add(orderFilter.getId(), order.id::eq)
                .buildAnd();

        return new JPAQuery<Product>(entityManager)
                .select(product)
                .from(product)
                .join(product.shoppingCarts, shoppingCart)
                .join(shoppingCart.order, order)
                .where(predicate)
                .fetch();
    }

}

