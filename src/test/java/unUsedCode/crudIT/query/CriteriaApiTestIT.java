//package query;
//
//import entity.Product;
//import entity.enums.Brand;
//import lombok.Cleanup;
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import org.junit.jupiter.api.AfterAll;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.TestInstance;
//import util.HibernateUtil;
//
//import java.time.LocalDate;
//
//import static java.util.stream.Collectors.toList;
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;
//
//@TestInstance(PER_CLASS)
//public class CriteriaApiTestIT {
//
//    private final SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
//    private final Criteria criteria = Criteria.getInstance();
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
//    @Test
//    void findById() {
//        @Cleanup Session session = sessionFactory.openSession();
//        session.beginTransaction();
//
//        var expected = session.get(Product.class, 1);
//
//        var actualResult = criteria.findById(session, 1);
//        assertThat(actualResult).isEqualTo(expected);
//
//        session.getTransaction().commit();
//    }
//
//    @Test
//    void findProductsGtPriceAndBrandAndCategory() {
//        @Cleanup Session session = sessionFactory.openSession();
//        session.beginTransaction();
//
//        var results = criteria.findProductsGtPriceAndBrandCategory(session, Brand.SAMSUNG, "Smartphone", 899);
//        assertThat(results).hasSize(3);
//
//        var categoryResult = results.stream().map(it -> it.getCatalog().getCategory()).collect(toList());
//        assertThat(categoryResult).contains("Smartphone");
//
//        var brandResult = results.stream().map(Product::getBrand).collect(toList());
//        assertThat(brandResult).contains(Brand.SAMSUNG);
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
//        var results = criteria.findAllProductOfBrand(session, Brand.SAMSUNG);
//        assertThat(results).hasSize(4);
//
//        var brands = results.stream().map(Product::getBrand).toList();
//        assertThat(brands).contains(Brand.SAMSUNG);
//
//        session.getTransaction().commit();
//    }
//
//    @Test
//    void findAllProductsFromOneOrder() {
//        @Cleanup Session session = sessionFactory.openSession();
//        session.beginTransaction();
//
//        var results = criteria.findAllProductsFromOrder(session, 3, "petr@gmail.com");
//        assertThat(results).hasSize(3);
//
//        var categoryResult = results.stream().map(it -> it.getProduct().getCatalog().getCategory()).collect(toList());
//        assertThat(categoryResult).contains("TV", "Smartphone", "Headphones");
//
//        var brandResult = results.stream().map(it -> it.getProduct().getBrand().name()).collect(toList());
//        assertThat(brandResult).contains("SAMSUNG", "SAMSUNG", "SONY");
//
//        var modelResult = results.stream().map(it -> it.getProduct().getModel()).collect(toList());
//        assertThat(modelResult).contains("A80J", "S22", "XM3");
//
//        session.getTransaction().commit();
//    }
//
//        @Test
//    void findAllOrdersWithProductsOfOneUser() {
//        @Cleanup Session session = sessionFactory.openSession();
//        session.beginTransaction();
//
//
//        var results = criteria.findAllOrdersWithProductsOfOneUser(session, "petr@gmail.com");
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
//        var results = criteria.findUserWhoMadeOrderInTime(session, LocalDate.of(2022, 10, 10), LocalDate.of(2023, 12, 12));
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
//        @Test
//        void getStatisticOfAllOrdersTest () {
//            @Cleanup Session session = sessionFactory.openSession();
//            session.beginTransaction();
//
//            var result = criteria.getStatisticOfEachOrdersWithSum(session);
//            assertThat(result).hasSize(8);
//
//            var orderId = result.stream().map(it -> it.get(0, Integer.class)).collect(toList());
//            assertThat(orderId).contains(1, 2, 3, 4, 5, 6, 7, 8);
//
//            var orderSum = result.stream().map(it -> it.get(1, Long.class)).collect(toList());
//            assertThat(orderSum).contains(1000L, 1100L, 3400L, 2000L, 1450L, 1450L, 4000L, 3350L);
//
//            session.getTransaction().commit();
//
//        }

//    @Test
//    void findAllOrdersWithProductsOfOneUser() {
//        @Cleanup Session session = sessionFactory.openSession();
//        session.beginTransaction();
//
////        UserFilter userFilter = UserFilter.builder()
////                .personalInformation(PersonalInformation.builder()
////                        .email("petr@gmail.com")
////                        .build())
////                .build();
//
//        var results = criteria.findAllOrdersWithProductsOfOneUser(session);
//        assertThat(results).hasSize(3);
//
//        var orderId = results.stream().map(it -> it.getOrder().getId()).collect(toList());
//        assertThat(orderId).contains(3, 3, 3);
//
//        var categoryResult = results.stream().map(it -> it.getProduct().getCatalog().getCategory()).collect(toList());
//        assertThat(categoryResult).contains("TV", "Smartphone", "Headphones");
//
//
//        session.getTransaction().commit();
//    }
//    }
