package util;

import entity.Catalog;
import entity.Order;
import entity.Product;
import entity.ShoppingCart;
import entity.User;
import entity.embeddable.DeliveryAdress;
import entity.embeddable.PersonalInformation;
import entity.enums.Brand;
import entity.enums.Color;
import entity.enums.PaymentCondition;
import entity.enums.Role;
import lombok.Cleanup;
import lombok.experimental.UtilityClass;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.time.LocalDate;
import java.util.Arrays;

@UtilityClass
public class TestDataImporter {

    public void importData(SessionFactory sessionFactory) {
        @Cleanup Session session = sessionFactory.openSession();

        var smartphone = saveCatalog(session, "Smartphone");
        var tv = saveCatalog(session, "TV");
        var headphone = saveCatalog(session, "Headphones");

        var apple13 = saveProduct(session, smartphone, Brand.APPLE, "13", LocalDate.of(2021, 9, 12), 1000, Color.BLACK, "image");
        var apple14 = saveProduct(session, smartphone, Brand.APPLE, "14", LocalDate.of(2022, 9, 12), 1100, Color.WHITE, "image");
        var samsungS21 = saveProduct(session, smartphone, Brand.SAMSUNG, "S21", LocalDate.of(2022, 1, 10), 1050, Color.WHITE, "image");
        var samsungS22 = saveProduct(session, smartphone, Brand.SAMSUNG, "S22", LocalDate.of(2022, 1, 12), 1100, Color.BLACK, "image");
        var samsungS20 = saveProduct(session, smartphone, Brand.SAMSUNG, "S22", LocalDate.of(2022, 1, 12), 900, Color.BLACK, "image");
        var samsungA80J = saveProduct(session, tv, Brand.SAMSUNG, "A80J", LocalDate.of(2022, 1, 12), 2000, Color.BLACK, "image");
        var sonyHeadphoneXm3 = saveProduct(session, headphone, Brand.SONY, "XM3", LocalDate.of(2019, 1, 10), 300, Color.BLACK, "image");
        var sonyHeadphoneXm4 = saveProduct(session, headphone, Brand.SONY, "XM4", LocalDate.of(2020, 1, 10), 350, Color.BLACK, "image");
        var sonyHeadphoneXm5 = saveProduct(session, headphone, Brand.SONY, "XM5", LocalDate.of(2021, 1, 10), 400, Color.BLACK, "image");
        var appleHeadphone = saveProduct(session, headphone, Brand.APPLE, "AirPodsMax", LocalDate.of(2021, 2, 15), 400, Color.WHITE, "image");
        var samsungHeadphone = saveProduct(session, headphone, Brand.SAMSUNG, "GalaxyBuds", LocalDate.of(2020, 10, 10), 150, Color.BLACK, "image");

        var ivan = saveUser(session, "Ivan", "Ivanov", "ivan@gmail.com", "ivan", "123-45-67", LocalDate.of(1990, 10, 10), Role.USER);
        var sveta = saveUser(session, "Sveta", "Svetikova", "sveta@gmail.com", "sveta", "123-67-47", LocalDate.of(1985, 2, 2), Role.USER);
        var petr = saveUser(session, "Petr", "Petrov", "petr@gmail.com", "petr", "321-45-67", LocalDate.of(1980, 3, 3), Role.USER);
        var sasha = saveUser(session, "Sasha", "Sasheva", "sasha@gmail.com", "sasha", "123-45-78", LocalDate.of(1991, 10, 5), Role.USER);
        var dima = saveUser(session, "Dima", "Dimov", "dima@gmail.com", "dima", "321-32-32", LocalDate.of(1988, 3, 18), Role.USER);
        var ksenia = saveUser(session, "Ksenia", "Kseneva", "ksenia@gmail.com", "ksenia", "123-32-32", LocalDate.of(1992, 7, 18), Role.USER);
        var vasia = saveUser(session, "Vasia", "Vasev", "vasia@gmail.com", "vasia", "327-32-14", LocalDate.of(1991, 5, 22), Role.USER);
        var vlad = saveUser(session, "Vlad", "Vladov", "vlad@gmail.com", "vlad", "981-01-18", LocalDate.of(1995, 6, 19), Role.USER);
        var admin = saveUser(session, "Admin", "Admin", "admin@gmail.com", "admin", "111-11-11", LocalDate.of(1992, 1, 1), Role.ADMIN);

        var orderIvan = saveOrder(session, "Minsk", "Masherova", 17, LocalDate.of(2022, 12, 10), PaymentCondition.CARD, ivan);
        var orderSveta = saveOrder(session, "Minsk", "Pobedi", 10, LocalDate.of(2022, 11, 5), PaymentCondition.CASH, sveta);
        var orderPetr = saveOrder(session, "Smorgon", "Minskaya", 11, LocalDate.of(2022, 10, 4), PaymentCondition.CARD, petr);
        var orderSasha = saveOrder(session, "Minsk>", "Pobedi", 15, LocalDate.of(2019, 8, 4), PaymentCondition.CARD, sasha);
        var orderDima = saveOrder(session, "Minsk>", "Geelyna", 7, LocalDate.of(2022, 7, 4), PaymentCondition.CASH, dima);
        var orderKsenia = saveOrder(session, "Minsk>", "Vashina", 18, LocalDate.of(2019, 10, 4), PaymentCondition.CASH, ksenia);
        var orderVasia = saveOrder(session, "Minsk>", "Porovnu", 55, LocalDate.of(2022, 11, 5), PaymentCondition.CASH, vasia);
        var orderVlad = saveOrder(session, "Minsk>", "Vasheva", 10, LocalDate.of(1990, 12, 4), PaymentCondition.CASH, vlad);

        addToShoppingCart(session, LocalDate.of(2022, 12, 10), orderIvan, apple13);
        addToShoppingCart(session, LocalDate.of(2022, 11, 5), orderSveta, apple14);
        addToShoppingCart(session, LocalDate.of(2022, 10, 4), orderPetr, samsungA80J);
        addToShoppingCart(session, LocalDate.of(2022, 10, 4), orderPetr, samsungS22);
        addToShoppingCart(session, LocalDate.of(2022, 10, 4), orderPetr, sonyHeadphoneXm3);
        addToShoppingCart(session, LocalDate.of(2019, 8, 4), orderSasha, samsungA80J);
        addToShoppingCart(session, LocalDate.of(2022, 7, 4), orderDima, sonyHeadphoneXm4);
        addToShoppingCart(session, LocalDate.of(2022, 7, 4), orderDima, samsungS22);
        addToShoppingCart(session, LocalDate.of(2019, 10, 4), orderKsenia, apple14);
        addToShoppingCart(session, LocalDate.of(2019, 10, 4), orderKsenia, sonyHeadphoneXm4);
        addToShoppingCart(session,  LocalDate.of(2022, 11, 5), orderVasia, samsungA80J);
        addToShoppingCart(session,  LocalDate.of(2022, 11, 5), orderVasia, samsungA80J);
        addToShoppingCart(session, LocalDate.of(2021, 12, 4), orderVlad, sonyHeadphoneXm5);
        addToShoppingCart(session, LocalDate.of(2021, 12, 4), orderVlad, apple13);
        addToShoppingCart(session, LocalDate.of(2021, 12, 4), orderVlad, samsungS21);
        addToShoppingCart(session, LocalDate.of(2021, 12, 4), orderVlad, samsungS20);
    }
    private void addToShoppingCart(Session session, LocalDate instant, Order order, Product... products){
        Arrays.stream(products)
                .map(product -> ShoppingCart.builder()
                        .order(order)
                        .product(product)
                        .createdAt(instant)
                        .build())
                .forEach(session::save);
    }


