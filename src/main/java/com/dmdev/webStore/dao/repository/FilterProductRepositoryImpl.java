package com.dmdev.webStore.dao.repository;

import com.dmdev.webStore.dao.repository.filter.OrderFilter;
import com.dmdev.webStore.dao.repository.filter.ProductFilter;
import com.dmdev.webStore.dao.repository.filter.QPredicate;
import com.dmdev.webStore.entity.Product;
import com.dmdev.webStore.entity.enums.Brand;
import com.querydsl.jpa.impl.JPAQuery;
import org.hibernate.graph.GraphSemantic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.EntityManager;
import java.util.List;

import static com.dmdev.webStore.entity.QCatalog.catalog;
import static com.dmdev.webStore.entity.QOrder.order;
import static com.dmdev.webStore.entity.QProduct.product;
import static com.dmdev.webStore.entity.QShoppingCart.shoppingCart;

public class FilterProductRepositoryImpl implements FilterProductRepository {
    @Autowired
    private EntityManager entityManager;

    public List<Product> findListOfProductsEq(ProductFilter filter) {

        var predicate = QPredicate.builder()
                .add(filter.getCatalog().getCategory(), catalog.category::eq)
                .add(filter.getBrand(), product.brand::eq)
                .buildAnd();

        return new JPAQuery<Product>(entityManager)
                .select(product)
                .from(product)
                .join(product.catalog, catalog)
                .where(predicate)
                .fetch();
    }

    @Override
    public List<Product> findProductOfOneCategoryAndBrandBetweenTwoPrice(ProductFilter filter, Integer sumA, Integer sumB) {

        var predicate = QPredicate.builder()
                .add(filter.getCatalog().getCategory(), catalog.category::eq)
                .add(filter.getBrand(), product.brand::eq)
                .add(sumA, product.price::gt)
                .add(sumB, product.price::lt)
                .buildAnd();

        return new JPAQuery<Product>(entityManager)
                .select(product)
                .from(product)
                .join(product.catalog, catalog)
                .join(product.shoppingCarts, shoppingCart)
                .join(shoppingCart.order, order)
                .where(predicate)
                .fetch();
    }

    @Override
    public List<Product> findProductsOfBrandAndCategoryAndLtPrice(ProductFilter filter) {

        var predicate = QPredicate.builder()
                .add(filter.getCatalog().getCategory(), catalog.category::eq)
                .add(filter.getBrand(), product.brand::eq)
                .add(filter.getPrice(), product.price::lt)
                .buildAnd();

        return new JPAQuery<Product>(entityManager)
                .select(product)
                .from(product)
                .join(product.catalog, catalog)
                .join(product.shoppingCarts, shoppingCart)
                .join(shoppingCart.order, order)
                .where(predicate)
                .fetch();
    }

    @Override
    public List<Product> findProductsOfBrandAndCategoryAndGtPrice(ProductFilter filter) {

        var predicate = QPredicate.builder()
                .add(filter.getCatalog().getCategory(), catalog.category::eq)
                .add(filter.getBrand(), product.brand::eq)
                .add(filter.getPrice(), product.price::gt)
                .buildAnd();

        return new JPAQuery<Product>(entityManager)
                .select(product)
                .from(product)
                .join(product.catalog, catalog)
                .join(product.shoppingCarts, shoppingCart)
                .join(shoppingCart.order, order)
                .where(predicate)
                .orderBy(product.price.asc())
//                .setHint(GraphSemantic.LOAD.getJpaHintName(), entityManager.getEntityGraph("withCatalog"))
                .distinct()
                .fetch();
    }

    @Override
    public List<Product> findAllProductOfBrand(Brand brand) {
        return new JPAQuery<Product>(entityManager)
                .select(product)
                .from(product)
                .where(product.brand.eq(brand))
                .fetch();
    }

    @Override
    @Query(value = "SELECT p FROM Product p ORDER BY p.price ASC LIMIT 1", nativeQuery = true)
    public Product findMinPriceOfProduct() {
        return null;
    }

    @Override
    public List<Product> findAllProductsFromOrder(OrderFilter orderFilter) {

        var predicate = QPredicate.builder()
                .add(orderFilter.getDeliveryDate(), order.deliveryDate::eq)
                .buildAnd();

        return new JPAQuery<Product>(entityManager)
                .select(product)
                .from(product)
                .join(product.shoppingCarts, shoppingCart)
                .join(shoppingCart.order, order)
                .where(predicate)
                .fetch();
    }


}

