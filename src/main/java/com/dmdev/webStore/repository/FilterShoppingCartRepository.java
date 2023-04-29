package com.dmdev.webStore.repository;

import com.dmdev.webStore.repository.filter.PersonalInformationFilter;
import com.dmdev.webStore.repository.filter.ProductFilter;
import com.dmdev.webStore.entity.ShoppingCart;
import com.querydsl.core.Tuple;

import java.util.List;

public interface FilterShoppingCartRepository {

    List<Tuple> getStatisticOfEachOrdersWithSum();

    List<ShoppingCart> findUsersWhoMadeOrderOfSpecificProduct(ProductFilter productFilter);

    List<ShoppingCart> findAllOrdersWithProductsOfOneUser(PersonalInformationFilter filter);
}
