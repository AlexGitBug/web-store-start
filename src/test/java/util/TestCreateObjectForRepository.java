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
import lombok.experimental.UtilityClass;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@UtilityClass
public class TestCreateObjectForRepository {

    public static Product getProduct(Catalog catalog) {
        return Product.builder()
                .catalog(catalog)
                .brand(Brand.SONY)
                .model("test")
                .dateOfRelease(LocalDate.of(2021, 1, 1))
                .price(100)
                .color(Color.BLACK)
                .image("test")
                .build();
    }

    public static Catalog getCatalog() {
        return Catalog.builder()
                .category("Smartphone")
                .build();
    }

    public static Order getOrder(User user) {
        return Order.builder()
                .deliveryAdress(DeliveryAdress.builder()
                        .city("Minsk")
                        .street("Pobedi")
                        .building(10)
                        .build())
                .deliveryDate(LocalDate.of(2022, 11, 5))
                .paymentCondition(PaymentCondition.CASH)
                .user(user)
                .build();
    }

    public static User getUser() {
        return User.builder()
                .personalInformation(PersonalInformation.builder()
                        .firstName("Sasha")
                        .lastName("Sasheva")
                        .telephone("123-45-78")
                        .email("sasha@gmail.com")
                        .birthDate(LocalDate.of(1991, 10, 5))
                        .build())
                .role(Role.USER)
                .build();
    }

    public static ShoppingCart shoppingCart(Order order,Product product) {
        return ShoppingCart.builder()
                .createdAt(LocalDate.of(2019, 8, 4))
                .product(product)
                .order(order)
                .build();
    }

}

