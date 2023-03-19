package dao.repository;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;
import dao.repository.filter.FilterPredicate;
import dao.repository.filter.ProductFilter;
import dao.repository.filter.UserFilter;
import entity.ShoppingCart;
import org.hibernate.Session;
import org.hibernate.graph.GraphSemantic;
import query.QPredicate;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

import static entity.QCatalog.catalog;
import static entity.QOrder.order;
import static entity.QProduct.product;
import static entity.QShoppingCart.shoppingCart;
import static entity.QUser.user;

public class ShoppingCartRepository extends RepositoryBase<Integer, ShoppingCart>{

    public ShoppingCartRepository(Class<ShoppingCart> clazz, EntityManager entityManager) {
        super(clazz, entityManager);
    }

    public List<ShoppingCart> findAllOrdersWithProductsOfOneUser(Session session, UserFilter userFilter) {

        var predicate = QPredicate.builder()
                .add(userFilter.getPersonalInformation().getEmail(), user.personalInformation.email::eq)
                .buildAnd();

        return new JPAQuery<ShoppingCart>(session)
                .select(shoppingCart)
                .from(shoppingCart)
                .join(shoppingCart.order, order)
                .join(order.user, user)
                .join(shoppingCart.product, product)
                .join(product.catalog)
                .setHint(GraphSemantic.LOAD.getJpaHintName(), session.getEntityGraph("findAllOrdersOfUsers"))
                .where(predicate)
                .fetch();
    }

    public List<ShoppingCart> findUsersWhoMadeAnOrderAtASpecificTime(Session session, LocalDate startDate, LocalDate endDate) {

        return new JPAQuery<ShoppingCart>(session)
                .select(shoppingCart)
                .from(shoppingCart)
                .join(shoppingCart.order, order)
                .join(order.user, user)
                .where(shoppingCart.createdAt.between(startDate, endDate))
                .setHint(GraphSemantic.LOAD.getJpaHintName(), session.getEntityGraph("findAllOrdersOfUsers"))
                .fetch();
    }

    public List<Tuple> getStatisticOfEachOrdersWithSum(Session session) {

        return new JPAQuery<Tuple>(session)
                .select(shoppingCart)
                .from(shoppingCart)
                .join(shoppingCart.product, product)
                .join(shoppingCart.order, order)
                .groupBy(order.id)
                .select(order.id, product.price.sum())
                .fetch();
    }

    public List<ShoppingCart> findUsersWhoMadeAnOrderOfSpecificProduct(Session session, ProductFilter productFilter) {


        var predicate = QPredicate.builder()
                .add(productFilter.getBrand(), product.brand::eq)
                .add(productFilter.getCatalog().getCategory(), catalog.category::eq)
                .add(productFilter.getModel(), product.model::eq)
                .buildAnd();

        return new JPAQuery<ShoppingCart>(session)
                .select(shoppingCart)
                .from(shoppingCart)
                .join(shoppingCart.order, order)
                .join(order.user, user)
                .join(shoppingCart.product, product)
                .join(product.catalog, catalog)
                .where(predicate)
                .setHint(GraphSemantic.LOAD.getJpaHintName(), session.getEntityGraph("findAllOrdersOfUsers"))
                .fetch();
    }
}
