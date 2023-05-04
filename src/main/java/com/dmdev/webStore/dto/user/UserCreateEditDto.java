package com.dmdev.webStore.dto.user;

import com.dmdev.webStore.entity.enums.Role;
import lombok.Value;
import lombok.experimental.FieldNameConstants;
//import com.dmdev.spring.validation.group.CreateAction;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Value
@FieldNameConstants
public class UserCreateEditDto {
    @NotBlank
    @NotEmpty
    @Size(min = 3, max = 30)
    String firstName;
    @NotBlank
    @NotEmpty
    @Size(min = 3, max = 30)
    String lastName;
    @Email
    String email;
    String password;
    String telephone;
    @Past
    LocalDate birthDate;
    Role role;
}