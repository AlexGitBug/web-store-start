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
    @Value("${connection.driver_class}")
    private String driver;
    @Value("${hibernate.dialect}")
    private String dialect;
    @Value("${show_sql}")
    private String showSQL;
    @Value("${format_sql}")
    private String formatSQL;
    @Value("${hibernate.hbm2ddl.auto}")
    private String createTable;

    @Value("${hibernate.cache.use_second_level_cache}")
    private String secondLevelCache;

    @Value("${hibernate.cache.region.factory_class}")
    private String region;

    @Value("${hibernate.cache.use_query_cache}")
    private String cache;

    @Value("${hibernate.current_session_context_class}")
    private String thread;

    @Value("${connection.username}")
    private String username;

    @Value("${connection.password}")
    private String password;

    public org.hibernate.cfg.Configuration getNewConfiguration() {
        return new org.hibernate.cfg.Configuration()
                .setProperty(Environment.URL, url)
                .setProperty(Environment.DRIVER, driver)
                .setProperty(Environment.DIALECT, dialect)
                .setProperty(Environment.SHOW_SQL, showSQL)
                .setProperty(Environment.FORMAT_SQL, formatSQL)
                .setProperty(Environment.HBM2DDL_AUTO, createTable)
                .setProperty(Environment.CURRENT_SESSION_CONTEXT_CLASS, thread)
                .setProperty(Environment.USER, username)
                .setProperty(Environment.PASS, password)
                .setProperty(Environment.USE_SECOND_LEVEL_CACHE, secondLevelCache)
                .setProperty(Environment.CACHE_REGION_FACTORY, region)
                .setProperty(Environment.USE_QUERY_CACHE, cache)
                .addAnnotatedClass(Order.class)
                .addAnnotatedClass(Catalog.class)
                .addAnnotatedClass(ShoppingCart.class)
                .addAnnotatedClass(Product.class)
                .addAnnotatedClass(User.class);
    }
}
