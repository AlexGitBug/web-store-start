package com.dmdev.webStore.dao.repository;

import com.dmdev.webStore.entity.User;
import com.querydsl.jpa.impl.JPAQuery;
import org.hibernate.graph.GraphSemantic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.dmdev.webStore.dao.repository.filter.QPredicate;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

import static com.dmdev.webStore.entity.QOrder.order;
import static com.dmdev.webStore.entity.QUser.user;

@Repository
public interface UserRepository extends
        JpaRepository<User, Integer>,
        FilterUserRepository {
}
