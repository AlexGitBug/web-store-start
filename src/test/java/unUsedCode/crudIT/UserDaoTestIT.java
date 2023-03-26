package unUsedCode.crudIT;

import com.dmdev.webStore.entity.User;
import com.dmdev.webStore.entity.embeddable.PersonalInformation;
import com.dmdev.webStore.entity.enums.Role;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static unUsedCode.dao.util.SessionUtil.closeTransactionSession;
import static unUsedCode.dao.util.SessionUtil.openTransactionSession;

class UserDaoTestIT {

    @Test
    void findAll() {
        try (Session session = openTransactionSession()) {
            var user = getUser();
            var userTest = getUserTest();
            session.save(user);
            session.save(userTest);
            closeTransactionSession();


            var actualResult = session.createQuery("select u from User u ", User.class)
                    .list();

            assertThat(actualResult).containsExactlyInAnyOrder(user, userTest);
        }
    }

    @Test
    void findById() {
        try (Session session = openTransactionSession()) {
            var user = getUser();

            session.save(user);
            closeTransactionSession();

            var actualResult = session.get(User.class, user.getId());
            assertThat(actualResult).isEqualTo(user);
        }
    }

    @Test
    void delete() {
        try (Session session = openTransactionSession()) {
            var user = getUser();
            session.save(user);
            closeTransactionSession();

            session.delete(user);

            var actualResult = session.get(User.class, user.getId());
            assertThat(actualResult).isNull();
        }
    }

    @Test
    void update() {
        try (Session session = openTransactionSession()) {
            var user = getUser();
            session.save(user);
            closeTransactionSession();
            var updatedUser = session.get(User.class, user.getId());
            updatedUser.setRole(Role.ADMIN);

            session.update(user);

            var actualResult = session.get(User.class, user.getId());
            assertThat(actualResult).isEqualTo(user);

        }
    }

    @Test
    void save() {
        try (Session session = openTransactionSession()) {
            var user = getUser();

            session.save(user);

            closeTransactionSession();

            var actualResult = session.get(User.class, user.getId());
            assertNotNull(actualResult.getId());
        }
    }

    private static User getUser() {
        return User.builder().personalInformation
                        (PersonalInformation.builder()
                                .firstName("Test")
                                .lastName("Test")
                                .email("test@gmail.ru")
                                .password("test")
                                .telephone("test")
                                .birthDate(LocalDate.of(2023, 4, 3))
                                .build())
                .role(Role.USER)
                .build();
    }

    private static User getUserTest() {
        return User.builder().personalInformation
                        (PersonalInformation.builder()
                                .firstName("Test1")
                                .lastName("Test1")
                                .email("test1@gmail.ru")
                                .password("test1")
                                .telephone("test1")
                                .birthDate(LocalDate.of(2023, 4, 3))
                                .build())
                .role(Role.USER)
                .build();
    }
}