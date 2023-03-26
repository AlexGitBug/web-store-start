//package query;
//
//import entity.Catalog;
//import entity.Product;
//import entity.embeddable.PersonalInformation;
//import entity.enums.Brand;
//import lombok.Cleanup;
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import org.junit.jupiter.api.AfterAll;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.TestInstance;
//import query.filter.OrderFilter;
//import query.filter.ProductFilter;
//import query.filter.UserFilter;
//import util.HibernateUtil;
//
//import java.time.LocalDate;
//import java.util.List;
//
//import static java.util.stream.Collectors.toList;
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;
//
//@TestInstance(PER_CLASS)
//class QueryTestIT {
//
//    private final SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
//    private final Query query = Query.getInstance();
////    private final Criteria criteria = Criteria.getInstance();
//
//    @BeforeAll
//    public void initDb() {
//        DataImporter.importData(sessionFactory);
//    }
//
//    @AfterAll
//    public void finish() {
//        sessionFactory.close();
//    }
//
////    @Test
////    void findById() {
////        @Cleanup Session session = sessionFactory.openSession();
////        session.beginTransaction();
////
////        var expected = session.get(Product.class, 1);
////
////        var actualResult = query.findById(session, 1);
////        assertThat(actualResult).isEqualTo(expected);
////
////        session.getTransaction().commit();
////    }
//
//    @Test
//    void findMinPriceOfProduct() {
//        @Cleanup Session session = sessionFactory.openSession();
//        session.beginTransaction();
//
//        var result = query.findMinPriceOfProduct(session);
//        assertThat(result.getPrice()).isEqualTo(300);
//
//        session.getTransaction().commit();
//    }
//
//    @Test
//    void findListOfProductBetweenTwoPrice() {
//        @Cleanup Session session = sessionFactory.openSession();
//        session.beginTransaction();
//
//        var productFilter = ProductFilter.builder()
//                .catalog(Catalog.builder()
//                        .category("Headphones")
//                        .build())
//                .brand(Brand.SONY)
//                .build();
//
//        var results = query.findListOfProductBetweenTwoPrice(session, productFilter, 349, 401);
//        assertThat(results).hasSize(2);
//
//        var brands = results.stream().map(Product::getBrand).collect(toList());
//        assertThat(brands).contains(productFilter.getBrand());
//
//    }
//
//    @Test
//    void findListOfProductsEq() {
//        @Cleanup Session session = sessionFactory.openSession();
//        session.beginTransaction();
//
//        var productFilter = ProductFilter.builder()
//                .catalog(Catalog.builder()
//                        .category("Smartphone")
//                        .build())
//                .brand(Brand.APPLE)
//                .build();
//
//        var results = query.findListOfProductsEq(session, productFilter);
//        assertThat(results).hasSize(2);
//
//        var categoryResult = results.stream().map(it -> it.getCatalog().getCategory()).collect(toList());
//        assertThat(categoryResult).contains(productFilter.getCatalog().getCategory());
//
//        var brandResult = results.stream().map(Product::getBrand).collect(toList());
//        assertThat(brandResult).contains(productFilter.getBrand());
//
//
//        session.getTransaction().commit();
//
//    }
//
//    @Test
//    void findListOfProductGtPrice() {
//        @Cleanup Session session = sessionFactory.openSession();
//        session.beginTransaction();
//
//        var productFilter = ProductFilter.builder()
//                .catalog(Catalog.builder()
//                        .category("Smartphone")
//                        .build())
//                .brand(Brand.APPLE)
//                .build();
//        var results = query.findListOfProductGtPrice(session, productFilter, 1050);
//        assertThat(results).hasSize(1);
//
//        var categoryResult = results.stream().map(it -> it.getCatalog().getCategory()).collect(toList());
//        assertThat(categoryResult).contains(productFilter.getCatalog().getCategory());
//
//    }
//
//    @Test
//    void findListOfProductLtPrice() {
//        @Cleanup Session session = sessionFactory.openSession();
//        session.beginTransaction();
//
//        var productFilter = ProductFilter.builder()
//                .catalog(Catalog.builder()
//                        .category("Smartphone")
//                        .build())
//                .brand(Brand.APPLE)
//                .build();
//        var results = query.findListOfProductLtPrice(session, productFilter, 1050);
//        assertThat(results).hasSize(1);
//
//        var categoryResult = results.stream().map(it -> it.getCatalog().getCategory()).collect(toList());
//        assertThat(categoryResult).contains(productFilter.getCatalog().getCategory());
//
//    }
//
//    @Test
//    void findProductsGtPriceAndBrandAndCategory() {
//        @Cleanup Session session = sessionFactory.openSession();
//        session.beginTransaction();
//
//        var productFilter = ProductFilter.builder()
//                .catalog(Catalog.builder()
//                        .category("Smartphone")
//                        .build())
//                .price(899)
//                .brand(Brand.SAMSUNG)
//                .build();
//
//        var results = query.findProductsGtPriceAndBrand(session, productFilter);
//        assertThat(results).hasSize(3);
//
//        var categoryResult = results.stream().map(it -> it.getCatalog().getCategory()).collect(toList());
//        assertThat(categoryResult).contains(productFilter.getCatalog().getCategory());
//
//        var brandResult = results.stream().map(Product::getBrand).collect(toList());
//        assertThat(brandResult).contains(productFilter.getBrand());
//
//        var priceResult = results.stream().map(Product::getPrice).collect(toList());
//        assertThat(priceResult).contains(900, 1050, 1100);
//
//        session.getTransaction().commit();
//    }
//
//    @Test
//    void findAllProductOfBrand() {
//        @Cleanup Session session = sessionFactory.openSession();
//        session.beginTransaction();
//
//        var productFilter = ProductFilter.builder()
//                .brand(Brand.SAMSUNG)
//                .build();
//
//        var results = query.findAllProductOfBrand(session, productFilter);
//        assertThat(results).hasSize(4);
//
//        var brands = results.stream().map(Product::getBrand).toList();
//        assertThat(brands).contains(productFilter.getBrand());
//
//        session.getTransaction().commit();
//    }
//
//    @Test
//    void findAllProductsFromOneOrder() {
//        @Cleanup Session session = sessionFactory.openSession();
//        session.beginTransaction();
//
//        var userFilter = UserFilter.builder()
//                .personalInformation(PersonalInformation.builder()
//                        .email("petr@gmail.com")
//                        .build())
//                .build();
//
//        var orderFilter = OrderFilter.builder()
//                .id(3)
//                .build();
//
//        List<Product> results = query.findAllProductsFromOrder(session, userFilter, orderFilter);
//        assertThat(results).hasSize(3);
//
//        var categoryResult = results.stream().map(it->it.getCatalog().getCategory()).collect(toList());
//        assertThat(categoryResult).contains("TV", "Smartphone", "Headphones");
//
//        var brandResult = results.stream().map(it->it.getBrand().name()).collect(toList());
//        assertThat(brandResult).contains("SAMSUNG", "SAMSUNG", "SONY");
//
//        var modelResult = results.stream().map(Product::getModel).collect(toList());
//        assertThat(modelResult).contains("A80J", "S22", "XM3");
//
//        session.getTransaction().commit();
//
//    }
//
//    @Test
//    void findUsersWhoMadeAnOrderOfSpecificProduct() {
//        @Cleanup Session session = sessionFactory.openSession();
//        session.beginTransaction();
//
//        var productFilter = ProductFilter.builder()
//                .brand(Brand.SONY)
//                .catalog(Catalog.builder()
//                        .category("Headphones")
//                        .build())
//                .model("XM4")
//                .build();
//
//        var results = query.findUsersWhoMadeAnOrderOfSpecificProduct(session, productFilter);
//        assertThat(results).hasSize(2);
//
//        var firstName = results.stream().map(it -> it.getOrder().getUser().getPersonalInformation().getFirstName()).collect(toList());
//        assertThat(firstName).contains("Dima", "Ksenia");
//
//        var telephoneResult = results.stream().map(it -> it.getOrder().getUser().getPersonalInformation().getTelephone()).collect(toList());
//        assertThat(telephoneResult).contains("321-32-32", "123-32-32");
//
//        var emailResult = results.stream().map(it -> it.getOrder().getUser().getPersonalInformation().getEmail()).collect(toList());
//        assertThat(emailResult).contains("dima@gmail.com", "ksenia@gmail.com");
//
//        session.getTransaction().commit();
//
//    }
//
//    @Test
//    void findAllOrdersWithProductsOfOneUser() {
//        @Cleanup Session session = sessionFactory.openSession();
//        session.beginTransaction();
//
//        UserFilter userFilter = UserFilter.builder()
//                .personalInformation(PersonalInformation.builder()
//                        .email("petr@gmail.com")
//                        .build())
//                .build();
//
//        var results = query.findAllOrdersWithProductsOfOneUser(session, userFilter);
//        assertThat(results).hasSize(3);
//
//        var orderId = results.stream().map(it -> it.getOrder().getId()).collect(toList());
//        assertThat(orderId).contains(3,3,3);
//
//        var categoryResult = results.stream().map(it->it.getProduct().getCatalog().getCategory()).collect(toList());
//        assertThat(categoryResult).contains("TV", "Smartphone", "Headphones");
//
//
//        session.getTransaction().commit();
//    }
//
//    @Test
//    void findUsersWhoMadeAnOrderAtASpecificTime() {
//        @Cleanup Session session = sessionFactory.openSession();
//        session.beginTransaction();
//
//        var results = query.findUserWhoMadeOrderInTime(session, LocalDate.of(2022, 10, 10), LocalDate.of(2023, 12, 12));
//        assertThat(results).hasSize(4);
//
//        var firstName = results.stream().map(it -> it.getOrder().getUser().getPersonalInformation().getFirstName()).collect(toList());
//        assertThat(firstName).contains("Ivan", "Sveta", "Vasia", "Vasia");
//
//        var email = results.stream().map(it -> it.getOrder().getUser().getPersonalInformation().getEmail()).collect(toList());
//        assertThat(email).contains("ivan@gmail.com", "sveta@gmail.com", "vasia@gmail.com", "vasia@gmail.com");
//
//        session.getTransaction().commit();
//    }
//
//    //
//    @Test
//    void getStatisticOfAllOrders() {
//        @Cleanup Session session = sessionFactory.openSession();
//        session.beginTransaction();
//
//        var result = query.getStatisticOfEachOrdersWithSum(session);
//        assertThat(result).hasSize(8);
//
//        var orderId = result.stream().map(it -> it.get(0, Integer.class)).collect(toList());
//        assertThat(orderId).contains(1, 2, 3, 4, 5, 6, 7, 8);
//
//        var orderSum = result.stream().map(it -> it.get(1, Integer.class)).collect(toList());
//        assertThat(orderSum).contains(1000, 1100, 3400, 2000, 1450, 1450, 4000, 3350);
//
//        session.getTransaction().commit();
//    }
//
//
//}