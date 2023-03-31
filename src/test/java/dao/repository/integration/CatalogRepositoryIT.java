package dao.repository.integration;

import com.dmdev.webStore.dao.repository.CatalogRepository;
import com.dmdev.webStore.entity.Catalog;
import dao.repository.integration.annotation.IT;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import dao.repository.util.MocksForRepository;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;
@IT
@RequiredArgsConstructor
public class CatalogRepositoryIT {
    private final CatalogRepository catalogRepository;
    private final EntityManager entityManager;

    @Test
    void deleteCatalogIT() {
        var catalog = MocksForRepository.getCatalog();
        catalogRepository.save(catalog);

        catalogRepository.delete(catalog);

        assertThat(catalogRepository.findById(catalog.getId())).isEmpty();
    }

    @Test
    void saveCatalogIT() {
        var catalog = MocksForRepository.getCatalog();

        catalogRepository.save(catalog);

        assertThat(catalogRepository.findById(catalog.getId())).contains(catalog);
    }
    @Test
    void updateCatalogIT() {
        var catalog = MocksForRepository.getCatalog();
        catalogRepository.save(catalog);

        Catalog result = entityManager.find(Catalog.class, catalog.getId());
        result.setCategory("update-category");
        catalogRepository.update(catalog);

        Catalog actualResult = entityManager.find(Catalog.class, catalog.getId());
        assertThat(actualResult).isEqualTo(catalog);
    }

    @Test
    void findByIdCatalogIT() {
        var catalog = MocksForRepository.getCatalog();
        catalogRepository.save(catalog);

        var actualResult =  catalogRepository.findById(catalog.getId());

        assertThat(actualResult).contains(catalog);
    }
}