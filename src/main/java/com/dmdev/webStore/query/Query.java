package com.dmdev.webStore.query;

public class Query {

    private static final Query INSTANCE = new Query();

//    public Product findById(Session session, Integer productId) {
//
//        session.getEntityGraph("withCatalog");
//
//        Map<String, Object> properties = Map.of(
//                GraphSemantic.LOAD.getJpaHintName(), session.getEntityGraph("withCatalog"));
//
//        return session.find(Product.class, productId, properties);
//    }

//    public List<Product> findListOfProductsEq(Session session, ProductFilter filter) {
//
//        var predicate = QPredicate.builder()
//                .add(filter.getCatalog().getCategory(), catalog.category::eq)
//                .add(filter.getBrand(), product.brand::eq)
//                .buildAnd();
//
//        return new JPAQuery<Product>(session)
//                .select(product)
//                .from(product)
//                .join(product.catalog, catalog)
//                .where(predicate)
//                .setHint(GraphSemantic.LOAD.getJpaHintName(), session.getEntityGraph("withCatalog"))
//                .fetch();
//    }


//    public List<Product> findListOfProductBetweenTwoPrice(Session session, ProductFilter filter, Integer sumA, Integer sumB) {
//
//        var predicate = QPredicate.builder()
//                .add(filter.getCatalog().getCategory(), catalog.category::eq)
//                .add(filter.getBrand(), product.brand::eq)
//                .buildAnd();
//
//        return new JPAQuery<Product>(session)
//                .select(product)
//                .from(product)
//                .join(product.catalog, catalog)
//                .where(predicate, product.price.between(sumA, sumB))
//                .setHint(GraphSemantic.LOAD.getJpaHintName(), session.getEntityGraph("withCatalog"))
//                .fetch();
//    }

//    public List<Product> findListOfProductGtPrice(Session session, ProductFilter filter, Integer price) {
//
//        var predicate = QPredicate.builder()
//                .add(filter.getCatalog().getCategory(), catalog.category::eq)
//                .add(filter.getBrand(), product.brand::eq)
//                .add(price, product.price::gt)
//                .buildAnd();
//
//        return new JPAQuery<Product>(session)
//                .select(product)
//                .from(product)
//                .join(product.catalog, catalog)
//                .where(predicate)
//                .setHint(GraphSemantic.LOAD.getJpaHintName(), session.getEntityGraph("withCatalog"))
//                .fetch();
//    }

//    public List<Product> findListOfProductLtPrice(Session session, ProductFilter filter, Integer price) {
//
//        var predicate = QPredicate.builder()
//                .add(filter.getCatalog().getCategory(), catalog.category::eq)
//                .add(filter.getBrand(), product.brand::eq)
//                .add(price, product.price::lt)
//                .buildAnd();
//
//        return new JPAQuery<Product>(session)
//                .select(product)
//                .from(product)
//                .join(product.catalog, catalog)
//                .where(predicate)
//                .setHint(GraphSemantic.LOAD.getJpaHintName(), session.getEntityGraph("withCatalog"))
//                .fetch();
//    }

    //Список всех товаров выше 1000 и определенного бренда
//    public List<Product> findProductsGtPriceAndBrand(Session session, ProductFilter filter) {
//
//        var predicate = QPredicate.builder()
//                .add(filter.getCatalog().getCategory(), catalog.category::eq)
//                .add(filter.getBrand(), product.brand::eq)
//                .add(filter.getPrice(), product.price::gt)
//                .buildAnd();
//
//        return new JPAQuery<Product>(session)
//                .select(product)
//                .from(product)
//                .join(product.catalog, catalog)
//                .where(predicate)
//                .orderBy(product.price.asc())
//                .setHint(GraphSemantic.LOAD.getJpaHintName(), session.getEntityGraph("withCatalog"))
//                .fetch();
//    }

