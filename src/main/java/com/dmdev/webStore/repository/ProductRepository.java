package com.dmdev.webStore.repository;

import com.dmdev.webStore.entity.Product;
import com.dmdev.webStore.entity.enums.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends
        JpaRepository<Product, Integer>,
        FilterProductRepository,
        QuerydslPredicateExecutor<Product> {
    List<Product> findAllByBrand(Brand brand);
    List<Product> findAllByCatalogCategory(String catalog);
    Optional<Product> findTopByOrderByPriceAsc();
    Optional<Product> findTopByOrderByPriceDesc();
}
