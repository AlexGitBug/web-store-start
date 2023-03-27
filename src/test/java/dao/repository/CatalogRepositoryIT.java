package dao.repository;

import com.dmdev.webStore.dao.repository.CatalogRepository;
import com.dmdev.webStore.entity.Catalog;
import dao.repository.initProxy.ProxySessionTestBase;
import org.junit.jupiter.api.Test;
import dao.repository.util.MocksForRepository;

import static org.assertj.core.api.Assertions.assertThat;

public class CatalogRepositoryIT extends ProxySessionTestBase {

    private final CatalogRepository catalogRepository = applicationContext.getBean(CatalogRepository.class);
    @Test
    void deleteCatalog() {
        entityManager.getTransaction().begin();
        var catalog = MocksForRepository.getCatalog();
        catalogRepository.save(catalog);

        catalogRepository.delete(catalog);

        var actualResult = entityManager.find(Catalog.class, catalog.getId());
        assertThat(actualResult).isNull();
        entityManager.getTransaction().commit();
    }


    @Test
    void saveCatalog() {
        entityManager.getTransaction().begin();
        var catalog = MocksForRepository.getCatalog();

        catalogRepository.save(catalog);

        Catalog actualResult = entityManager.find(Catalog.class, catalog.getId());
        assertThat(actualResult).isEqualTo(catalog);
        entityManager.getTransaction().commit();
    }

    @Test
    void updateCatalog() {
        entityManager.getTransaction().begin();
        var catalog = MocksForRepository.getCatalog();
        catalogRepository.save(catalog);

        Catalog result = entityManager.find(Catalog.class, catalog.getId());
        result.setCategory("update-category");
        catalogRepository.update(catalog);

        Catalog actualResult = entityManager.find(Catalog.class, catalog.getId());
        assertThat(actualResult).isEqualTo(catalog);
        entityManager.getTransaction().commit();
    }

    @Test
    void findByIdCatalog() {
        entityManager.getTransaction().begin();
        var catalog = MocksForRepository.getCatalog();
        catalogRepository.save(catalog);

        var actualResult =  entityManager.find(Catalog.class, catalog.getId());

        assertThat(actualResult).isEqualTo(catalog);
        entityManager.getTransaction().commit();
    }
}