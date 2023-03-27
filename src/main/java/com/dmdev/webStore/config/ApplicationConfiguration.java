package com.dmdev.webStore.config;



import org.hibernate.SessionFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.persistence.EntityManager;
import java.lang.reflect.Proxy;

@Configuration
@PropertySource("classpath:application.properties") //информация из property
@ComponentScan(basePackages = "com.dmdev.webStore")
public class ApplicationConfiguration extends Context{
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



