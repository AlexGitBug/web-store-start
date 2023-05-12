package com.dmdev.webStore.aop.userservice;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class UserPointcuts {

    @Pointcut("execution(public * com.dmdev.webStore.service.UserService.findByEmail(*))")
    public void anyFindByEmailServiceMethod(){
    }
}
