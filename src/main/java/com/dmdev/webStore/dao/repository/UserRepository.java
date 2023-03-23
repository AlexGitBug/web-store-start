package com.dmdev.webStore.dao.repository;

import com.dmdev.webStore.entity.User;

import javax.persistence.EntityManager;

public class UserRepository extends RepositoryBase<Integer, User>{

    public UserRepository(Class<User> clazz, EntityManager entityManager) {
        super(clazz, entityManager);
    }
}
