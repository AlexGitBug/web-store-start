package unUsedCode.dao;

import com.dmdev.webStore.entity.Catalog;

import java.util.List;
import java.util.Optional;

public class CatalogDao implements Dao<Integer, Catalog> {
    @Override
    public List<Catalog> findAll() {
        return null;
    }

    @Override
    public Optional<Catalog> findById(Integer id) {
        return Optional.empty();
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }

    @Override
    public Catalog update(Catalog entity) {
        return null;
    }

    @Override
    public Catalog save(Catalog entity) {
        return null;
    }
}
