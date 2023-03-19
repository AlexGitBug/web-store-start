package dao.repository;

import entity.User;
import entity.enums.Role;
import org.junit.jupiter.api.Test;
import util.TestCreateObjectForRepository;

import static org.assertj.core.api.Assertions.assertThat;

public class UserRepositoryIT extends ProductRepositoryIT {

    private final UserRepository userRepository = new UserRepository(User.class, session);

    @Test
    void deleteUser() {
        var user = TestCreateObjectForRepository.getUser();
        userRepository.save(user);

        userRepository.delete(user.getId());

        var actualResult = session.get(User.class, user.getId());
        assertThat(actualResult).isNull();
    }

    @Test
    void saveUser() {
        var user = TestCreateObjectForRepository.getUser();

        userRepository.save(user);

        var actualResult = session.get(User.class, user.getId());

        assertThat(actualResult).isEqualTo(user);
    }

    @Test
    void updateUser() {
        var user = TestCreateObjectForRepository.getUser();
        userRepository.save(user);

        var result = session.get(User.class, user.getId());
        result.setRole(Role.ADMIN);
        userRepository.update(user);

        var actualResult = session.get(User.class, user.getId());
        assertThat(actualResult).isEqualTo(user);
    }

    @Test
    void findByIdUser() {
        var user = TestCreateObjectForRepository.getUser();
        userRepository.save(user);

        var actualResult = userRepository.findById(user.getId());

        assertThat(actualResult).contains(user);
    }

}
