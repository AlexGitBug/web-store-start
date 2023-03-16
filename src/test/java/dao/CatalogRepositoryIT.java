package dao;

import entity.Catalog;
import initProxy.ProxySessionTestBase;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CatalogRepositoryIT extends ProxySessionTestBase {

    private final CatalogRepository catalogRepository = new CatalogRepository(Catalog.class, session);

    @Test
    void deleteCatalog() {
        var catalog = createCatalog();
        catalogRepository.save(catalog);

        catalogRepository.delete(catalog.getId());

        assertThat(session.get(Catalog.class, catalog.getId())).isNull();
    }







    private Catalog createCatalog() {
        Catalog catalog = Catalog.builder()
//                .id(1)
                .category("categoryName")
                .build();
        return catalog;
    }

}