package dao.repository;

import com.querydsl.jpa.impl.JPAQuery;
import entity.Product;
import org.hibernate.Session;
import org.hibernate.graph.GraphSemantic;
import query.QPredicate;
import dao.repository.filter.OrderFilter;
import dao.repository.filter.ProductFilter;
import dao.repository.filter.UserFilter;

import javax.persistence.EntityManager;
import java.util.List;

import static entity.QCatalog.catalog;
import static entity.QOrder.order;
import static entity.QProduct.product;
import static entity.QShoppingCart.shoppingCart;
import static entity.QUser.user;

public class ProductRepository extends RepositoryBase<Integer, Product> {

    public ProductRepository(Class<Product> clazz, EntityManager entityManager) {
        super(clazz, entityManager);
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

    public List<Product> findListOfProductOfOneCategoryAndBrandBetweenTwoPrice(ProductFilter filter, Integer sumA, Integer sumB) {

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
                .where(predicate)
                .setHint(GraphSemantic.LOAD.getJpaHintName(), getEntityManager().getEntityGraph("withCatalog"))
                .fetch();
    }

    public List<Product> findListOfProductGtPrice(ProductFilter filter, Integer price) {

        var predicate = QPredicate.builder()
                .add(filter.getCatalog().getCategory(), catalog.category::eq)
                .add(filter.getBrand(), product.brand::eq)
                .add(price, product.price::gt)
                .buildAnd();

        return new JPAQuery<Product>(getEntityManager())
                .select(product)
                .from(product)
                .join(product.catalog, catalog)
                .where(predicate)
                .setHint(GraphSemantic.LOAD.getJpaHintName(), getEntityManager().getEntityGraph("withCatalog"))
                .fetch();
    }

    public List<Product> findListOfProductLtPrice(ProductFilter filter, Integer price) {

        var predicate = QPredicate.builder()
                .add(filter.getCatalog().getCategory(), catalog.category::eq)
                .add(filter.getBrand(), product.brand::eq)
                .add(price, product.price::lt)
                .buildAnd();

        return new JPAQuery<Product>(getEntityManager())
                .select(product)
                .from(product)
                .join(product.catalog, catalog)
                .where(predicate)
                .setHint(GraphSemantic.LOAD.getJpaHintName(), getEntityManager().getEntityGraph("withCatalog"))
                .fetch();
    }

    public List<Product> findProductsGtPriceAndBrand(ProductFilter filter) {

        var predicate = QPredicate.builder()
                .add(filter.getCatalog().getCategory(), catalog.category::eq)
                .add(filter.getBrand(), product.brand::eq)
                .add(filter.getPrice(), product.price::gt)
                .buildAnd();

        return new JPAQuery<Product>(getEntityManager())
                .select(product)
                .from(product)
                .join(product.catalog, catalog)
                .where(predicate)
                .orderBy(product.price.asc())
                .setHint(GraphSemantic.LOAD.getJpaHintName(), getEntityManager().getEntityGraph("withCatalog"))
                .fetch();
    }

    public List<Product> findAllProductOfBrand(ProductFilter filter) {

        var predicate = QPredicate.builder()
                .add(filter.getBrand(), product.brand::eq)
                .buildAnd();

        return new JPAQuery<Product>(getEntityManager())
                .select(product)
                .from(product)
                .where(predicate)
                .fetch();
    }

    public List<Product> findAllProductsFromOrder(OrderFilter orderFilter) {

        var predicate = QPredicate.builder()
                .add(orderFilter.getId(), order.id::eq)
                .buildAnd();

        return new JPAQuery<Product>(getEntityManager())
                .select(product)
                .from(product)
                .join(product.shoppingCarts, shoppingCart)
                .join(shoppingCart.order, order)
                .join(order.user, user)
                .setHint(GraphSemantic.LOAD.getJpaHintName(), getEntityManager().getEntityGraph("withCatalog"))
                .where(predicate)
                .fetch();
    }

    public Product findMinPriceOfProduct() {
        var result = getEntityManager().createQuery("select p from Product p order by p.price asc", Product.class)
                .setMaxResults(1)
                .getSingleResult();
        return result;
    }
}
