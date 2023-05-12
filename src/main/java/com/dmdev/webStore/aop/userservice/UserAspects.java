package com.dmdev.webStore.aop.userservice;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class UserAspects {

    @Before(value = "com.dmdev.webStore.aop.userservice.UserPointcuts.anyFindByEmailServiceMethod() " +
            "&& args(email)" +
            "&& target(service))",
            argNames = "joinPoint,email,service")
    public void addBeforeLoggingFindByEmail(JoinPoint joinPoint,
                                         Object email,
                                         Object service) {
        log.info("before -invoke findByEmail method in class {}, id {}", service, email);
    }

    @AfterReturning(value = "com.dmdev.webStore.aop.userservice.UserPointcuts.anyFindByEmailServiceMethod() " +
            "&& target(service)",
            returning = "result",
            argNames = "result,service")
    public void addLoggingFindById(Object result,
                                   Object service) {
        log.info("After Returning - invoke findByEmail method in class {}, result {}", service, result);
    }
}
