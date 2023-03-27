package dao.repository;

import com.dmdev.webStore.dao.repository.CatalogRepository;
import com.dmdev.webStore.dao.repository.ProductRepository;
import com.dmdev.webStore.dao.repository.filter.OrderFilter;
import com.dmdev.webStore.dao.repository.filter.ProductFilter;
import dao.repository.initProxy.ProxySessionTestBase;
import com.dmdev.webStore.entity.Catalog;
import com.dmdev.webStore.entity.Product;
import com.dmdev.webStore.entity.enums.Brand;

import org.junit.jupiter.api.Test;
import dao.repository.util.MocksForRepository;
import dao.repository.util.TestDataImporter;

import java.time.LocalDate;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

public class ProductRepositoryIT extends ProxySessionTestBase {
    private final ProductRepository productRepository = applicationContext.getBean(ProductRepository.class);
    private final CatalogRepository catalogRepository = applicationContext.getBean(CatalogRepository.class);

    @Test
    void deleteProduct() {
        entityManager.getTransaction().begin();
        var catalog = MocksForRepository.getCatalog();
        catalogRepository.save(catalog);
        var product = MocksForRepository.getProduct(catalog);
        productRepository.save(product);

        productRepository.delete(product);

        var actualResult = entityManager.find(Product.class, product.getId());
        entityManager.getTransaction().commit();
        assertThat(actualResult).isNull();
    }

    @Test
    void saveProduct() {
        entityManager.getTransaction().begin();
        var catalog = MocksForRepository.getCatalog();
        catalogRepository.save(catalog);
        var product = MocksForRepository.getProduct(catalog);

        productRepository.save(product);

        var actualResult = entityManager.find(Product.class, product.getId());
        entityManager.getTransaction().commit();
        assertThat(actualResult).isEqualTo(product);
    }

    @Test
    void updateProduct() {
        entityManager.getTransaction().begin();
        var catalog = MocksForRepository.getCatalog();
        catalogRepository.save(catalog);
        var product = MocksForRepository.getProduct(catalog);
        productRepository.save(product);

        Product result = entityManager.find(Product.class, product.getId());
        product.setModel("test-update");
        productRepository.update(product);

        var actualResult = entityManager.find(Product.class, result.getId());
        entityManager.getTransaction().commit();
        assertThat(actualResult).isEqualTo(product);

    }

    @Test
    void findByIdProduct() {
        entityManager.getTransaction().begin();
        var catalog = MocksForRepository.getCatalog();
        catalogRepository.save(catalog);
        var product = MocksForRepository.getProduct(catalog);
        productRepository.save(product);

        var actualResult = entityManager.find(Product.class, product.getId());
        entityManager.getTransaction().commit();
        assertThat(actualResult).isEqualTo(product);
    }

    @Test
    void findListOfProductsEq() {
        TestDataImporter.importData(sessionFactory);
        entityManager.getTransaction().begin();

        var productFilter = ProductFilter.builder()
                .catalog(MocksForRepository.getCatalog())
                .brand(Brand.APPLE)
                .build();

        var results = productRepository.findListOfProductsEq(productFilter);
        entityManager.getTransaction().commit();
        assertThat(results).hasSize(3);

        var categoryResult = results.stream().map(it -> it.getCatalog().getCategory()).collect(toList());
        assertThat(categoryResult).contains(productFilter.getCatalog().getCategory());

        var brandResult = results.stream().map(Product::getBrand).collect(toList());
        assertThat(brandResult).contains(productFilter.getBrand());
    }

    @Test
    void findProductOfOneCategoryAndBrandBetweenTwoPrice() {
        TestDataImporter.importData(sessionFactory);
        entityManager.getTransaction().begin();

        var productFilter = ProductFilter.builder()
                .catalog(Catalog.builder()
                        .category("Headphones")
                        .build())
                .brand(Brand.SONY)
                .build();

        var results = productRepository.findProductOfOneCategoryAndBrandBetweenTwoPrice(productFilter, 100, 5000);
        entityManager.getTransaction().commit();
        assertThat(results).hasSize(3);

//        var brands = results.stream().map(Product::getBrand).collect(toList());
//        assertThat(brands).contains(productFilter.getBrand());
//
//        var price = results.stream().map(Product::getPrice).toList();
//        assertThat(price).contains(350, 400);
    }


    @Test
    void findProductsOfBrandAndCategoryAndLtPrice() {
        TestDataImporter.importData(sessionFactory);
        entityManager.getTransaction().begin();

        var productFilter = ProductFilter.builder()
                .catalog(Catalog.builder()
                        .category("Smartphone")
                        .build())
                .brand(Brand.APPLE)
                .build();

        var results = productRepository.findProductsOfBrandAndCategoryAndLtPrice(productFilter, 1050);
        entityManager.getTransaction().commit();
        assertThat(results).hasSize(2);

//        var brands = results.stream().map(Product::getBrand).collect(toList());
//        assertThat(brands).contains(Brand.APPLE);

    }

    @Test
    void findProductsOfBrandAndCategoryAndGtPrice() {
        TestDataImporter.importData(sessionFactory);
        entityManager.getTransaction().begin();

        var productFilter = ProductFilter.builder()
                .catalog(Catalog.builder()
                        .category("Smartphone")
                        .build())
                .brand(Brand.SAMSUNG)
                .build();

        var results = productRepository.findProductsOfBrandAndCategoryAndGtPrice(productFilter);
        entityManager.getTransaction().commit();
        assertThat(results).hasSize(3);

//        var brands = results.stream().map(Product::getBrand).collect(toList());
//        assertThat(brands).contains(Brand.SAMSUNG);

    }

    @Test
    void findAllProductOfBrand() {
        TestDataImporter.importData(sessionFactory);
        entityManager.getTransaction().begin();
        var productFilter = ProductFilter.builder()
                .brand(Brand.SAMSUNG)
                .build();

        var results = productRepository.findAllProductOfBrand(productFilter);
        entityManager.getTransaction().commit();
        assertThat(results).hasSize(5);

//        var brands = results.stream().map(Product::getBrand).toList();
//        assertThat(brands).contains(Brand.SAMSUNG);
    }

    @Test
    void findMinPriceOfProduct() {
        TestDataImporter.importData(sessionFactory);
        entityManager.getTransaction().begin();

        var result = productRepository.findMinPriceOfProduct();
        entityManager.getTransaction().commit();
//        assertThat(result.getPrice()).isEqualTo(150);
    }

    @Test
    void findAllProductsFromOrder() {
        TestDataImporter.importData(sessionFactory);

        entityManager.getTransaction().begin();

        var orderFilter = OrderFilter.builder()
                .deliveryDate(LocalDate.of(2022, 12, 10))
                .build();
        var results = productRepository.findAllProductsFromOrder(orderFilter);
        entityManager.getTransaction().commit();
        assertThat(results).hasSize(1);
//        var model = results.stream().map(Product::getModel).collect(toList());
//        assertThat(model).contains("13");
    }

}
