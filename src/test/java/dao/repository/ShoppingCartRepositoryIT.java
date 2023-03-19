package dao.repository;

import dao.repository.filter.ProductFilter;
import dao.repository.filter.UserFilter;
import entity.Catalog;
import entity.Order;
import entity.Product;
import entity.ShoppingCart;
import entity.User;
import entity.embeddable.PersonalInformation;
import entity.enums.Brand;
import initProxy.ProxySessionTestBase;

import org.hibernate.graph.GraphSemantic;
import org.junit.jupiter.api.Test;
import util.TestCreateObjectForRepository;
import util.TestDataImporter;

import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

public class ShoppingCartRepositoryIT extends ProxySessionTestBase {

    private final ShoppingCartRepository shoppingCartRepository = new ShoppingCartRepository(ShoppingCart.class, session);
    private final ProductRepository productRepository = new ProductRepository(Product.class, session);
    private final OrderRepository orderRepository = new OrderRepository(Order.class, session);
    private final UserRepository userRepository = new UserRepository(User.class, session);
    private final CatalogRepository catalogRepository = new CatalogRepository(Catalog.class, session);

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

        shoppingCartRepository.delete(shoppingCart.getId());

        var actualResult = session.get(ShoppingCart.class, shoppingCart.getId());
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

        var actualResult = session.get(ShoppingCart.class, shoppingCart.getId());
        assertThat(actualResult).isEqualTo(shoppingCart);
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

        var result = session.get(ShoppingCart.class, shoppingCart.getId());
        result.setCreatedAt(LocalDate.now());
        shoppingCartRepository.update(shoppingCart);

        var actualResult = session.get(ShoppingCart.class, result.getId());
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

        Map<String, Object> properties = Map.of(
                GraphSemantic.LOAD.getJpaHintName(), session.getEntityGraph("findAllOrdersOfUsers")
        );
        Optional<ShoppingCart> actualResult = shoppingCartRepository.findById(shoppingCart.getId(), properties);

        assertThat(actualResult).contains(shoppingCart);
    }


    @Test
    void findAllOrdersWithProductsOfOneUser() {
        TestDataImporter.importData(sessionFactory);

        var userFilter = UserFilter.builder()
                .personalInformation(PersonalInformation.builder()
                        .email("petr@gmail.com")
                        .build())
                .build();

        var results = shoppingCartRepository.findAllOrdersWithProductsOfOneUser(session, userFilter);
        assertThat(results).hasSize(3);

        var orderId = results.stream().map(it -> it.getOrder().getId()).collect(toList());
        assertThat(orderId).contains(3, 3, 3);

        var categoryResult = results.stream().map(it -> it.getProduct().getCatalog().getCategory()).collect(toList());
        assertThat(categoryResult).contains("TV", "Smartphone", "Headphones");
    }

    @Test
    void findUsersWhoMadeAnOrderAtASpecificTime() {
        TestDataImporter.importData(sessionFactory);

        var results = shoppingCartRepository.findUsersWhoMadeAnOrderAtASpecificTime(session, LocalDate.of(2022, 10, 10), LocalDate.of(2023, 12, 12));
        assertThat(results).hasSize(4);

        var firstName = results.stream().map(it -> it.getOrder().getUser().getPersonalInformation().getFirstName()).collect(toList());
        assertThat(firstName).contains("Ivan", "Sveta", "Vasia", "Vasia");

        var email = results.stream().map(it -> it.getOrder().getUser().getPersonalInformation().getEmail()).collect(toList());
        assertThat(email).contains("ivan@gmail.com", "sveta@gmail.com", "vasia@gmail.com", "vasia@gmail.com");
    }

    @Test
    void getStatisticOfEachOrdersWithSum() {
        TestDataImporter.importData(sessionFactory);

        var result = shoppingCartRepository.getStatisticOfEachOrdersWithSum(session);
        assertThat(result).hasSize(8);

        var orderId = result.stream().map(it -> it.get(0, Integer.class)).collect(toList());
        assertThat(orderId).contains(1, 2, 3, 4, 5, 6, 7, 8);

        var orderSum = result.stream().map(it -> it.get(1, Integer.class)).collect(toList());
        assertThat(orderSum).contains(1000, 1100, 3400, 2000, 1450, 1450, 4000, 3350);
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

        var results = shoppingCartRepository.findUsersWhoMadeAnOrderOfSpecificProduct(session, productFilter);
        assertThat(results).hasSize(2);

        var firstName = results.stream().map(it -> it.getOrder().getUser().getPersonalInformation().getFirstName()).collect(toList());
        assertThat(firstName).contains("Dima", "Ksenia");

        var telephoneResult = results.stream().map(it -> it.getOrder().getUser().getPersonalInformation().getTelephone()).collect(toList());
        assertThat(telephoneResult).contains("321-32-32", "123-32-32");

        var emailResult = results.stream().map(it -> it.getOrder().getUser().getPersonalInformation().getEmail()).collect(toList());
        assertThat(emailResult).contains("dima@gmail.com", "ksenia@gmail.com");
    }
}

