package unUsedCode.crudIT;

import com.dmdev.webStore.entity.Catalog;
import com.dmdev.webStore.entity.Product;
import com.dmdev.webStore.entity.enums.Brand;
import com.dmdev.webStore.entity.enums.Color;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static unUsedCode.dao.util.SessionUtil.closeTransactionSession;
import static unUsedCode.dao.util.SessionUtil.openTransactionSession;

class ProductDaoTestIT {

    @Test
    void findAll() {
        try (Session session = openTransactionSession()) {
            var product = getProduct();
            var productTest = getProductTest();
            var catalog = getCatalog();
            session.save(catalog);
            session.save(product);
            session.saveOrUpdate(productTest);
            closeTransactionSession();

            String sql = """
                    SELECT *
                    FROM product
                    """;
            var query = session.createSQLQuery(sql).addEntity(Product.class);
            var actualResult = query.list();

            assertThat(actualResult).containsExactlyInAnyOrder(product, productTest);
    }
    }

    @Test
    void findById() {
        try (Session session = openTransactionSession()) {
            var product = getProduct();
            var catalog = getCatalog();
            session.save(catalog);
            session.save(product);
            closeTransactionSession();

            var actualResult = session.get(Product.class, product.getId());
            assertThat(actualResult).isEqualTo(product);
        }
    }

    @Test
    void delete() {
        try (Session session = openTransactionSession()) {
            var product = getProduct();
            var catalog = getCatalog();
            session.save(catalog);
            session.save(product);
            closeTransactionSession();

            session.delete(product);

            var actualResult = session.get(Product.class, product.getId());
            assertThat(actualResult).isNull();
        }
    }

    @Test
    void update() {
        try (Session session = openTransactionSession()) {
            var product = getProduct();
            var catalog = getCatalog();
            session.save(catalog);
            session.save(product);
            closeTransactionSession();

            var updatedProduct = session.get(Product.class, product.getId());
            updatedProduct.setColor(Color.BLACK);
            updatedProduct.setBrand(Brand.SONY);
            session.update(product);

            var actualResult = session.get(Product.class, product.getId());
            assertThat(actualResult).isEqualTo(product);
        }
    }

    @Test
    void save() {
        try (Session session = openTransactionSession()) {
            var product = getProduct();
            var catalog = getCatalog();

            session.save(catalog);
            session.save(product);

            closeTransactionSession();

            var actualResult = session.get(Product.class, product.getId());
            assertNotNull(actualResult.getId());
        }
    }

    private static Catalog getCatalog() {
        return Catalog.builder()
                .category("Test")
                .build();
    }

    private static Product getProduct() {
        return Product.builder()
                .catalog(getCatalog())
                .brand(Brand.APPLE)
                .model("test")
                .dateOfRelease(LocalDate.of(2023, 3, 4))
                .price(100)
                .color(Color.WHITE)
                .image("test")
                .build();
    }
    private static Product getProductTest() {
        return Product.builder()
                .catalog(getCatalog())
                .brand(Brand.SONY)
                .model("test1")
                .dateOfRelease(LocalDate.of(2023, 3, 4))
                .price(100)
                .color(Color.BLACK)
                .image("test1")
                .build();
    }
}