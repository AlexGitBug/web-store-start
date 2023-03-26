package com.dmdev.webStore.config;



import com.dmdev.webStore.entity.Catalog;
import com.dmdev.webStore.entity.Order;
import com.dmdev.webStore.entity.Product;
import com.dmdev.webStore.entity.ShoppingCart;
import com.dmdev.webStore.entity.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Environment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.annotation.PreDestroy;
import javax.persistence.EntityManager;
import java.lang.reflect.Proxy;
import java.util.Properties;

@Configuration
@PropertySource("classpath:application.properties") //информация из property
@ComponentScan(basePackages = "com.dmdev.webStore")
//        , useDefaultFilters = false, includeFilters = {
//        @Filter(type = FilterType.ANNOTATION, value = ComponentType.class),
//        @Filter(type = FilterType.REGEX, pattern = "com\\..+Repository")
//обработка аннотаций, компонентов (сервисов, репозитории и тд) Можно сделать по дефолту true
public class ApplicationConfiguration extends AppConfigProperties{

    @Bean
    public org.hibernate.cfg.Configuration configuration() {
        return getNewConfiguration();
    }

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

//    private org.hibernate.cfg.Configuration getNewConfiguration() {
//        return new org.hibernate.cfg.Configuration()
//                .setProperty(Environment.URL, url)
//                .setProperty(Environment.DRIVER, driver)
//                .setProperty(Environment.DIALECT, dialect)
//                .setProperty(Environment.SHOW_SQL, showSQL)
//                .setProperty(Environment.FORMAT_SQL, formatSQL)
//                .setProperty(Environment.HBM2DDL_AUTO, createTable)
//                .setProperty(Environment.CURRENT_SESSION_CONTEXT_CLASS, thread)
//                .setProperty(Environment.USER, username)
//                .setProperty(Environment.PASS, password)
//                .setProperty(Environment.USE_SECOND_LEVEL_CACHE, secondLevelCache)
//                .setProperty(Environment.CACHE_REGION_FACTORY, region)
//                .setProperty(Environment.USE_QUERY_CACHE, cache)
//                .addAnnotatedClass(Order.class)
//                .addAnnotatedClass(Catalog.class)
//                .addAnnotatedClass(ShoppingCart.class)
//                .addAnnotatedClass(Product.class)
//                .addAnnotatedClass(User.class);
//    }
}



