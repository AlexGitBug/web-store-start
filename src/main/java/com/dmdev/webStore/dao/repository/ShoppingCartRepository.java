package com.dmdev.webStore.dao.repository;

import com.dmdev.webStore.entity.ShoppingCart;
import com.dmdev.webStore.dao.repository.filter.QPredicate;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;
import com.dmdev.webStore.dao.repository.filter.ProductFilter;
import org.hibernate.graph.GraphSemantic;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.dmdev.webStore.entity.QCatalog.catalog;
import static com.dmdev.webStore.entity.QOrder.order;
import static com.dmdev.webStore.entity.QProduct.product;
import static com.dmdev.webStore.entity.QShoppingCart.shoppingCart;
import static com.dmdev.webStore.entity.QUser.user;

@Repository
public class ShoppingCartRepository extends RepositoryBase<Integer, ShoppingCart>{

    public ShoppingCartRepository(EntityManager entityManager) {
        super(ShoppingCart.class, entityManager);
    }

    public List<Tuple> getStatisticOfEachOrdersWithSum() {
        return new JPAQuery<Tuple>(getEntityManager())
                .select(shoppingCart)
                .from(shoppingCart)
                .join(shoppingCart.product, product)
                .join(shoppingCart.order, order)
                .groupBy(order.id)
                .select(order.id, product.price.sum())
                .fetch();
    }

    public List<ShoppingCart> findUsersWhoMadeOrderOfSpecificProduct(ProductFilter productFilter) {

        var predicate = QPredicate.builder()
                .add(productFilter.getBrand(), product.brand::eq)
                .add(productFilter.getCatalog().getCategory(), catalog.category::eq)
                .add(productFilter.getModel(), product.model::eq)
                .buildAnd();

        return new JPAQuery<ShoppingCart>(getEntityManager())
                .select(shoppingCart)
                .from(shoppingCart)
                .join(shoppingCart.order, order)
                .join(order.user, user)
                .join(shoppingCart.product, product)
                .join(product.catalog, catalog)
                .where(predicate)
                .setHint(GraphSemantic.LOAD.getJpaHintName(), getEntityManager().getEntityGraph("findAllOrdersOfUsers"))
                .fetch();
    }
}
