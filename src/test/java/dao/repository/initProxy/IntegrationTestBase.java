package dao.repository.initProxy;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;

import javax.persistence.EntityManager;

@RequiredArgsConstructor
public abstract class IntegrationTestBase {

    private final EntityManager entityManager;

    @BeforeEach
    void beginSession() {
        TestDelete.deleteAll(entityManager);
    }

}
