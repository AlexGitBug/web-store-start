package com.dmdev.webStore.query;


//public class Criteria {
//
//    private static final Criteria INSTANCE = new Criteria();
//
//    //пробный тест на поиск определенного товара
//
//
//    public Product findById(Session session, Integer productId) {

//        session.getEntityGraph("withCatalog");
//
//        Map<String, Object> properties = Map.of(
//                GraphSemantic.LOAD.getJpaHintName(), session.getEntityGraph("withCatalog"));
//
//        return session.find(Product.class, productId, properties);
//
//        var cb = session.getCriteriaBuilder();
//
//        var criteria = cb.createQuery(Product.class);
//        var product = criteria.from(Product.class);
//
//        criteria.select(product)
//                .where(cb.equal(product.get(Product_.id), productId));
//
//        return session.createQuery(criteria)
//                .uniqueResult();
//    }
//
//    public List<Product> findOneProductEq(Session session, ProductFilter filter) {
//
//        var predicate = QPredicate.builder()
//                .add(filter.getCatalog().getCategory(), catalog.category::eq)
//                .add(filter.getBrand(), product.brand::eq)
//                .add(filter.getDateOfRelease(), product.dateOfRelease::eq)
//                .add(filter.getPrice(), product.price::eq)
//                .add(filter.getColor(), product.color::eq)
//                .buildAnd();
//
//        return new JPAQuery<Product>(session)
//                .select(product)
//                .from(product)
//                .join(product.catalog, catalog)
//                .where(predicate)
//                .setHint(GraphSemantic.LOAD.getJpaHintName(), session.getEntityGraph("withCatalog"))
//                .fetch();
//        return null;
//    }

    //Список всех товаров выше 1000 и определенного бренда
//    public List<Product> findProductsGtPriceAndBrandCategory(Session session, Brand brandName, String category, Integer priceLimit) {

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

//        var cb = session.getCriteriaBuilder();
//        var criteria = cb.createQuery(Product.class);
//        var product = criteria.from(Product.class);
//        var catalog = product.join(Product_.catalog);
//
//        criteria.select(product).where(
//                        cb.equal(product.get(Product_.brand), brandName),
//                        cb.equal(catalog.get(Catalog_.category), category)
//                )
//                .having(cb.gt(product.get(Product_.price), priceLimit))
//                .orderBy(cb.asc(product.get(Product_.price)));
//
//        return session.createQuery(criteria)
//                .list();

//    }

    //Список всех продуктов по бренду
//    public List<Product> findAllProductOfBrand(Session session, Brand brandName) {

//        var predicate = QPredicate.builder()
//                .add(filter.getBrand(), product.brand::eq)
//                .buildAnd();
//
//        return new JPAQuery<Product>(session)
//                .select(product)
//                .from(product)
//                .where(predicate)
//                .fetch();
//        var cb = session.getCriteriaBuilder();
//        var criteria = cb.createQuery(Product.class);
//        var product = criteria.from(Product.class);
//
//        criteria.select(product).where(
//                cb.equal(product.get(Product_.brand), brandName));
//
//        return session.createQuery(criteria)
//                .list();


//    }

//    public List<ShoppingCart> findAllProductsFromOrder(Session session, Integer orderId, String email) {
//        var predicate = QPredicate.builder()
//                .add(userFilter.getPersonalInformation().getEmail(), user.personalInformation.email::eq)
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

//        var cb = session.getCriteriaBuilder();
//        var criteria = cb.createQuery(ShoppingCart.class);
//        var shoppingCart = criteria.from(ShoppingCart.class);
//        var order = shoppingCart.join(ShoppingCart_.order);
//        var user = order.join(Order_.user);
//
//        criteria.select(shoppingCart).where(
//                cb.equal(order.get(Order_.id), orderId),
//                cb.equal(user.get(User_.personalInformation).get(PersonalInformation_.email), email));
//
//        return session.createQuery(criteria)
//                .list();
//    }



