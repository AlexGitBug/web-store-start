package com.dmdev.webStore.mapper.user;

import com.dmdev.webStore.dto.user.UserReadDto;
import com.dmdev.webStore.entity.User;
import com.dmdev.webStore.mapper.Mapper;
import org.springframework.stereotype.Component;


@Component
public class UserReadMapper implements Mapper<User, UserReadDto> {

    @Override
    public UserReadDto map(User object) {
        return new UserReadDto(
                object.getId(),
                object.getPersonalInformation().getFirstName(),
                object.getPersonalInformation().getLastName(),
                object.getPersonalInformation().getEmail(),
                object.getPassword(),
                object.getPersonalInformation().getTelephone(),
                object.getPersonalInformation().getBirthDate(),
                object.getRole()
        );
    }
}
