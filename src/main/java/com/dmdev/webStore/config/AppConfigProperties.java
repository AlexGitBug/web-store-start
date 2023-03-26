package com.dmdev.webStore.config;

import com.dmdev.webStore.entity.Catalog;
import com.dmdev.webStore.entity.Order;
import com.dmdev.webStore.entity.Product;
import com.dmdev.webStore.entity.ShoppingCart;
import com.dmdev.webStore.entity.User;
import org.hibernate.cfg.Environment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
public class AppConfigProperties {

    @Value("${connection.url}")
    private String url;
    @Value("${connection.username}")
    private String username;
    @Value("${connection.password}")
    private String password;
    @Value("${connection.driver_class}")
    private String driverClass;
    @Value("${show_sql}")
    private String showSql;
    @Value("${format_sql}")
    private String formatSql;
    @Value("${hibernate.dialect}")
    private String dialect;
    @Value("${hibernate.hbm2ddl.auto}")
    private String hbm2ddlAuto;

    @Value("${hibernate.cache.use_second_level_cache}")
    private String useSecondLevelCache;

    @Value("${hibernate.cache.region.factory_class}")
    private String regionFactoryClass;

    @Value("${hibernate.cache.use_query_cache}")
    private String useQueryCache;

    @Value("${hibernate.current_session_context_class}")
    private String currentSessionContextClass;



    public org.hibernate.cfg.Configuration getNewConfiguration() {
        return new org.hibernate.cfg.Configuration()
                .setProperty(Environment.URL, url)
                .setProperty(Environment.DRIVER, driverClass)
                .setProperty(Environment.DIALECT, dialect)
                .setProperty(Environment.SHOW_SQL, showSql)
                .setProperty(Environment.FORMAT_SQL, formatSql)
                .setProperty(Environment.HBM2DDL_AUTO, hbm2ddlAuto)
                .setProperty(Environment.CURRENT_SESSION_CONTEXT_CLASS, currentSessionContextClass)
                .setProperty(Environment.USER, username)
                .setProperty(Environment.PASS, password)
                .setProperty(Environment.USE_SECOND_LEVEL_CACHE, useSecondLevelCache)
                .setProperty(Environment.CACHE_REGION_FACTORY, regionFactoryClass)
                .setProperty(Environment.USE_QUERY_CACHE, useQueryCache)
                .addAnnotatedClass(Order.class)
                .addAnnotatedClass(Catalog.class)
                .addAnnotatedClass(ShoppingCart.class)
                .addAnnotatedClass(Product.class)
                .addAnnotatedClass(User.class);
    }
}
