package com.dmdev.webStore.repository;

import com.dmdev.webStore.repository.filter.OrderFilter;
import com.dmdev.webStore.entity.Product;

import java.util.List;

public interface FilterProductRepository {

    List<Product> findAllProductsFromOrder(OrderFilter orderFilter);


}
