package com.dmdev.webStore.unit.service;


import com.dmdev.webStore.dto.product.ProductCreateEditDto;
import com.dmdev.webStore.dto.user.UserCreateEditDto;
import com.dmdev.webStore.dto.user.UserReadDto;
import com.dmdev.webStore.entity.User;
import com.dmdev.webStore.entity.embeddable.PersonalInformation;
import com.dmdev.webStore.entity.enums.Role;
import com.dmdev.webStore.mapper.user.UserCreateEditMapper;
import com.dmdev.webStore.mapper.user.UserReadMapper;
import com.dmdev.webStore.repository.UserRepository;
import com.dmdev.webStore.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    private static final Integer USER_ID = 1;

    @Mock
    private UserReadMapper userReadMapper;
    @Mock
    private UserCreateEditMapper userCreateEditMapper;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserService userService;


    @Test
    void findAll() {
        var user = getUser();
        var user2 = getUser2();
        List<User> users = List.of(user, user2);
        var userReadDto = getUserReadDto();
        var userReadDto2 = getUserReadDto2();
        var expectListDto = List.of(userReadDto, userReadDto2);
        doReturn(users).when(userRepository).findAll();
        doReturn(expectListDto.get(0), expectListDto.get(1))
                .when(userReadMapper).map(any(User.class));

        List<UserReadDto> actualResult = userService.findAll();

        assertThat(actualResult).hasSize(2);
        assertThat(actualResult).isEqualTo(expectListDto);
        assertThat(actualResult).containsExactlyInAnyOrder(userReadDto, userReadDto2);
    }

    @Test
    void findByIdSuccess() {
        var user = getUser();
        var userReadDto = getUserReadDto();
        doReturn(Optional.of(user)).when(userRepository).findById(user.getId());
        doReturn(userReadDto).when(userReadMapper).map(user);

        var actualResult = userService.findById(USER_ID);

        assertThat(actualResult).isPresent();
        assertThat(actualResult.get()).isEqualTo(userReadDto);
    }

    @Test
    void findByIdNotSuccess() {
        doReturn(Optional.empty()).when(userRepository).findById(USER_ID);

        var actualResult = userService.findById(USER_ID);

        assertThat(actualResult).isEmpty();
        verifyNoInteractions(userReadMapper);
    }

    @Test
    void create() {
        var userCreateEditDto = getUserCreateEditDto();
        var user = getUser();
        var userReadDto = getUserReadDto();
        doReturn(user).when(userCreateEditMapper).map(userCreateEditDto);
        doReturn(user).when(userRepository).save(user);
        doReturn(userReadDto).when(userReadMapper).map(user);

        var actualResult = userService.create(userCreateEditDto);

        assertThat(actualResult).isEqualTo(userReadDto);
        verify(userRepository).save(user);
    }

    @Test
    void nullPointerExceptionCreate() {
        UserCreateEditDto userCreateEditDto = null;

        assertThrows(NullPointerException.class, () -> userService.create(userCreateEditDto));
        verifyNoInteractions(userCreateEditMapper, userRepository, userReadMapper);
    }

    @Test
    void update() {
        var userCreateEditDto = getUserCreateEditDto();
        var user = getUser();
        var updatedUser = getUser();
        var userReadDto = getUserReadDto();
        doReturn(Optional.of(user)).when(userRepository).findById(USER_ID);
        doReturn(updatedUser).when(userCreateEditMapper).map(userCreateEditDto, user);
        doReturn(updatedUser).when(userRepository).saveAndFlush(updatedUser);
        doReturn(userReadDto).when(userReadMapper).map(updatedUser);

        var actualResult = userService.update(USER_ID, userCreateEditDto);

        assertAll(
                () -> assertThat(actualResult).isPresent(),
                () -> assertThat(actualResult.get()).isEqualTo(userReadDto)
        );
    }

    @Test
    void delete() {
        var user = getUser();
        doReturn(Optional.of(user)).when(userRepository).findById(USER_ID);

        var actualResult = userService.delete(USER_ID);

        assertThat(actualResult).isTrue();
    }

    @Test
    void findByEmail() {
        String email = "test@gmail.com";
        var user = getUser();
        var userReadDto = getUserReadDto();
        doReturn(Optional.of(user)).when(userRepository).findByEmail(email);
        doReturn(userReadDto).when(userReadMapper).map(user);

        var actualResult = userService.findByEmail(email);

        assertThat(actualResult).isNotNull();
        assertThat(actualResult).isEqualTo(userReadDto);
    }



    private User getUser() {
        return User.builder()
                .id(1)
                .personalInformation(new PersonalInformation(
                        "test",
                        "test",
                        "test@gmail.com",
                        "test",
                        LocalDate.now())
                )
                .password("test")
                .role(Role.ADMIN)
                .build();
    }

    private User getUser2() {
        return User.builder()
                .id(2)
                .personalInformation(new PersonalInformation(
                        "test2",
                        "test2",
                        "test2@gmail.com",
                        "test2",
                        LocalDate.now())
                )
                .password("test2")
                .role(Role.USER)
                .build();
    }

    private UserReadDto getUserReadDto() {
        return new UserReadDto(1, "test", "test", "test@gmail.com", "test", "test", LocalDate.now(), Role.ADMIN);
    }

    private UserReadDto getUserReadDto2() {
        return new UserReadDto(2, "test2", "test2", "test2@gmail.com", "test2", "test2", LocalDate.now(), Role.USER);
    }

    private UserCreateEditDto getUserCreateEditDto() {
        return new UserCreateEditDto("test", "test", "test@gmail.com", "test", "test", LocalDate.now(), Role.ADMIN);
    }
}
