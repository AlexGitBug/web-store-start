package com.dmdev.webStore.repository.filter;

import com.dmdev.webStore.entity.embeddable.PersonalInformation;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder
public class UserFilter {

    PersonalInformation personalInformation;

    LocalDate startDate;
    LocalDate endDate;
}