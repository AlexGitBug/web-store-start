package dao.repository;

import com.dmdev.webStore.dao.repository.UserRepository;
import com.dmdev.webStore.entity.User;
import com.dmdev.webStore.entity.enums.Role;
import dao.repository.initProxy.ProxySessionTestBase;
import org.junit.jupiter.api.Test;
import util.TestCreateObjectForRepository;

import static org.assertj.core.api.Assertions.assertThat;

public class UserRepositoryIT extends ProxySessionTestBase {

    private final UserRepository userRepository = applicationContext.getBean(UserRepository.class);

    @Test
    void deleteUser() {
        var user = TestCreateObjectForRepository.getUser();
        userRepository.save(user);

        userRepository.delete(user);

        var actualResult = entityManager.find(User.class, user.getId());
        assertThat(actualResult).isNull();
    }

    @Test
    void saveUser() {
        var user = TestCreateObjectForRepository.getUser();

        userRepository.save(user);

        var actualResult = entityManager.find(User.class, user.getId());

        assertThat(actualResult).isEqualTo(user);
    }

    @Test
    void updateUser() {
        var user = TestCreateObjectForRepository.getUser();
        userRepository.save(user);

        var result = entityManager.find(User.class, user.getId());
        result.setRole(Role.ADMIN);
        userRepository.update(user);

        var actualResult = entityManager.find(User.class, user.getId());
        assertThat(actualResult).isEqualTo(user);
    }

    @Test
    void findByIdUser() {
        var user = TestCreateObjectForRepository.getUser();
        userRepository.save(user);

        var actualResult = entityManager.find(User.class, user.getId());

        assertThat(actualResult).isEqualTo(user);
    }

}
