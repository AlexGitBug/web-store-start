package com.dmdev.webStore.http.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@Slf4j
@ControllerAdvice(basePackages = "com.dmdev.webStore.http.controller")
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

//    @ExceptionHandler(Exception.class)
//    public String handleExceptions(Exception exception) {
//        log.error("Failed to return response", exception);
//        return "error/error500";
//    }
}
