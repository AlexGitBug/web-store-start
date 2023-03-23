package dao.repository;

import com.dmdev.webStore.dao.repository.*;
import com.dmdev.webStore.dao.repository.filter.ProductFilter;
import com.dmdev.webStore.dao.repository.filter.UserFilter;
import com.dmdev.webStore.entity.Product;
import dao.repository.initProxy.ProxySessionTestBase;
import com.dmdev.webStore.entity.Catalog;
import com.dmdev.webStore.entity.ShoppingCart;
import com.dmdev.webStore.entity.embeddable.PersonalInformation;
import com.dmdev.webStore.entity.enums.Brand;

import org.junit.jupiter.api.Test;
import util.TestCreateObjectForRepository;
import util.TestDataImporter;

import java.time.LocalDate;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

public class ShoppingCartRepositoryIT extends ProxySessionTestBase {
    private final ShoppingCartRepository shoppingCartRepository = applicationContext.getBean(ShoppingCartRepository.class);
    private final ProductRepository productRepository = applicationContext.getBean(ProductRepository.class);
    private final OrderRepository orderRepository = applicationContext.getBean(OrderRepository.class);
    private final UserRepository userRepository = applicationContext.getBean(UserRepository.class);
    private final CatalogRepository catalogRepository = applicationContext.getBean(CatalogRepository.class);

    @Test
    void deleteShoppingCart() {
        var user = TestCreateObjectForRepository.getUser();
        userRepository.save(user);
        var order = TestCreateObjectForRepository.getOrder(user);
        orderRepository.save(order);
        var catalog = TestCreateObjectForRepository.getCatalog();
        catalogRepository.save(catalog);
        var product = TestCreateObjectForRepository.getProduct(catalog);
        productRepository.save(product);
        var shoppingCart = TestCreateObjectForRepository.shoppingCart(order, product);
        shoppingCartRepository.save(shoppingCart);

        shoppingCartRepository.delete(shoppingCart);

        var actualResult = entityManager.find(ShoppingCart.class, shoppingCart.getId());
        assertThat(actualResult).isNull();
    }

    @Test
    void saveShoppingCart() {
        var user = TestCreateObjectForRepository.getUser();
        userRepository.save(user);
        var order = TestCreateObjectForRepository.getOrder(user);
        orderRepository.save(order);
        var catalog = TestCreateObjectForRepository.getCatalog();
        catalogRepository.save(catalog);
        var product = TestCreateObjectForRepository.getProduct(catalog);
        productRepository.save(product);
        var shoppingCart = TestCreateObjectForRepository.shoppingCart(order, product);

        shoppingCartRepository.save(shoppingCart);

        var actualResult = entityManager.find(ShoppingCart.class, shoppingCart.getId());
        assertThat(actualResult).isNotNull();
    }

    @Test
    void updateShoppingCart() {
        var user = TestCreateObjectForRepository.getUser();
        userRepository.save(user);
        var order = TestCreateObjectForRepository.getOrder(user);
        orderRepository.save(order);
        var catalog = TestCreateObjectForRepository.getCatalog();
        catalogRepository.save(catalog);
        var product = TestCreateObjectForRepository.getProduct(catalog);
        productRepository.save(product);
        var shoppingCart = TestCreateObjectForRepository.shoppingCart(order, product);
        shoppingCartRepository.save(shoppingCart);

        var result = entityManager.find(ShoppingCart.class, shoppingCart.getId());
        result.setCreatedAt(LocalDate.now());
        shoppingCartRepository.update(shoppingCart);


        var actualResult = entityManager.find(ShoppingCart.class, shoppingCart.getId());
        assertThat(actualResult).isEqualTo(shoppingCart);
    }

