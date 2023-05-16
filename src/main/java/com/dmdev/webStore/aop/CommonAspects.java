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

    @Before(value = "com.dmdev.webStore.aop.CommonPointcuts.anyServiceMethod()")
    public void addBefore(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        Object[] args = joinPoint.getArgs();
        log.info("before -invoke method [{}] in class [{}] with args [{}]", methodName, className, args);
    }

    @AfterReturning(value = "com.dmdev.webStore.aop.CommonPointcuts.anyServiceMethod()", returning = "result")
    public void addAfterLogging(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        log.info("after -invoke method [{}] in class [{}] with result [{}]", methodName, className, result);
    }
}