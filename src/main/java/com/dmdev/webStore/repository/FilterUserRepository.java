package com.dmdev.webStore.repository;

import com.dmdev.webStore.repository.filter.UserFilter;
import com.dmdev.webStore.entity.User;

import java.util.List;

public interface FilterUserRepository {

    List<User> findUsersWhoMadeOrderSpecificTime(UserFilter filter);
}
