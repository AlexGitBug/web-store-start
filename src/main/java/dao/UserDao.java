package dao;

import entity.User;

import java.util.List;
import java.util.Optional;

public class UserDao implements Dao<Integer, User> {
    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public Optional<User> findById(Integer id) {
        return Optional.empty();
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }

    @Override
    public User update(User entity) {
        return null;
    }

    @Override
    public User save(User entity) {
        return null;
    }
}
