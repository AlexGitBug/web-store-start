package com.dmdev.webStore.repository;

import com.dmdev.webStore.entity.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingCartRepository extends
        JpaRepository<ShoppingCart, Integer>,
        FilterShoppingCartRepository,
        QuerydslPredicateExecutor<ShoppingCart> {

}
