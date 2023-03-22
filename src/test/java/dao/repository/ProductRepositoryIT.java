package dao.repository;

import dao.repository.filter.OrderFilter;
import dao.repository.filter.ProductFilter;
import dao.repository.filter.UserFilter;
import dao.repository.initProxy.ProxySessionTestBase;
import entity.Catalog;
import entity.Product;
import entity.embeddable.PersonalInformation;
import entity.enums.Brand;

import org.hibernate.graph.GraphSemantic;
import org.junit.jupiter.api.Test;
import util.TestCreateObjectForRepository;
import util.TestDataImporter;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

public class ProductRepositoryIT extends ProxySessionTestBase {

    private final ProductRepository productRepository = new ProductRepository(Product.class, session);
    private final CatalogRepository catalogRepository = new CatalogRepository(Catalog.class, session);


    @Test
    void deleteProduct() {
        var catalog = TestCreateObjectForRepository.getCatalog();
        catalogRepository.save(catalog);
        var product = TestCreateObjectForRepository.getProduct(catalog);
        productRepository.save(product);

        productRepository.delete(product.getId());

        var actualResult = session.get(Product.class, product.getId());
        assertThat(actualResult).isNull();
    }

    @Test
    void saveProduct() {
        var catalog = TestCreateObjectForRepository.getCatalog();
        catalogRepository.save(catalog);
        var product = TestCreateObjectForRepository.getProduct(catalog);

        productRepository.save(product);

        var actualResult = session.get(Product.class, product.getId());
        assertThat(actualResult).isEqualTo(product);
    }

    @Test
    void updateProduct() {
        var catalog = TestCreateObjectForRepository.getCatalog();
        catalogRepository.save(catalog);
        var product = TestCreateObjectForRepository.getProduct(catalog);
        productRepository.save(product);

        Product result = session.get(Product.class, product.getId());
        product.setModel("test-update");
        productRepository.update(product);

        var actualResult = session.get(Product.class, result.getId());
        assertThat(actualResult).isEqualTo(product);

    }

    @Test
    void findByIdProduct() {
        var catalog = TestCreateObjectForRepository.getCatalog();
        catalogRepository.save(catalog);
        var product = TestCreateObjectForRepository.getProduct(catalog);
        productRepository.save(product);

        var actualResult = session.get(Product.class, product.getId());

        assertThat(actualResult).isEqualTo(product);

    }

    @Test
    void findListOfProductsEq() {
        TestDataImporter.importData(sessionFactory);

        var productFilter = ProductFilter.builder()
                .catalog(TestCreateObjectForRepository.getCatalog())
                .brand(Brand.APPLE)
                .build();

        var results = productRepository.findListOfProductsEq(productFilter);
        assertThat(results).hasSize(3);

        var categoryResult = results.stream().map(it -> it.getCatalog().getCategory()).collect(toList());
        assertThat(categoryResult).contains(productFilter.getCatalog().getCategory());

        var brandResult = results.stream().map(Product::getBrand).collect(toList());
        assertThat(brandResult).contains(productFilter.getBrand());
    }

    @Test
    void findListOfProductOfOneCategoryAndBrandBetweenTwoPrice() {
        TestDataImporter.importData(sessionFactory);

        var productFilter = ProductFilter.builder()
                .catalog(Catalog.builder()
                        .category("Headphones")
                        .build())
                .brand(Brand.SONY)
                .build();

        var results = productRepository.findListOfProductOfOneCategoryAndBrandBetweenTwoPrice(productFilter, 349, 401);
        assertThat(results).hasSize(2);

        var brands = results.stream().map(Product::getBrand).collect(toList());
        assertThat(brands).contains(productFilter.getBrand());

        var price = results.stream().map(Product::getPrice).toList();
        assertThat(price).contains(350, 400);
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

        var results = productRepository.findListOfProductGtPrice(productFilter, 1050);
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

        var results = productRepository.findListOfProductLtPrice(productFilter, 1050);
        assertThat(results).hasSize(2);

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

        var results = productRepository.findProductsGtPriceAndBrand(productFilter);
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

        var results = productRepository.findAllProductOfBrand(productFilter);
        assertThat(results).hasSize(5);

        var brands = results.stream().map(Product::getBrand).toList();
        assertThat(brands).contains(productFilter.getBrand());
    }

    @Test
    void findAllProductsFromOrder() {
        TestDataImporter.importData(sessionFactory);

        var orderFilter = OrderFilter.builder()
                .id(3)
                .build();

        List<Product> results = productRepository.findAllProductsFromOrder(orderFilter);
        assertThat(results).hasSize(3);

        var brands = results.stream().map(Product::getBrand).toList();
        assertThat(brands).contains(Brand.SAMSUNG, Brand.SAMSUNG, Brand.SONY);

        var modelResult = results.stream().map(Product::getModel).collect(toList());
        assertThat(modelResult).contains("A80J", "S22", "XM3");
    }

    @Test
    void findMinPriceOfProduct() {
        TestDataImporter.importData(sessionFactory);

        var result = productRepository.findMinPriceOfProduct();

        assertThat(result.getPrice()).isEqualTo(150);
    }

}
