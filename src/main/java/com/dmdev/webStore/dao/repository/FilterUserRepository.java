package com.dmdev.webStore.dao.repository;

import com.dmdev.webStore.entity.User;

import java.time.LocalDate;
import java.util.List;

public interface FilterUserRepository {

    List<User> findUsersWhoMadeOrderSpecificTime(LocalDate startDate, LocalDate endDate);
}
