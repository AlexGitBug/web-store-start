package com.dmdev.webStore.dao.repository;

import com.dmdev.webStore.entity.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
@Repository
public class UserRepository extends RepositoryBase<Integer, User>{

    public UserRepository(EntityManager entityManager) {
        super(User.class, entityManager);
    }
}
