package com.dmdev.webStore.dto.user;

import com.dmdev.webStore.entity.enums.Role;
import lombok.Value;
import lombok.experimental.FieldNameConstants;

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
    @Size(min = 3, max = 30)
    String email;
    String password;

    @Pattern(regexp = "\\+375 \\(\\d{2}\\) \\d{3}-\\d{2}-\\d{2}")
    String telephone;
    @Past
    LocalDate birthDate;
    Role role;
}