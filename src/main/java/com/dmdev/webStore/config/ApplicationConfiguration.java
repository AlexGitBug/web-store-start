package com.dmdev.webStore.config;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.annotation.PreDestroy;
import javax.persistence.EntityManager;
import java.lang.reflect.Proxy;

import static org.springframework.context.annotation.ComponentScan.*;

@Configuration
@PropertySource("classpath:application.properties") //информация из property
@ComponentScan(basePackages = "com.dmdev.webStore")
//        , useDefaultFilters = false, includeFilters = {
//        @Filter(type = FilterType.ANNOTATION, value = ComponentType.class),
//        @Filter(type = FilterType.REGEX, pattern = "com\\..+Repository")
//обработка аннотаций, компонентов (сервисов, репозитории и тд) Можно сделать по дефолту true
public class ApplicationConfiguration {

    @Bean
    org.hibernate.cfg.Configuration configuration() {
        return new org.hibernate.cfg.Configuration();
    }

    @Bean
    SessionFactory sessionFactory(org.hibernate.cfg.Configuration configuration) {
        configuration.configure();
        return configuration.buildSessionFactory();
    }

    @Bean
    EntityManager entityManager(SessionFactory sessionFactory) {
        return (EntityManager) Proxy.newProxyInstance(SessionFactory.class.getClassLoader(), new Class[]{Session.class},
                (proxy, method, args) -> method.invoke(sessionFactory.getCurrentSession(), args));
    }

    @PreDestroy
    void closeSessionFactory() {
        sessionFactory().close();
    }
}



