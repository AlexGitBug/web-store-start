package dao.dao;

import entity.Product;

import java.util.List;
import java.util.Optional;

public class ProductDao implements Dao<Integer, Product>{
    @Override
    public List<Product> findAll() {
        return null;
    }

    @Override
    public Optional<Product> findById(Integer id) {
        return Optional.empty();
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }

    @Override
    public Product update(Product entity) {
        return null;
    }

    @Override
    public Product save(Product entity) {
        return null;
    }
}
