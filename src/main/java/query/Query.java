package query;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;
import entity.Product;
import entity.ShoppingCart;
import entity.User;
import org.hibernate.Session;
import org.hibernate.graph.GraphSemantic;
import query.filter.OrderFilter;
import query.filter.ProductFilter;
import query.filter.UserFilter;
import service.QPredicate;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static entity.QCatalog.catalog;
import static entity.QOrder.order;
import static entity.QProduct.product;
import static entity.QShoppingCart.shoppingCart;
import static entity.QUser.user;


public class Query {

    private static final Query INSTANCE = new Query();

    //пробный тест на поиск определенного товара


    public Product findById(Session session, Integer productId) {

        session.getEntityGraph("withCatalog");

        Map<String, Object> properties = Map.of(
                GraphSemantic.LOAD.getJpaHintName(), session.getEntityGraph("withCatalog"));

        return session.find(Product.class, productId, properties);
    }

    public List<Product> findOneProductEq(Session session, ProductFilter filter) {

        var predicate = QPredicate.builder()
                .add(filter.getCatalog().getCategory(), catalog.category::eq)
                .add(filter.getBrand(), product.brand::eq)
                .add(filter.getDateOfRelease(), product.dateOfRelease::eq)
                .add(filter.getPrice(), product.price::eq)
                .add(filter.getColor(), product.color::eq)
                .buildAnd();

        return new JPAQuery<Product>(session)
                .select(product)
                .from(product)
                .join(product.catalog, catalog)
                .where(predicate)
                .setHint(GraphSemantic.LOAD.getJpaHintName(), session.getEntityGraph("withCatalog"))
                .fetch();
    }

    //Список всех товаров выше 1000 и определенного бренда
    public List<Product> findProductsGtPriceAndBrand(Session session, ProductFilter filter) {

        var predicate = QPredicate.builder()
                .add(filter.getCatalog().getCategory(), catalog.category::eq)
                .add(filter.getBrand(), product.brand::eq)
                .add(filter.getPrice(), product.price::gt)
                .buildAnd();

        return new JPAQuery<Product>(session)
                .select(product)
                .from(product)
                .join(product.catalog, catalog)
                .where(predicate)
                .orderBy(product.price.asc())
                .setHint(GraphSemantic.LOAD.getJpaHintName(), session.getEntityGraph("withCatalog"))
                .fetch();
    }

    //Список всех продуктов по бренду
    public List<Product> findAllProductOfBrand(Session session, ProductFilter filter) {

        var predicate = QPredicate.builder()
                .add(filter.getBrand(), product.brand::eq)
                .buildAnd();

        return new JPAQuery<Product>(session)
                .select(product)
                .from(product)
                .where(predicate)
                .fetch();
    }

//    public List<Catalog> findCatalogOfProductWithBrandFromShoppingCart(Session session, CatalogFilter catalogFilter, ProductFilter productFilter) {
//        var predicate = QPredicate.builder()
//                .add(productFilter.getBrand(), product.brand::eq)
//                .add(catalogFilter.getCategory(), catalog.category::eq)
//                .buildAnd();
//
//        return new JPAQuery<Catalog>(session)
//                .select(catalog)
//                .from(catalog)
//                .join(catalog.products, product)
//                .join(product.shoppingCarts, shoppingCart)
//                .where(predicate)
//                .fetch();
//    }

    public List<Product> findAllProductsFromOrder(Session session, OrderFilter orderFilter, UserFilter userFilter) {
        var predicate = QPredicate.builder()
                .add(orderFilter.getDeliveryAdress().getCity(), order.deliveryAdress.city::eq)
                .add(orderFilter.getDeliveryAdress().getStreet(), order.deliveryAdress.street::eq)
                .add(orderFilter.getDeliveryAdress().getBuilding(), order.deliveryAdress.building::eq)
                .add(userFilter.getPersonalInformation().getTelephone(), user.personalInformation.telephone::eq)
                .buildAnd();

        return new JPAQuery<Product>(session)
                .select(product)
                .from(product)
                .join(product.shoppingCarts, shoppingCart)
                .join(shoppingCart.order, order)
                .join(order.user, user)
                .setHint(GraphSemantic.LOAD.getJpaHintName(), session.getEntityGraph("withCatalog"))
                .where(predicate)
                .fetch();
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

    public List<ShoppingCart> findUserWhoMadeOrderInTime(Session session, LocalDate startDate, LocalDate endDate) {
//        var predicate = QPredicate.builder()
//                .add(shoppingCartFilter.getCreatedAt(), shoppingCart.createdAt::eq)
//                .buildAnd();

        return new JPAQuery<ShoppingCart>(session)
                .select(shoppingCart)
                .from(shoppingCart)
                .join(shoppingCart.order, order)
                .join(order.user, user)
                .where(shoppingCart.createdAt.between(startDate, endDate))
                .setHint(GraphSemantic.LOAD.getJpaHintName(), session.getEntityGraph("findAllOrdersOfUsers"))
                .fetch();
    }

    public Product findMinPriceOfProduct(Session session) {
        var result = session.createQuery("select p from Product p order by p.price asc", Product.class)
                .setMaxResults(1)
                .uniqueResult();
        return result;
    }

    public List<Tuple> getStatisticOfAllOrders(Session session) {

        return new JPAQuery<Tuple>(session)
                .select(shoppingCart)
                .from(shoppingCart)
                .join(shoppingCart.product, product)
                .join(shoppingCart.order, order)
                .groupBy(order.id)
                .select(order.id, product.price.sum())
                .fetch();

    }



    public List<User> findUsersWhoMadeAnOrderSpecificProduct(Session session, UserFilter userFilter) {
        return null;
    }

    public List<User> findUsersWhoPayOrderWithCash(Session session, UserFilter userFilter) {
        return null;
    }


    public static Query getInstance() {
        return INSTANCE;
    }


}