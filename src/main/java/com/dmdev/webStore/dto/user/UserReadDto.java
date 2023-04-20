package com.dmdev.webStore.dto.user;
import com.dmdev.webStore.entity.enums.Role;
import lombok.Value;

import java.time.LocalDate;

@Value
public class UserReadDto {
    Integer id;
    String firstName;
    String lastName;
    String email;
    String password;
    String telephone;
    LocalDate birthDate;
    Role role;
}