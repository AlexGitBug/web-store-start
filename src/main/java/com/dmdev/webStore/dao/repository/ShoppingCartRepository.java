package com.dmdev.webStore.dao.repository;

import com.dmdev.webStore.entity.ShoppingCart;
import com.dmdev.webStore.dao.repository.filter.QPredicate;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;
import com.dmdev.webStore.dao.repository.filter.ProductFilter;
import org.hibernate.graph.GraphSemantic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.dmdev.webStore.entity.QCatalog.catalog;
import static com.dmdev.webStore.entity.QOrder.order;
import static com.dmdev.webStore.entity.QProduct.product;
import static com.dmdev.webStore.entity.QShoppingCart.shoppingCart;
import static com.dmdev.webStore.entity.QUser.user;

@Repository
public interface ShoppingCartRepository extends
        JpaRepository<ShoppingCart, Integer>,
        FilterShoppingCartRepository {

}