    @Test
    void findByIdShoppingCart() {
        var user = TestCreateObjectForRepository.getUser();
        userRepository.save(user);
        var order = TestCreateObjectForRepository.getOrder(user);
        orderRepository.save(order);
        var catalog = TestCreateObjectForRepository.getCatalog();
        catalogRepository.save(catalog);
        var product = TestCreateObjectForRepository.getProduct(catalog);
        productRepository.save(product);
        var shoppingCart = TestCreateObjectForRepository.shoppingCart(order, product);
        shoppingCartRepository.save(shoppingCart);

        var actualResult = entityManager.find(ShoppingCart.class, shoppingCart.getId());

        assertThat(actualResult).isEqualTo(shoppingCart);
    }

    @Test
    void findAllOrdersWithProductsOfOneUser() {
        TestDataImporter.importData(sessionFactory);

        var userFilter = UserFilter.builder()
                .personalInformation(PersonalInformation.builder()
                        .email("petr@gmail.com")
                        .build())
                .build();

        var results = shoppingCartRepository.findAllOrdersWithProductsOfOneUser(userFilter);
        assertThat(results).hasSize(3);

        var orderId = results.stream().map(it -> it.getOrder().getId()).collect(toList());
        assertThat(orderId).contains(3, 3, 3);

        var categoryResult = results.stream().map(it -> it.getProduct().getCatalog().getCategory()).collect(toList());
        assertThat(categoryResult).contains("TV", "Smartphone", "Headphones");
        deleteAll(entityManager);
    }

    @Test
    void findUsersWhoMadeAnOrderAtSpecificTime() {
        TestDataImporter.importData(sessionFactory);

        var results = shoppingCartRepository.findUsersWhoMadeOrderSpecificTime(LocalDate.of(2022, 10, 10), LocalDate.of(2023, 12, 12));
        assertThat(results).hasSize(4);

        var firstName = results.stream().map(it -> it.getOrder().getUser().getPersonalInformation().getFirstName()).collect(toList());
        assertThat(firstName).contains("Ivan", "Sveta", "Vasia", "Vasia");

        var email = results.stream().map(it -> it.getOrder().getUser().getPersonalInformation().getEmail()).collect(toList());
        assertThat(email).contains("ivan@gmail.com", "sveta@gmail.com", "vasia@gmail.com", "vasia@gmail.com");
        deleteAll(entityManager);
    }

    @Test
    void getStatisticOfEachOrdersWithSum() {
        TestDataImporter.importData(sessionFactory);

        var result = shoppingCartRepository.getStatisticOfEachOrdersWithSum();
        assertThat(result).hasSize(8);

        var orderId = result.stream().map(it -> it.get(0, Integer.class)).collect(toList());
        assertThat(orderId).contains(1, 2, 3, 4, 5, 6, 7, 8);

        var orderSum = result.stream().map(it -> it.get(1, Integer.class)).collect(toList());
        assertThat(orderSum).contains(1000, 1100, 3400, 2000, 1450, 1450, 4000, 3350);
        deleteAll(entityManager);
    }

    @Test
    void findUsersWhoMadeAnOrderOfSpecificProduct() {
        TestDataImporter.importData(sessionFactory);

        var productFilter = ProductFilter.builder()
                .brand(Brand.SONY)
                .catalog(Catalog.builder()
                        .category("Headphones")
                        .build())
                .model("XM4")
                .build();

        var results = shoppingCartRepository.findUsersWhoMadeOrderOfSpecificProduct(productFilter);
        assertThat(results).hasSize(2);

        var firstName = results.stream().map(it -> it.getOrder().getUser().getPersonalInformation().getFirstName()).collect(toList());
        assertThat(firstName).contains("Dima", "Ksenia");

        var telephoneResult = results.stream().map(it -> it.getOrder().getUser().getPersonalInformation().getTelephone()).collect(toList());
        assertThat(telephoneResult).contains("321-32-32", "123-32-32");

        var emailResult = results.stream().map(it -> it.getOrder().getUser().getPersonalInformation().getEmail()).collect(toList());
        assertThat(emailResult).contains("dima@gmail.com", "ksenia@gmail.com");
        deleteAll(entityManager);

    }


}




