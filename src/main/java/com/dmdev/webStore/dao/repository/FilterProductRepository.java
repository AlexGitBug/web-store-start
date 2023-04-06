package com.dmdev.webStore.dao.repository;

import com.dmdev.webStore.dao.repository.filter.OrderFilter;
import com.dmdev.webStore.dao.repository.filter.ProductFilter;
import com.dmdev.webStore.entity.Product;
import com.dmdev.webStore.entity.enums.Brand;

import java.util.List;

public interface FilterProductRepository {

    List<Product> findListOfProductsEq(ProductFilter filter);

    List<Product> findProductOfOneCategoryAndBrandBetweenTwoPrice(ProductFilter filter, Integer sumA, Integer sumB);

    List<Product> findProductsOfBrandAndCategoryAndLtPrice(ProductFilter filter);

    List<Product> findProductsOfBrandAndCategoryAndGtPrice(ProductFilter filter);

    List<Product> findAllProductOfBrand(Brand brand);


    Product findMinPriceOfProduct();

    List<Product> findAllProductsFromOrder(OrderFilter orderFilter);
}