//    public List<ShoppingCart> findAllOrdersWithProductsOfOneUser(Session session, String email) {
//
////        var predicate = QPredicate.builder()
////                .add(userFilter.getPersonalInformation().getEmail(), user.personalInformation.email::eq)
////                .buildAnd();
////
////        return new JPAQuery<ShoppingCart>(session)
////                .select(shoppingCart)
////                .from(shoppingCart)
////                .join(shoppingCart.order, order)
////                .join(order.user, user)
////                .join(shoppingCart.product, product)
////                .join(product.catalog)
////                .setHint(GraphSemantic.LOAD.getJpaHintName(), session.getEntityGraph("findAllOrdersOfUsers"))
////                .where(predicate)
////                .fetch();
//
//        var cb = session.getCriteriaBuilder();
//        var criteria = cb.createQuery(ShoppingCart.class);
//        var shoppingCart = criteria.from(ShoppingCart.class);
//        var product = shoppingCart.join(ShoppingCart_.product);
//        var order = shoppingCart.join(ShoppingCart_.order);
//        var user = order.join(Order_.user);;
//
//
//        criteria.select(shoppingCart).where(
//                cb.equal(user.get(User_.personalInformation).get(PersonalInformation_.email), email));
//
//
//        return session.createQuery(criteria)
//                .setHint(GraphSemantic.LOAD.getJpaHintName(), session.getEntityGraph("findAllOrdersOfUsers"))
//                .list();
//
//    }
//
//    public List<ShoppingCart> findUserWhoMadeOrderInTime(Session session, LocalDate startDate, LocalDate endDate) {
//
////        var predicate = QPredicate.builder()
////                .add(shoppingCartFilter.getCreatedAt(), shoppingCart.createdAt::eq)
////                .buildAnd();
//
////        return new JPAQuery<ShoppingCart>(session)
////                .select(shoppingCart)
////                .from(shoppingCart)
////                .join(shoppingCart.order, order)
////                .join(order.user, user)
////                .where(shoppingCart.createdAt.between(startDate, endDate))
////                .setHint(GraphSemantic.LOAD.getJpaHintName(), session.getEntityGraph("findAllOrdersOfUsers"))
////                .fetch();
//
//        var cb = session.getCriteriaBuilder();
//        var criteria = cb.createQuery(ShoppingCart.class);
//        var shoppingCart = criteria.from(ShoppingCart.class);
//        var order = shoppingCart.join(ShoppingCart_.order);
//        var user = order.join(Order_.user);
//
//        criteria.select(shoppingCart)
//                .where(cb.equal(shoppingCart.get(ShoppingCart_.createdAt),
//                        cb.between(shoppingCart.get(ShoppingCart_.createdAt), startDate, endDate)));
//
//        return session.createQuery(criteria)
//                .list();
//
//    }
//
//    public Product findMinPriceOfProduct(Session session) {
//        var result = session.createQuery("select p from Product p order by p.price asc", Product.class)
//                .setMaxResults(1)
//                .uniqueResult();
//        return result;
//    }
//
//    public List<Tuple> getStatisticOfEachOrdersWithSum(Session session) {
//
////        return new JPAQuery<Tuple>(session)
////                .select(shoppingCart)
////                .from(shoppingCart)
////                .join(shoppingCart.product, product)
////                .join(shoppingCart.order, order)
////                .groupBy(order.id)
////                .select(order.id, product.price.sum())
////                .fetch();
//
//        var cb = session.getCriteriaBuilder();
//
//        var criteria = cb.createQuery(Tuple.class);
//        var shoppingCart = criteria.from(ShoppingCart.class);
//        var product = shoppingCart.join(ShoppingCart_.product);
//        var order = shoppingCart.join(ShoppingCart_.order);
//
//        criteria.select(
//                        cb.tuple(
//                                order.get(Order_.id),
//                                cb.sum(product.get(Product_.price)))
//                )
//                .groupBy(order.get(Order_.id));
//
//
//        return session.createQuery(criteria)
//                .list();
//
//    }
//
//
//    public List<ShoppingCart> findUsersWhoMadeAnOrderOfSpecificProduct(Session session, ProductFilter productFilter) {
////
////        var predicate = QPredicate.builder()
////                .add(productFilter.getBrand(), product.brand::eq)
////                .add(productFilter.getModel(), product.model::eq)
////                .buildAnd();
////
////        return new JPAQuery<ShoppingCart>(session)
////                .select(shoppingCart)
////                .from(shoppingCart)
////                .join(shoppingCart.order, order)
////                .join(order.user, user)
////                .join(shoppingCart.product, product)
////                .join(product.catalog, catalog)
////                .where(predicate)
////                .setHint(GraphSemantic.LOAD.getJpaHintName(), session.getEntityGraph("findAllOrdersOfUsers"))
////                .fetch();
//        return null;
//    }
//
//    public List<User> findUsersWhoPayOrderWithCash(Session session, UserFilter userFilter) {
//        return null;
//    }
//
//
//    public static Criteria getInstance() {
//        return INSTANCE;
//    }
//
//
//}