package com.dmdev.webStore.dao.repository;

import com.dmdev.webStore.dao.repository.filter.OrderFilter;
import com.dmdev.webStore.dao.repository.filter.ProductFilter;
import com.dmdev.webStore.entity.Product;

import java.util.List;
import java.util.Optional;

public interface FilterProductRepository {

    List<Product> findListOfProductsEq(ProductFilter filter);

    List<Product> findProductOfOneCategoryAndBrandBetweenTwoPrice(ProductFilter filter);

    List<Product> findProductsOfBrandAndCategoryAndLtPrice(ProductFilter filter);

    List<Product> findProductsOfBrandAndCategoryAndGtPrice(ProductFilter filter);

    List<Product> findAllProductsFromOrder(OrderFilter orderFilter);


}
