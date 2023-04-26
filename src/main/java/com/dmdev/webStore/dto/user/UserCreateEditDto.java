package com.dmdev.webStore.dto.user;

import com.dmdev.webStore.entity.enums.Role;
import lombok.Value;
import lombok.experimental.FieldNameConstants;
//import com.dmdev.spring.validation.group.CreateAction;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Value
@FieldNameConstants
public class UserCreateEditDto {
    String firstName;
    String lastName;
    String email;

//    @NotBlank(groups = CreateAction.class)
    String password;
    String telephone;
    LocalDate birthDate;
    Role role;
}