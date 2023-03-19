package dao.crudIT;

import entity.Catalog;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static util.SessionUtil.closeTransactionSession;
import static util.SessionUtil.openTransactionSession;

class CatalogDaoTestIT {

    @Test
    void findAll() {
        try (Session session = openTransactionSession()) {
            var tv = Catalog.builder().category("TV").build();
            var smartphone = Catalog.builder().category("Smartphone").build();
            var bag = Catalog.builder().category("Bag").build();
            session.save(tv);
            session.save(smartphone);
            session.save(bag);
            closeTransactionSession();

            var query = session.createNativeQuery("select c.* from catalog c ", Catalog.class);
            var actualResult = query.list();

            assertThat(actualResult).containsExactlyInAnyOrder(tv, smartphone, bag);
        }
    }

    @Test
    void findById() {
        try (Session session = openTransactionSession()) {
            var tv = Catalog.builder().category("TV").build();
            session.save(tv);
            closeTransactionSession();

            var actualResult = session.get(Catalog.class, tv.getId());

            assertThat(actualResult).isEqualTo(tv);
        }
    }

    @Test
    void delete() {
        try (Session session = openTransactionSession()) {
            var tv = Catalog.builder().category("TV").build();
            session.save(tv);
            closeTransactionSession();

            session.delete(tv);

            var actualResult = session.get(Catalog.class, tv.getId());
            assertThat(actualResult).isNull();
        }
    }

    @Test
    void update() {
        try (Session session = openTransactionSession()) {
            var tv = Catalog.builder().category("TV").build();
            session.save(tv);
            closeTransactionSession();

            var updatedCatalog = session.get(Catalog.class, tv.getId());
            updatedCatalog.setCategory("TV-update");
            session.update(tv);

            var actualResult = session.get(Catalog.class, tv.getId());
            assertThat(actualResult).isEqualTo(tv);
        }
    }

    @Test
    void save() {
        try (Session session = openTransactionSession()) {
            var tv = Catalog.builder().category("TV").build();

            session.save(tv);

            closeTransactionSession();

            var actualResult = session.get(Catalog.class, tv.getId());
            assertNotNull(actualResult.getId());
        }
    }
}
