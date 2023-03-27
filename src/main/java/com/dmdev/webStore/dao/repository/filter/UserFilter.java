package com.dmdev.webStore.dao.repository.filter;

import com.dmdev.webStore.entity.embeddable.PersonalInformation;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserFilter {

    PersonalInformation personalInformation;
}