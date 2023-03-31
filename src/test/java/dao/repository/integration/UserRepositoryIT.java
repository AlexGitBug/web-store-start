package dao.repository.integration;

import com.dmdev.webStore.dao.repository.UserRepository;
import com.dmdev.webStore.entity.User;
import com.dmdev.webStore.entity.enums.Role;
import dao.repository.initProxy.TestDelete;
import dao.repository.integration.annotation.IT;
import dao.repository.util.TestDataImporter;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import dao.repository.util.MocksForRepository;

import javax.persistence.EntityManager;
import java.time.LocalDate;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
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
        userRepository.update(user);

        var actualResult = entityManager.find(User.class, user.getId());
        assertThat(actualResult).isEqualTo(user);

    }

    @Test
    void findByIdUser() {
        var user = MocksForRepository.getUser();
        userRepository.save(user);

        var actualResult = userRepository.findById(user.getId());

        assertThat(actualResult).contains(user);

    }

    @Test
    void findUsersWhoMadeAnOrderAtSpecificTime() {
        entityManager.clear();
        TestDelete.deleteAll(entityManager);
        TestDataImporter.importData(entityManager);
        
        var results = userRepository.findUsersWhoMadeOrderSpecificTime(LocalDate.of(2022, 10, 10), LocalDate.of(2023, 12, 12));

        assertThat(results).hasSize(3);

        var actualResult = results.stream().map(User::getId).collect(toList());
        assertThat(actualResult).contains(3,4,9);
    }



}
