package dao.repository;

import com.dmdev.webStore.dao.repository.UserRepository;
import com.dmdev.webStore.entity.User;
import com.dmdev.webStore.entity.enums.Role;
import dao.repository.initProxy.ProxySessionTestBase;
import dao.repository.util.TestDataImporter;
import org.junit.jupiter.api.Test;
import dao.repository.util.MocksForRepository;

import java.time.LocalDate;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

public class UserRepositoryIT extends ProxySessionTestBase {

    private final UserRepository userRepository = applicationContext.getBean(UserRepository.class);

    @Test
    void deleteUser() {
        entityManager.getTransaction().begin();
        var user = MocksForRepository.getUser();
        userRepository.save(user);

        userRepository.delete(user);

        var actualResult = entityManager.find(User.class, user.getId());
        assertThat(actualResult).isNull();
        entityManager.getTransaction().commit();
    }

    @Test
    void saveUser() {
        entityManager.getTransaction().begin();
        var user = MocksForRepository.getUser();

        userRepository.save(user);

        var actualResult = entityManager.find(User.class, user.getId());

        assertThat(actualResult).isEqualTo(user);
        entityManager.getTransaction().commit();
    }

    @Test
    void updateUser() {
        entityManager.getTransaction().begin();
        var user = MocksForRepository.getUser();
        userRepository.save(user);

        var result = entityManager.find(User.class, user.getId());
        result.setRole(Role.ADMIN);
        userRepository.update(user);

        var actualResult = entityManager.find(User.class, user.getId());
        assertThat(actualResult).isEqualTo(user);
        entityManager.getTransaction().commit();
    }

    @Test
    void findByIdUser() {
        entityManager.getTransaction().begin();
        var user = MocksForRepository.getUser();
        userRepository.save(user);

        var actualResult = entityManager.find(User.class, user.getId());

        assertThat(actualResult).isEqualTo(user);
        entityManager.getTransaction().commit();
    }

    @Test
    void findUsersWhoMadeAnOrderAtSpecificTime() {
        TestDataImporter.importData(sessionFactory);
        entityManager.getTransaction().begin();

        var results = userRepository.findUsersWhoMadeOrderSpecificTime(LocalDate.of(2022, 10, 10), LocalDate.of(2023, 12, 12));
        entityManager.getTransaction().commit();
        assertThat(results).hasSize(3);

        var actualResult = results.stream().map(User::getId).collect(toList());
        assertThat(actualResult).contains(3,4,9);
    }



}