    private Order saveOrder(Session session,
                            String city,
                            String street,
                            Integer building,
                            LocalDate deliveryDate,
                            PaymentCondition paymentCondition,
                            User user
    ) {

        var order = Order.builder()
                .deliveryAdress(DeliveryAdress.builder()
                        .city(city)
                        .street(street)
                        .building(building)
                        .build())
                .deliveryDate(deliveryDate)
                .paymentCondition(paymentCondition)
                .user(user)
                .build();

        session.save(order);

        return order;
    }

    private User saveUser(Session session,
                          String firstName,
                          String lastName,
                          String email,
                          String password,
                          String telephone,
                          LocalDate birthDate,
                          Role role) {

        var user = User.builder()
                .personalInformation(PersonalInformation.builder()
                        .firstName(firstName)
                        .lastName(lastName)
                        .email(email)
                        .password(password)
                        .telephone(telephone)
                        .birthDate(birthDate)
                        .build())
                .role(role)
                .build();

        session.save(user);

        return user;
    }

    private Product saveProduct(Session session,
                                Catalog catalog,
                                Brand brand,
                                String model,
                                LocalDate dateOfRelease,
                                Integer price,
                                Color color,
                                String image) {
        var product = Product.builder()
                .catalog(catalog)
                .brand(brand)
                .model(model)
                .dateOfRelease(dateOfRelease)
                .price(price)
                .color(color)
                .image(image)
                .build();

        session.save(product);

        return product;
    }



    private Catalog saveCatalog(Session session, String categoryName) {
        Catalog catalog = Catalog.builder()
                .id(1)
                .category(categoryName)
                .build();
        session.save(catalog);

        return catalog;
    }
}
