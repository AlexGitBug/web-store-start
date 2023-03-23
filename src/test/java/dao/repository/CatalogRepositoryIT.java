package dao.repository;

import com.dmdev.webStore.dao.repository.CatalogRepository;
import com.dmdev.webStore.entity.Catalog;
import dao.repository.initProxy.ProxySessionTestBase;
import org.junit.jupiter.api.Test;
import util.TestCreateObjectForRepository;

import static org.assertj.core.api.Assertions.assertThat;

class CatalogRepositoryIT extends ProxySessionTestBase {

    private final CatalogRepository catalogRepository = new CatalogRepository(Catalog.class, session);

    @Test
    void deleteCatalog() {
        var catalog = TestCreateObjectForRepository.getCatalog();
        //сохраняем, присваиваем id
        catalogRepository.save(catalog);
        //удаляем товар по id
        catalogRepository.delete(catalog.getId());
        //через сессию достаем сохраненный каталог
        Catalog actualResult = session.get(Catalog.class, catalog.getId());
        assertThat(actualResult).isNull();
    }

    @Test
    void saveCatalog() {
        var catalog = TestCreateObjectForRepository.getCatalog();

        catalogRepository.save(catalog);

        Catalog actualResult = session.get(Catalog.class, catalog.getId());
        assertThat(actualResult).isEqualTo(catalog);
    }

    @Test
    void updateCatalog() {
        var catalog = TestCreateObjectForRepository.getCatalog();
        catalogRepository.save(catalog);

        Catalog result = session.get(Catalog.class, catalog.getId());
        result.setCategory("update-category");
        catalogRepository.update(catalog);

        Catalog actualResult = session.get(Catalog.class, catalog.getId());
        assertThat(actualResult).isEqualTo(catalog);
    }

    @Test
    void findByIdCatalog() {
        var catalog = TestCreateObjectForRepository.getCatalog();
        catalogRepository.save(catalog);

        var actualResult =  session.get(Catalog.class, catalog.getId());

        assertThat(actualResult).isEqualTo(catalog);
    }
}