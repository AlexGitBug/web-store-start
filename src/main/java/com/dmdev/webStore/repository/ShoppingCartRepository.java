package com.dmdev.webStore.repository;

import com.dmdev.webStore.entity.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import java.util.List;

@Repository
public interface ShoppingCartRepository extends
        JpaRepository<ShoppingCart, Integer>,
        FilterShoppingCartRepository,
        QuerydslPredicateExecutor<ShoppingCart> {

    List<ShoppingCart> findShoppingCartByOrderId(Integer orderId);

}
