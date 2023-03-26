package dao.repository.initProxy;

import dao.repository.config.ApplicationConfigurationTest;
import lombok.RequiredArgsConstructor;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@RequiredArgsConstructor
public abstract class ProxySessionTestBase extends TestDelete {

    protected static AnnotationConfigApplicationContext applicationContext;
    protected static EntityManager entityManager;
    protected static SessionFactory sessionFactory;

    @BeforeAll
    static void init() {
        applicationContext = new AnnotationConfigApplicationContext(ApplicationConfigurationTest.class);
        entityManager = applicationContext.getBean(EntityManager.class);
        sessionFactory = applicationContext.getBean(SessionFactory.class);
    }

    @BeforeEach
    void beginSession() {
        deleteAll(entityManager);
    }

    @AfterEach
    void commitSession() {
//        entityManager.getTransaction().commit();
        entityManager.getTransaction().rollback();
    }

    @AfterAll
    static void closeSessionFactory() {
        sessionFactory.close();
    }
}
