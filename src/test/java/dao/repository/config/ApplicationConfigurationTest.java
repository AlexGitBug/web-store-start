package dao.repository.config;


import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.persistence.EntityManager;
import java.lang.reflect.Proxy;


@Configuration
@ComponentScan(basePackages = "com.dmdev.webStore")
public class ApplicationConfigurationTest {

    @Bean
    public SessionFactory sessionFactory(org.hibernate.cfg.Configuration configuration) {
        configuration.configure();
        return configuration.buildSessionFactory();
    }
    @Bean
    public EntityManager entityManager(SessionFactory sessionFactory) {
        return (EntityManager) Proxy.newProxyInstance(EntityManager.class.getClassLoader(), new Class[]{EntityManager.class},
                (proxy, method, args) -> method.invoke(sessionFactory.getCurrentSession(), args));
    }
}

