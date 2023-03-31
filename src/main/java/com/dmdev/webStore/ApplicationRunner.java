package com.dmdev.webStore;

import com.dmdev.webStore.config.DataBaseYmlProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@ConfigurationPropertiesScan
public class ApplicationRunner{

    public static void main(String[] args) {

        var context = SpringApplication.run(ApplicationRunner.class, args);
        System.out.println(context.getBean(DataBaseYmlProperties.class));
    }
}
