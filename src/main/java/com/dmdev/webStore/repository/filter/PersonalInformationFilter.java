package com.dmdev.webStore.repository.filter;

import lombok.Builder;
import lombok.Value;

import javax.persistence.Column;
import java.time.LocalDate;

@Value
@Builder
public class PersonalInformationFilter {
    String firstName;
    String lastName;
    String email;
    String password;
    String telephone;
    @Column(name = "birth_date")
    LocalDate birthDate;
}
