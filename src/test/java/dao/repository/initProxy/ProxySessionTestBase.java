package dao.repository.initProxy;

import com.dmdev.webStore.config.ApplicationConfiguration;
import lombok.RequiredArgsConstructor;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.persistence.EntityManager;

@RequiredArgsConstructor
public abstract class ProxySessionTestBase extends TestDelete {

    protected static AnnotationConfigApplicationContext applicationContext;
    protected static EntityManager entityManager;
    protected static SessionFactory sessionFactory;

    @BeforeAll
    static void init() {
        applicationContext = new AnnotationConfigApplicationContext(ApplicationConfiguration.class);
        entityManager = applicationContext.getBean(EntityManager.class);
        sessionFactory = applicationContext.getBean(SessionFactory.class);
    }

    @BeforeEach
    void beginSession() {
        entityManager.getTransaction().begin();
    }

    @AfterEach
    void closeSession() {
        entityManager.close();
    }

    @AfterAll
    static void closeSessionFactory() {
        sessionFactory.close();
    }

    @Override
    public void deleteAll(EntityManager entityManager) {
        super.deleteAll(entityManager);
    }
}
