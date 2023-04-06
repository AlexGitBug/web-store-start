package dao.repository.integration;

import com.dmdev.webStore.dao.repository.UserRepository;
import com.dmdev.webStore.entity.User;
import com.dmdev.webStore.entity.embeddable.PersonalInformation;
import com.dmdev.webStore.entity.enums.Role;
import dao.repository.util.TestDelete;
import dao.repository.integration.annotation.IT;
import dao.repository.util.TestDataImporter;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import dao.repository.util.MocksForRepository;

import javax.persistence.EntityManager;
import java.time.LocalDate;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@IT
@RequiredArgsConstructor
public class UserRepositoryIT {

    private final String MAIL_SVETA = "ivan@gmail.com";
    private final String MAIL_IVAN = "sveta@gmail.com";
    private final String MAIL_VASIA = "vasia@gmail.com";
    private final UserRepository userRepository;
    private final EntityManager entityManager;

    @BeforeEach
    void deleteAllData() {
        TestDelete.deleteAll(entityManager);
    }

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
        userRepository.save(User.builder().
                personalInformation(PersonalInformation.builder().email(MAIL_IVAN).build())
                .build());
        userRepository.save(User.builder().
                personalInformation(PersonalInformation.builder().email(MAIL_SVETA).build())
                .build());

        var actualResult = userRepository.findAll();

        var users = actualResult.stream()
                .map(User::getPersonalInformation)
                .map(PersonalInformation::getEmail)
                .toList();
        assertAll(
                () -> assertThat(actualResult).hasSize(2),
                () -> assertThat(users).containsExactlyInAnyOrder(
                        MAIL_IVAN, MAIL_SVETA
                )
        );
    }

    @Test
    void findUsersWhoMadeAnOrderAtSpecificTime() {
        TestDataImporter.importData(entityManager);

        var results = userRepository
                .findUsersWhoMadeOrderSpecificTime(LocalDate.of(2022, 10, 10),
                                                    LocalDate.of(2023, 12, 12));

        var actualResult = results.stream()
                .map(user -> user.getPersonalInformation().getEmail())
                .collect(toList());

        assertAll(
                () -> assertThat(results).hasSize(3),
                () -> assertThat(actualResult).containsExactlyInAnyOrder(MAIL_IVAN, MAIL_SVETA, MAIL_VASIA)
        );
    }
}
