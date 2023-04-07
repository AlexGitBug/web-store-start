package com.dmdev.webStore.dao.repository;

import com.dmdev.webStore.dao.repository.filter.ProductFilter;
import com.dmdev.webStore.entity.ShoppingCart;
import com.querydsl.core.Tuple;
import org.springframework.data.jpa.repository.EntityGraph;

import java.util.List;

public interface FilterShoppingCartRepository {

    List<Tuple> getStatisticOfEachOrdersWithSum();

    List<ShoppingCart> findUsersWhoMadeOrderOfSpecificProduct(ProductFilter productFilter);
}
