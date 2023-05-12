package com.dmdev.webStore.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CommonPointcuts {

    @Pointcut("@within(org.springframework.stereotype.Controller)")
    public void isControllerLayer() {
    }

    @Pointcut("within(com.dmdev.webStore.service.*Service)")
    public void isServiceLayer() {
    }

    @Pointcut("execution(public * com.dmdev.webStore.service.*Service.findById(*))")
    public void anyFindByIdServiceMethod(){
    }

    @Pointcut("execution(public * com.dmdev.webStore.service.*Service.findAll())")
    public void anyFindAllServiceMethod(){
    }

    @Pointcut("execution(public * com.dmdev.webStore.service.*Service.delete(*))")
    public void anyDeleteServiceMethod(){
    }

    @Pointcut("execution(public * com.dmdev.webStore.service.*Service.create(*))")
    public void anyCreateServiceMethod(){
    }

    @Pointcut("execution(public * com.dmdev.webStore.service.*Service.update(*,*))")
    public void anyUpdateServiceMethod(){
    }

}
