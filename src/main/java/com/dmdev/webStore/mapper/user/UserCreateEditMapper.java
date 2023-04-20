package com.dmdev.webStore.mapper.user;

import com.dmdev.webStore.dto.user.UserCreateEditDto;
import com.dmdev.webStore.entity.User;
import com.dmdev.webStore.entity.embeddable.PersonalInformation;
import com.dmdev.webStore.mapper.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserCreateEditMapper implements Mapper<UserCreateEditDto, User> {

    @Override
    public User map(UserCreateEditDto object) {
        User user = new User();
        copy(object, user);
        return user;
    }

    @Override
    public User map(UserCreateEditDto fromObject, User toObject) {
        copy(fromObject, toObject);
        return toObject;
    }

    private void copy(UserCreateEditDto object, User user) {
        user.setRole(object.getRole());
        user.setPersonalInformation(new PersonalInformation(
                object.getFirstName(),
                object.getLastName(),
                object.getEmail(),
                object.getPassword(),
                object.getTelephone(),
                object.getBirthDate()
        ));

    }
}
