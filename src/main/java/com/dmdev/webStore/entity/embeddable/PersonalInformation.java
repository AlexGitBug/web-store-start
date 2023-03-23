package com.dmdev.webStore.entity.embeddable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class PersonalInformation {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String telephone;
    @Column(name = "birth_date")
    private LocalDate birthDate;

}
