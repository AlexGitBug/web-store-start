package com.dmdev.webStore.integration;

import com.dmdev.webStore.dao.repository.CatalogRepository;
import com.dmdev.webStore.entity.Catalog;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import com.dmdev.webStore.util.MocksForRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@RequiredArgsConstructor
public class CatalogRepositoryIT extends IntegrationTestBase {

    private static final String SMARTPHONE = "Smartphone";
    private static final String TV = "TV";
    private static final String HEADPHONES = "Headphones";

    private final CatalogRepository catalogRepository;

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

        assertThat(catalog.getId()).isNotNull();
    }

    @Test
    @Disabled /* выключен, т.к. стоит cache = READ*/
    void updateCatalogIT() {
        var catalog = MocksForRepository.getCatalog();
        catalogRepository.save(catalog);

        var updatedCatalog = catalogRepository.findById(catalog.getId());
        updatedCatalog.ifPresent(it -> it.setCategory("update-category"));
        catalogRepository.saveAndFlush(updatedCatalog.get());

        var actualResult = catalogRepository.findById(catalog.getId());
        assertThat(actualResult).isPresent();
        assertThat(actualResult.get().getCategory())
                .isEqualTo(catalog.getCategory());
    }

    @Test
    void findByIdCatalogIT() {
        var catalog = MocksForRepository.getCatalog();
        catalogRepository.save(catalog);

        var actualResult = catalogRepository.findById(catalog.getId());
        assertThat(actualResult).isPresent();
        assertThat(actualResult.get()).isEqualTo(catalog);
    }

    @Test
    void findAllCatalogIT() {
        var actualResult = catalogRepository.findAll();

        var categories = actualResult.stream()
                .map(Catalog::getCategory)
                .toList();
        assertAll(
                () -> assertThat(actualResult).hasSize(3),
                () -> assertThat(categories).containsExactlyInAnyOrder(
                        SMARTPHONE, TV, HEADPHONES
                )
        );
    }
}