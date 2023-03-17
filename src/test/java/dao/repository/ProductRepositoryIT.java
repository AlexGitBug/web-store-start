package dao.repository;

import dao.repisitory.CatalogRepository;
import dao.repisitory.OrderRepository;
import dao.repisitory.ProductRepository;
import dao.repisitory.filter.OrderFilter;
import dao.repisitory.filter.ProductFilter;
import dao.repisitory.filter.UserFilter;
import entity.Catalog;
import entity.Order;
import entity.Product;
import entity.embeddable.PersonalInformation;
import entity.enums.Brand;
import entity.enums.Color;
import initProxy.ProxySessionTestBase;
import org.hibernate.graph.GraphSemantic;
import org.junit.jupiter.api.Test;
import util.TestCreateObjectForRepository;
import util.TestDataImporter;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

public class ProductRepositoryIT extends ProxySessionTestBase {

    private final ProductRepository productRepository = new ProductRepository(Product.class, session);
    private final CatalogRepository catalogRepository = new CatalogRepository(Catalog.class, session);
    private final OrderRepository orderRepository = new OrderRepository(Order.class, session);

    @Test
    void deleteProduct() {
        var catalog = TestCreateObjectForRepository.getCatalog();
        catalogRepository.save(catalog);
        var product = getProduct(catalog);
        productRepository.save(product);

        productRepository.delete(product.getId());

        var actualResult = session.get(Product.class, product.getId());
        assertThat(actualResult).isNull();
    }

    @Test
    void saveProduct() {
        var catalog = TestCreateObjectForRepository.getCatalog();
        catalogRepository.save(catalog);
        var product = getProduct(catalog);

        productRepository.save(product);

        var actualResult = session.get(Product.class, product.getId());
        assertThat(actualResult).isEqualTo(product);
    }

    @Test
    void updateProduct() {
        var catalog = TestCreateObjectForRepository.getCatalog();
        catalogRepository.save(catalog);
        var product = getProduct(catalog);
        productRepository.save(product);

        Product result = session.get(Product.class, product.getId());
        product.setModel("test-update");
        productRepository.update(product);

        var actualResult = session.get(Product.class, result.getId());
        assertThat(actualResult).isEqualTo(product);

    }

    @Test
    void findById() {
        var catalog = TestCreateObjectForRepository.getCatalog();
        catalogRepository.save(catalog);
        var product = getProduct(catalog);
        productRepository.save(product);

        Map<String, Object> properties = Map.of(
                GraphSemantic.LOAD.getJpaHintName(), session.getEntityGraph("withCatalog")
        );
        Optional<Product> actualResult = productRepository.findById(product.getId(), properties);

        assertThat(actualResult).contains(product);

    }

    @Test
    void findListOfProductsEq() {
        TestDataImporter.importData(sessionFactory);
                var productFilter = ProductFilter.builder()
                .catalog(Catalog.builder()
                        .category("Smartphone")
                        .build())
                .brand(Brand.APPLE)
                .build();

        var results = productRepository.findListOfProductsEq(session, productFilter);
        assertThat(results).hasSize(2);

        var categoryResult = results.stream().map(it -> it.getCatalog().getCategory()).collect(toList());
        assertThat(categoryResult).contains(productFilter.getCatalog().getCategory());

        var brandResult = results.stream().map(Product::getBrand).collect(toList());
        assertThat(brandResult).contains(productFilter.getBrand());
    }

    @Test
    void findListOfProductBetweenTwoPrice() {
        TestDataImporter.importData(sessionFactory);

        var productFilter = ProductFilter.builder()
                .catalog(Catalog.builder()
                        .category("Headphones")
                        .build())
                .brand(Brand.SONY)
                .build();

        var results = productRepository.findListOfProductBetweenTwoPrice(session, productFilter, 349, 401);
        assertThat(results).hasSize(2);

        var brands = results.stream().map(Product::getBrand).collect(toList());
        assertThat(brands).contains(productFilter.getBrand());
    }

    @Test
    void findListOfProductGtPrice() {
        TestDataImporter.importData(sessionFactory);

        var productFilter = ProductFilter.builder()
                .catalog(Catalog.builder()
                        .category("Smartphone")
                        .build())
                .brand(Brand.APPLE)
                .build();

        var results = productRepository.findListOfProductGtPrice(session, productFilter, 1050);
        assertThat(results).hasSize(1);

        var categoryResult = results.stream().map(it -> it.getCatalog().getCategory()).collect(toList());
        assertThat(categoryResult).contains(productFilter.getCatalog().getCategory());
    }

    @Test
    void findListOfProductLtPrice() {
        TestDataImporter.importData(sessionFactory);

        var productFilter = ProductFilter.builder()
                .catalog(Catalog.builder()
                        .category("Smartphone")
                        .build())
                .brand(Brand.APPLE)
                .build();

        var results = productRepository.findListOfProductLtPrice(session, productFilter, 1050);
        assertThat(results).hasSize(1);

        var categoryResult = results.stream().map(it -> it.getCatalog().getCategory()).collect(toList());
        assertThat(categoryResult).contains(productFilter.getCatalog().getCategory());
    }

    @Test
    void findProductsGtPriceAndBrand() {
        TestDataImporter.importData(sessionFactory);

        var productFilter = ProductFilter.builder()
                .catalog(Catalog.builder()
                        .category("Smartphone")
                        .build())
                .brand(Brand.SAMSUNG)
                .build();

        var results = productRepository.findProductsGtPriceAndBrand(session, productFilter);
        assertThat(results).hasSize(3);

        var categoryResult = results.stream().map(it -> it.getCatalog().getCategory()).collect(toList());
        assertThat(categoryResult).contains(productFilter.getCatalog().getCategory());
    }

    @Test
    void findAllProductOfBrand() {
        TestDataImporter.importData(sessionFactory);
        var productFilter = ProductFilter.builder()
                .brand(Brand.SAMSUNG)
                .build();

        var results = productRepository.findAllProductOfBrand(session, productFilter);
        assertThat(results).hasSize(5);

        var brands = results.stream().map(Product::getBrand).toList();
        assertThat(brands).contains(productFilter.getBrand());
    }

    @Test
    void findAllProductsFromOrder() {
        TestDataImporter.importData(sessionFactory);
        var userFilter = UserFilter.builder()
                .personalInformation(PersonalInformation.builder()
                        .email("petr@gmail.com")
                        .build())
                .build();

        var orderFilter = OrderFilter.builder()
                .id(3)
                .build();

        List<Product> results = productRepository.findAllProductsFromOrder(session, userFilter, orderFilter);
        assertThat(results).hasSize(3);

        var categoryResult = results.stream().map(it -> it.getCatalog().getCategory()).collect(toList());
        assertThat(categoryResult).contains("TV", "Smartphone", "Headphones");
    }

    @Test
    void findMinPriceOfProduct() {
        TestDataImporter.importData(sessionFactory);

        var result = productRepository.findMinPriceOfProduct(session);

        assertThat(result.getPrice()).isEqualTo(150);
    }


    private static Product getProduct(Catalog catalog) {
        return new Product().builder()
                .catalog(catalog)
                .brand(Brand.SONY)
                .model("test")
                .dateOfRelease(LocalDate.of(2021, 1, 1))
                .price(151)
                .color(Color.BLACK)
                .image("test")
                .build();
    }

    public static Catalog getCatalog() {
        return Catalog.builder()
                .category("categoryName")
                .build();
    }


}
