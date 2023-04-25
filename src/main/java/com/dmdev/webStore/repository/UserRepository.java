package com.dmdev.webStore.repository;

import com.dmdev.webStore.entity.User;
import com.dmdev.webStore.entity.embeddable.PersonalInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends
        JpaRepository<User, Integer>,
        FilterUserRepository {

    @Query("select u from User u " +
            "join fetch u.personalInformation pi " +
            "where pi.email = :email")
    Optional<User> findByEmail(@Param("email") String email);
}
