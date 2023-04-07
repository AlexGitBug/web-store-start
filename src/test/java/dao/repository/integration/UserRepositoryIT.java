package dao.repository.integration;

import com.dmdev.webStore.dao.repository.UserRepository;
import com.dmdev.webStore.entity.enums.Role;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import dao.repository.util.MocksForRepository;

import java.time.LocalDate;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;


@RequiredArgsConstructor
public class UserRepositoryIT extends IntegrationTestBase {

    private final String MAIL_SVETA = "ivan@gmail.com";
    private final String MAIL_IVAN = "sveta@gmail.com";
    private final String MAIL_VASIA = "vasia@gmail.com";
    private final UserRepository userRepository;

    @Test
    void deleteUser() {
        var user = MocksForRepository.getUser();
        userRepository.save(user);

        userRepository.delete(user);

        assertThat(userRepository.findById(user.getId())).isEmpty();
    }

    @Test
    void saveUser() {
        var user = MocksForRepository.getUser();

        userRepository.save(user);

        assertThat(user.getId()).isNotNull();
    }

    @Test
    void updateUser() {
        var user = MocksForRepository.getUser();
        userRepository.save(user);

        var updatedUser = userRepository.findById(user.getId());
        updatedUser.ifPresent(it -> it.setRole(Role.ADMIN));
        userRepository.saveAndFlush(updatedUser.get());

        var actualResult = userRepository.findById(user.getId());
        assertThat(actualResult).isPresent();
        assertThat(actualResult.get().getRole())
                .isEqualTo(user.getRole());
    }

    @Test
    void findByIdUser() {
        var user = MocksForRepository.getUser();
        userRepository.save(user);

        var actualResult = userRepository.findById(user.getId());

        assertThat(actualResult).isPresent();
        assertThat(actualResult.get()).isEqualTo(user);
    }

    @Test
    void findAllUserIT() {
        var actualResult = userRepository.findAll();

        assertThat(actualResult).hasSize(9);

    }

    @Test
    void findUsersWhoMadeAnOrderAtSpecificTime() {
        var results = userRepository
                .findUsersWhoMadeOrderSpecificTime(LocalDate.of(2022, 10, 10),
                        LocalDate.of(2023, 12, 12));
        assertThat(results).hasSize(3);

        var emails = results.stream()
                .map(user -> user.getPersonalInformation().getEmail())
                .collect(toList());

        assertAll(
                () -> assertThat(results).hasSize(3),
                () -> assertThat(emails).containsExactlyInAnyOrder(MAIL_IVAN, MAIL_SVETA, MAIL_VASIA)
        );
    }
}
