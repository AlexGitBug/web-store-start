package unUsedCode.crudIT.entity;

import com.dmdev.webStore.entity.User;
import com.dmdev.webStore.entity.embeddable.PersonalInformation;
import com.dmdev.webStore.entity.enums.Role;
import org.junit.jupiter.api.Test;
import unUsedCode.dao.util.HibernateUtil;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class UserTest {

    @Test
    void checkUserSaveAndGet() {

        try (var sessionFactory = HibernateUtil.buildSessionFactory();
             var session = sessionFactory.openSession()) {
            var user = getUser();
            session.beginTransaction();
            session.save(user);

            session.getTransaction().commit();

            var actualResult = session.get(User.class, user.getId());
            assertThat(actualResult).isEqualTo(user);
        }
    }

    private User getUser() {
        return User.builder()
                .personalInformation(PersonalInformation.builder()
                        .firstName("Test")
                        .lastName("Testov")
                        .email("test@mail.ru")
                        .password("test")
                        .telephone("test")
                        .birthDate(LocalDate.of(2020, 2, 2))
                        .build())
                .role(Role.USER)
                .build();
    }
}