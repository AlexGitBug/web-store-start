package query;

import com.querydsl.core.Tuple;
import entity.Catalog;
import entity.Product;
import entity.ShoppingCart;
import entity.embeddable.DeliveryAdress;
import entity.embeddable.PersonalInformation;
import entity.enums.Brand;
import entity.enums.Color;
import lombok.Cleanup;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import query.filter.OrderFilter;
import query.filter.ProductFilter;
import query.filter.UserFilter;
import util.HibernateUtil;

import java.time.LocalDate;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@TestInstance(PER_CLASS)
class QueryTestIT {

    private final SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
    private final Query query = Query.getInstance();

    @BeforeAll
    public void initDb() {
        DataImporter.importData(sessionFactory);
    }

    @AfterAll
    public void finish() {
        sessionFactory.close();
    }

    @Test
    void findById() {
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();

        var expected = session.get(Product.class, 1);

        var actualResult = query.findById(session, 1);
        assertThat(actualResult).isEqualTo(expected);

        session.getTransaction().commit();
    }

    @Test
    void findMinPriceOfProduct() {
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();

        var result = query.findMinPriceOfProduct(session);
        assertThat(result.getPrice()).isEqualTo(300);

        session.getTransaction().commit();
    }


    @Test
    void findOneProductEq() {
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();

        var productFilter = ProductFilter.builder()
                .catalog(Catalog.builder()
                        .category("Smartphone")
                        .build())
                .price(1000)
                .model("13")
                .brand(Brand.APPLE)
                .dateOfRelease(LocalDate.of(2021, 9, 12))
                .color(Color.BLACK)
                .build();

        var results = query.findOneProductEq(session, productFilter);
        assertThat(results).hasSize(1);

        var brands = results.stream().map(Product::getFullFilterForOneProduct).collect(toList());
        assertThat(brands).contains("Smartphone APPLE 13 2021-09-12 1000 BLACK");

        session.getTransaction().commit();

    }

    @Test
    void findProductsGtPriceAndBrandAndCategory() {
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();

        var productFilter = ProductFilter.builder()
                .catalog(Catalog.builder()
                        .category("Smartphone")
                        .build())
                .price(899)
                .brand(Brand.SAMSUNG)
                .build();

        var results = query.findProductsGtPriceAndBrand(session, productFilter);
        assertThat(results).hasSize(3);

        var brands = results.stream().map(Product::getCategoryAndBrandAndPrice).collect(toList());
        assertThat(brands).contains("Smartphone SAMSUNG 900", "Smartphone SAMSUNG 1050", "Smartphone SAMSUNG 1100");

        session.getTransaction().commit();
    }

    @Test
    void findAllProductOfBrand() {
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();

        var productFilter = ProductFilter.builder()
                .brand(Brand.SAMSUNG)
                .build();

        var results = query.findAllProductOfBrand(session, productFilter);
        assertThat(results).hasSize(4);

        var brands = results.stream().map(Product::getBrand).toList();
        assertThat(brands).contains(productFilter.getBrand());

        session.getTransaction().commit();
    }

//    @Test
//    void findCatalogOfProductWithBrandFromShoppingCart() {
//        @Cleanup Session session = sessionFactory.openSession();
//        session.beginTransaction();
//
//        CatalogFilter catalogFilter = CatalogFilter.builder().category("headphone").build();
//        ProductFilter productFilter = ProductFilter.builder().brand(Brand.SONY).build();
//
//        List<Catalog> results = query.findCatalogOfProductWithBrandFromShoppingCart(session, catalogFilter, productFilter);
//        assertThat(results).hasSize(1);
//
//        List<String> actualResult = results.stream().map(Catalog::getCategory).collect(toList());
//        assertThat(actualResult).contains("Smartphone");
//
//
//        session.getTransaction().commit();
//    }

    @Test
    void findAllProductsFromOrder() {
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();

        var orderFilter = OrderFilter.builder()
                .deliveryAdress(DeliveryAdress.builder()
                        .city("Smorgon")
                        .street("Minskaya")
                        .building(11)
                        .build())
                .build();

        var userFilter = UserFilter.builder()
                .personalInformation(PersonalInformation.builder()
                        .telephone("321-45-67")
                        .build())
                .build();

        List<Product> results = query.findAllProductsFromOrder(session, orderFilter, userFilter);
        assertThat(results).hasSize(3);

        List<String> actualResult = results.stream().map(Product::getCategoryAndBrandAndModel).toList();
        assertThat(actualResult).containsExactlyInAnyOrder("TV SAMSUNG A80J", "Smartphone SAMSUNG S22", "Headphones SONY XM3");

        session.getTransaction().commit();

    }

    @Test
    void findAllOrdersWithProductsOfOneUser() {
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();

        UserFilter userFilter = UserFilter.builder()
                .personalInformation(PersonalInformation.builder()
                        .email("petr@gmail.com")
                        .build())
                .build();

        var results = query.findAllOrdersWithProductsOfOneUser(session, userFilter);
        assertThat(results).hasSize(3);

        var actualResult = results.stream().map(ShoppingCart::getIdAndCatalogOfProduct).collect(toList());
        assertThat(actualResult).containsExactlyInAnyOrder("3 TV A80J", "3 Smartphone S22", "3 Headphones XM3");

        session.getTransaction().commit();
    }

    @Test
    void findUsersWhoMadeAnOrderAtASpecificTime() {
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();

        var results = query.findUserWhoMadeOrderInTime(session, LocalDate.of(2022, 10, 10), LocalDate.of(2023, 12, 12));
        assertThat(results).hasSize(4);

        var actualResult = results.stream().map(ShoppingCart::getUserIdAndOrderIdAndLocalDateOfOrder).collect(toList());
        assertThat(actualResult).containsExactlyInAnyOrder("Ivanov ivan@gmail.com 1 2022-12-10",
                "Svetikova sveta@gmail.com 2 2022-11-05",
                "Vasev vasia@gmail.com 7 2022-11-05",
                "Vasev vasia@gmail.com 7 2022-11-05");

        session.getTransaction().commit();
    }

    @Test
    void getStatisticOfAllOrders() {
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();

        var result = query.getStatisticOfAllOrders(session);
        assertThat(result).hasSize(8);

        var actualResult = result.stream().map(Tuple::toString).collect(toList());
        assertThat(actualResult).contains("[1, 1000]","[2, 1100]","[3, 3400]", "[4, 2000]", "[5, 1450]", "[6, 1450]", "[7, 4000]", "[8, 3350]");

        session.getTransaction().commit();
    }


}