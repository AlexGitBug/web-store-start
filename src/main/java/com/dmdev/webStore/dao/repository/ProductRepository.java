package com.dmdev.webStore.dao.repository;

import com.dmdev.webStore.entity.Product;
import com.dmdev.webStore.dao.repository.filter.QPredicate;
import com.querydsl.jpa.impl.JPAQuery;
import org.hibernate.graph.GraphSemantic;
import com.dmdev.webStore.dao.repository.filter.OrderFilter;
import com.dmdev.webStore.dao.repository.filter.ProductFilter;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.dmdev.webStore.entity.QCatalog.catalog;
import static com.dmdev.webStore.entity.QOrder.order;
import static com.dmdev.webStore.entity.QProduct.product;
import static com.dmdev.webStore.entity.QShoppingCart.shoppingCart;

@Repository
public class ProductRepository extends RepositoryBase<Integer, Product> {
    public ProductRepository(EntityManager entityManager) {
        super(Product.class, entityManager);
    }
    public List<Product> findListOfProductsEq(ProductFilter filter) {

        var predicate = QPredicate.builder()
                .add(filter.getCatalog().getCategory(), catalog.category::eq)
                .add(filter.getBrand(), product.brand::eq)
                .buildAnd();

        return new JPAQuery<Product>(getEntityManager())
                .select(product)
                .from(product)
                .join(product.catalog, catalog)
                .where(predicate)
                .setHint(GraphSemantic.LOAD.getJpaHintName(), getEntityManager().getEntityGraph("withCatalog"))
                .fetch();
    }

    public List<Product> findProductOfOneCategoryAndBrandBetweenTwoPrice(ProductFilter filter, Integer sumA, Integer sumB) {

        var predicate = QPredicate.builder()
                .add(filter.getCatalog().getCategory(), catalog.category::eq)
                .add(filter.getBrand(), product.brand::eq)
                .add(sumA, product.price::gt)
                .add(sumB, product.price::lt)
                .buildAnd();

        return new JPAQuery<Product>(getEntityManager())
                .select(product)
                .from(product)
                .join(product.catalog, catalog)
                .join(product.shoppingCarts, shoppingCart)
                .join(shoppingCart.order, order)
                .where(predicate)
                .setHint(GraphSemantic.LOAD.getJpaHintName(), getEntityManager().getEntityGraph("withCatalog"))
                .fetch();
    }
    public List<Product> findProductsOfBrandAndCategoryAndLtPrice(ProductFilter filter) {

        var predicate = QPredicate.builder()
                .add(filter.getCatalog().getCategory(), catalog.category::eq)
                .add(filter.getBrand(), product.brand::eq)
                .add(filter.getPrice(), product.price::lt)
                .buildAnd();

        return new JPAQuery<Product>(getEntityManager())
                .select(product)
                .from(product)
                .join(product.catalog, catalog)
                .join(product.shoppingCarts, shoppingCart)
                .join(shoppingCart.order, order)
                .where(predicate)
                .setHint(GraphSemantic.LOAD.getJpaHintName(), getEntityManager().getEntityGraph("withCatalog"))
                .fetch();
    }
    public List<Product> findProductsOfBrandAndCategoryAndGtPrice(ProductFilter filter) {

        var predicate = QPredicate.builder()
                .add(filter.getCatalog().getCategory(), catalog.category::eq)
                .add(filter.getBrand(), product.brand::eq)
                .add(filter.getPrice(), product.price::gt)
                .buildAnd();

        return new JPAQuery<Product>(getEntityManager())
                .select(product)
                .from(product)
                .join(product.catalog, catalog)
                .join(product.shoppingCarts, shoppingCart)
                .join(shoppingCart.order, order)
                .where(predicate)
                .orderBy(product.price.asc())
                .setHint(GraphSemantic.LOAD.getJpaHintName(), getEntityManager().getEntityGraph("withCatalog"))
                .distinct()
                .fetch();
    }

    public List<Product> findAllProductOfBrand(ProductFilter filter) {

        var predicate = QPredicate.builder()
                .add(filter.getBrand(), product.brand::eq)
                .buildAnd();

        return new JPAQuery<Product>(getEntityManager())
                .select(product)
                .from(product)
                .join(product.catalog, catalog)
//                .join(product.shoppingCarts, shoppingCart)
//                .join(shoppingCart.order, order)
                .where(predicate)
                .distinct()
                .fetch();
    }

    public List<Product> findAllProductsFromOrder(OrderFilter orderFilter) {

        var predicate = QPredicate.builder()
                .add(orderFilter.getDeliveryDate(), order.deliveryDate::eq)
                .buildAnd();

        return new JPAQuery<Product>(getEntityManager())
                .select(product)
                .from(product)
                .join(product.catalog, catalog)
                .join(product.shoppingCarts, shoppingCart)
                .join(shoppingCart.order, order)
                .where(predicate)
                .setHint(GraphSemantic.LOAD.getJpaHintName(), getEntityManager().getEntityGraph("withCatalog"))
                .fetch();
    }

    public Product findMinPriceOfProduct() {
        var result = getEntityManager().createQuery("select p from Product p order by p.price asc", Product.class)
                .setMaxResults(1)
                .getSingleResult();
        return result;
    }
}
