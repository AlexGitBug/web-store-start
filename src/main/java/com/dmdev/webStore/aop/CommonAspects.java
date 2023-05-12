package com.dmdev.webStore.aop;

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
public class CommonAspects {

//    @Pointcut("com.dmdev.webStore.aop.CommonPointcuts.isServiceLayer() && args(..)")
//    public void hasEntryParams() {
//    }

    @Before(value = "com.dmdev.webStore.aop.CommonPointcuts.anyFindByIdServiceMethod() " +
            "&& args(id)" +
            "&& target(service))",
            argNames = "joinPoint,id,service")
    public void addBeforeLoggingFindById(JoinPoint joinPoint,
                                         Object id,
                                         Object service) {
        log.info("before -invoke findById method in class {}, id {}", service, id);
    }

    @Before(value = "com.dmdev.webStore.aop.CommonPointcuts.anyFindAllServiceMethod() " +
            "&& target(service)")
    public void addBeforeLoggingFindAll(JoinPoint joinPoint,
                                        Object service) {
        log.info("before -invoke findAll method in class {}", service);
    }

    @Before(value = "com.dmdev.webStore.aop.CommonPointcuts.anyCreateServiceMethod() " +
            "&& args(dto)" +
            "&& target(service)",
            argNames = "joinPoint,service,dto")
    public void addBeforeLoggingCreate(JoinPoint joinPoint,
                                       Object service,
                                       Object dto) {
        log.info("before -invoke create method in class {}, dto {}", service, dto);
    }

    @Before(value = "com.dmdev.webStore.aop.CommonPointcuts.anyDeleteServiceMethod() " +
            "&& args(id)" +
            "&& target(service)",
            argNames = "joinPoint,service,id")
    public void addBeforeLoggingDelete(JoinPoint joinPoint,
                                       Object service,
                                       Object id) {
        log.info("before -invoke delete method in class {}, id {}", service, id);
    }

    @Before(value = "com.dmdev.webStore.aop.CommonPointcuts.anyUpdateServiceMethod() " +
            "&& args(id, dto)" +
            "&& target(service)",
            argNames = "joinPoint,service,id,dto")
    public void addBeforeLoggingUpdate(JoinPoint joinPoint,
                                       Object service,
                                       Object id,
                                       Object dto) {
        log.info("before -invoke update method in class {}, id {}, dto {}", service, id, dto);
    }

    @AfterReturning(value = "com.dmdev.webStore.aop.CommonPointcuts.anyFindByIdServiceMethod() " +
            "&& target(service)",
            returning = "result",
            argNames = "result,service")
    public void addLoggingFindById(Object result,
                                   Object service) {
        log.info("After Returning - invoke findById method in class {}, result {}", service, result);
    }

    @AfterReturning(value = "com.dmdev.webStore.aop.CommonPointcuts.anyFindAllServiceMethod() " +
            "&& target(service)",
            returning = "result",
            argNames = "result,service")
    public void addLoggingFindAll(Object result,
                                  Object service) {
        log.info("After Returning - invoke findAll method in class {}, result {}", service, result);
    }

    @AfterReturning(value = "com.dmdev.webStore.aop.CommonPointcuts.anyDeleteServiceMethod() " +
            "&& target(service)",
            returning = "result",
            argNames = "result,service")
    public void addLoggingDelete(Object result,
                                 Object service) {
        log.info("After Returning - invoke delete method in class {}, result {}", service, result);
    }

    @AfterReturning(value = "com.dmdev.webStore.aop.CommonPointcuts.anyCreateServiceMethod() " +
            "&& target(service)",
            returning = "result",
            argNames = "result,service")
    public void addLoggingCreate(Object result,
                                 Object service) {
        log.info("After Returning - invoke create method in class {}, result {}", service, result);
    }

    @AfterReturning(value = "com.dmdev.webStore.aop.CommonPointcuts.anyUpdateServiceMethod() " +
            "&& target(service)",
            returning = "result",
            argNames = "result,service")
    public void addLoggingUpdate(Object result,
                                 Object service) {
        log.info("After Returning - invoke update method in class {}, result {}", service, result);
    }

}
