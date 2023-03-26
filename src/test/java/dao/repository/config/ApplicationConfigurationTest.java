package dao.repository.config;


import com.dmdev.webStore.config.AppConfigProperties;
import com.dmdev.webStore.entity.*;
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

@Configuration
@ComponentScan(basePackages = "com.dmdev.webStore")
//        , useDefaultFilters = false, includeFilters = {
//        @Filter(type = FilterType.ANNOTATION, value = ComponentType.class),
//        @Filter(type = FilterType.REGEX, pattern = "com\\..+Repository")
//обработка аннотаций, компонентов (сервисов, репозитории и тд) Можно сделать по дефолту true
public class ApplicationConfigurationTest extends AppConfigPropertiesTest {
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
}


