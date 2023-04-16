package com.dmdev.webStore.dao.repository;

import com.dmdev.webStore.dao.repository.filter.OrderFilter;
import com.dmdev.webStore.dao.repository.filter.ProductFilter;
import com.dmdev.webStore.entity.Product;

import java.util.List;

public interface FilterProductRepository {

    List<Product> findListOfProductsEq(ProductFilter filter);

    List<Product> findAllProductsFromOrder(OrderFilter orderFilter);


}
