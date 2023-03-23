package dao.repository;

import com.dmdev.webStore.dao.repository.CatalogRepository;
import com.dmdev.webStore.entity.Catalog;
import dao.repository.initProxy.ProxySessionTestBase;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import util.TestCreateObjectForRepository;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;

class CatalogRepositoryIT extends ProxySessionTestBase {

    private final CatalogRepository catalogRepository = applicationContext.getBean(CatalogRepository.class);
    @Test
    void deleteCatalog() {
        var catalog = TestCreateObjectForRepository.getCatalog();
        //сохраняем, присваиваем id
        catalogRepository.save(catalog);
        //удаляем товар по id
        catalogRepository.delete(catalog);
        //через сессию достаем сохраненный каталог
        var actualResult = entityManager.find(Catalog.class, catalog.getId());
        assertThat(actualResult).isNull();
    }


    @Test
    void saveCatalog() {
        var catalog = TestCreateObjectForRepository.getCatalog();

        catalogRepository.save(catalog);

        Catalog actualResult = entityManager.find(Catalog.class, catalog.getId());
        assertThat(actualResult).isEqualTo(catalog);
    }

    @Test
    void updateCatalog() {
        var catalog = TestCreateObjectForRepository.getCatalog();
        catalogRepository.save(catalog);

        Catalog result = entityManager.find(Catalog.class, catalog.getId());
        result.setCategory("update-category");
        catalogRepository.update(catalog);

        Catalog actualResult = entityManager.find(Catalog.class, catalog.getId());
        assertThat(actualResult).isEqualTo(catalog);
    }

    @Test
    void findByIdCatalog() {
        var catalog = TestCreateObjectForRepository.getCatalog();
        catalogRepository.save(catalog);

        var actualResult =  entityManager.find(Catalog.class, catalog.getId());

        assertThat(actualResult).isEqualTo(catalog);
    }
}