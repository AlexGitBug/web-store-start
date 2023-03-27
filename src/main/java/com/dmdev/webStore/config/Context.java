package com.dmdev.webStore.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@RequiredArgsConstructor
public abstract class Context {

    protected static AnnotationConfigApplicationContext applicationContext = new
            AnnotationConfigApplicationContext(HibernateConfig.class);

}
