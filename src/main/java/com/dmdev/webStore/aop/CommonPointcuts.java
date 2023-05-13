package com.dmdev.webStore.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Aspect
@Component
public class CommonPointcuts {

    @Pointcut("@within(org.springframework.stereotype.Controller)")
    public void isControllerLayer() {
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


    @Pointcut("@within(org.springframework.stereotype.Service)")
    public void isServiceLayer() {
    }

    @Pointcut("execution(public * com.dmdev.webStore.service.*Service.*(..)) && isServiceLayer()")
    public void anyServiceMethod(){
    }

}
