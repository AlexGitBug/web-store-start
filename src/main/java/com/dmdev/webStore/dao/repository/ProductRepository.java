package com.dmdev.webStore.dao.repository;

import com.dmdev.webStore.entity.Catalog;
import com.dmdev.webStore.entity.Product;
import com.dmdev.webStore.dao.repository.filter.QPredicate;
import com.dmdev.webStore.entity.enums.Brand;
import com.querydsl.jpa.impl.JPAQuery;
import org.hibernate.graph.GraphSemantic;
import com.dmdev.webStore.dao.repository.filter.OrderFilter;
import com.dmdev.webStore.dao.repository.filter.ProductFilter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static com.dmdev.webStore.entity.QCatalog.catalog;
import static com.dmdev.webStore.entity.QOrder.order;
import static com.dmdev.webStore.entity.QProduct.product;
import static com.dmdev.webStore.entity.QShoppingCart.shoppingCart;

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