    //Список всех продуктов по бренду
//    public List<Product> findAllProductOfBrand(Session session, ProductFilter filter) {
//
//        var predicate = QPredicate.builder()
//                .add(filter.getBrand(), product.brand::eq)
//                .buildAnd();
//
//        return new JPAQuery<Product>(session)
//                .select(product)
//                .from(product)
//                .where(predicate)
//                .fetch();
//    }

//    public List<Product> findAllProductsFromOrder(Session session, UserFilter userFilter, OrderFilter orderFilter) {
//
//        var predicate = QPredicate.builder()
//                .add(userFilter.getPersonalInformation().getEmail(), user.personalInformation.email::eq)
//                .add(orderFilter.getId(), order.id::eq)
//                .buildAnd();
//
//        return new JPAQuery<Product>(session)
//                .select(product)
//                .from(product)
//                .join(product.shoppingCarts, shoppingCart)
//                .join(shoppingCart.order, order)
//                .join(order.user, user)
//                .setHint(GraphSemantic.LOAD.getJpaHintName(), session.getEntityGraph("withCatalog"))
//                .where(predicate)
//                .fetch();
//    }

//    public List<ShoppingCart> findAllOrdersWithProductsOfOneUser(Session session, UserFilter userFilter) {
//
//        var predicate = QPredicate.builder()
//                .add(userFilter.getPersonalInformation().getEmail(), user.personalInformation.email::eq)
//                .buildAnd();
//
//        return new JPAQuery<ShoppingCart>(session)
//                .select(shoppingCart)
//                .from(shoppingCart)
//                .join(shoppingCart.order, order)
//                .join(order.user, user)
//                .join(shoppingCart.product, product)
//                .join(product.catalog)
//                .setHint(GraphSemantic.LOAD.getJpaHintName(), session.getEntityGraph("findAllOrdersOfUsers"))
//                .where(predicate)
//                .fetch();
//    }

//    public List<ShoppingCart> findUserWhoMadeOrderInTime(Session session, LocalDate startDate, LocalDate endDate) {
//
////        var predicate = QPredicate.builder()
////                .add(shoppingCartFilter.getCreatedAt(), shoppingCart.createdAt::eq)
////                .buildAnd();
//
//        return new JPAQuery<ShoppingCart>(session)
//                .select(shoppingCart)
//                .from(shoppingCart)
//                .join(shoppingCart.order, order)
//                .join(order.user, user)
//                .where(shoppingCart.createdAt.between(startDate, endDate))
//                .setHint(GraphSemantic.LOAD.getJpaHintName(), session.getEntityGraph("findAllOrdersOfUsers"))
//                .fetch();
//    }

//    public Product findMinPriceOfProduct(Session session) {
//        var result = session.createQuery("select p from Product p order by p.price asc", Product.class)
//                .setMaxResults(1)
//                .uniqueResult();
//        return result;
//    }
//
//    public List<Tuple> getStatisticOfEachOrdersWithSum(Session session) {
//
//        return new JPAQuery<Tuple>(session)
//                .select(shoppingCart)
//                .from(shoppingCart)
//                .join(shoppingCart.product, product)
//                .join(shoppingCart.order, order)
//                .groupBy(order.id)
//                .select(order.id, product.price.sum())
//                .fetch();
//    }
//
//    public List<ShoppingCart> findUsersWhoMadeAnOrderOfSpecificProduct(Session session, ProductFilter productFilter) {
//
//        var predicate = QPredicate.builder()
//                .add(productFilter.getBrand(), product.brand::eq)
//                .add(productFilter.getCatalog().getCategory(), catalog.category::eq)
//                .add(productFilter.getModel(), product.model::eq)
//                .buildAnd();
//
//        return new JPAQuery<ShoppingCart>(session)
//                .select(shoppingCart)
//                .from(shoppingCart)
//                .join(shoppingCart.order, order)
//                .join(order.user, user)
//                .join(shoppingCart.product, product)
//                .join(product.catalog, catalog)
//                .where(predicate)
//                .setHint(GraphSemantic.LOAD.getJpaHintName(), session.getEntityGraph("findAllOrdersOfUsers"))
//                .fetch();
//    }
//
//    public List<User> findUsersWhoPayOrderWithCash(Session session, UserFilter userFilter) {
//        return null;
//    }
//
//
//    public static Query getInstance() {
//        return INSTANCE;
//    }


}