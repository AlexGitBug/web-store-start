package dao.repository;

import entity.User;

import javax.persistence.EntityManager;

public class UserRepository extends RepositoryBase<Integer, User>{

    public UserRepository(Class<User> clazz, EntityManager entityManager) {
        super(clazz, entityManager);
    }
}
