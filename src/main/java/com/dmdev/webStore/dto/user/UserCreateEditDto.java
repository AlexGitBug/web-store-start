package com.dmdev.webStore.dto.user;

import com.dmdev.webStore.entity.enums.Role;
import lombok.Value;
import lombok.experimental.FieldNameConstants;

import java.time.LocalDate;

@Value
@FieldNameConstants
public class UserCreateEditDto {
    String firstName;
    String lastName;
    String email;
    String password;
    String telephone;
    LocalDate birthDate;
    Role role;
}