package dao.repository.integration;

import com.dmdev.webStore.dao.repository.UserRepository;
import com.dmdev.webStore.entity.User;
import com.dmdev.webStore.entity.enums.Role;
import dao.repository.util.TestDelete;
import dao.repository.integration.annotation.IT;
import dao.repository.util.TestDataImporter;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import dao.repository.util.MocksForRepository;

import javax.persistence.EntityManager;
import java.time.LocalDate;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@IT
@RequiredArgsConstructor
public class UserRepositoryIT {

    private final UserRepository userRepository;

    private final EntityManager entityManager;

    @Test
    void deleteUser() {
        var user = MocksForRepository.getUser();
        userRepository.save(user);

        userRepository.delete(user);

        assertThat(userRepository.findById(user.getId())).isEmpty();

//        var actualResult = entityManager.find(User.class, user.getId());
//        assertThat(actualResult).isNull();
    }

    @Test
    void saveUser() {
        var user = MocksForRepository.getUser();

        userRepository.save(user);

        assertThat(userRepository.findById(user.getId())).isNotEmpty();

    }

    @Test
    void updateUser() {
        var user = MocksForRepository.getUser();
        userRepository.save(user);

        var result = entityManager.find(User.class, user.getId());
        result.setRole(Role.ADMIN);
        userRepository.update(result);

        var actualResult = userRepository.findById(user.getId());
        assertThat(actualResult).contains(user);
    }

    @Test
    void findByIdUser() {
        var user = MocksForRepository.getUser();
        userRepository.save(user);

        var actualResult = userRepository.findById(user.getId());
        assertThat(actualResult).isPresent();
        assertThat(actualResult).contains(user);
    }

    @Test
    void findUsersWhoMadeAnOrderAtSpecificTime() {
        TestDataImporter.importData(entityManager);

        var results = userRepository.findUsersWhoMadeOrderSpecificTime(LocalDate.of(2022, 10, 10), LocalDate.of(2023, 12, 12));

        assertThat(results).hasSize(3);

        var actualResult = results.stream().map(user -> user.getPersonalInformation().getEmail()).collect(toList());
        assertThat(actualResult).contains("ivan@gmail.com", "sveta@gmail.com", "vasia@gmail.com");
    }
}
