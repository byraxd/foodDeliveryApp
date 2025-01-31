package com.example.foodDeliveryApp.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(1)
@Slf4j
public class LoggingAspect {

    // Product service aspects
    @Before(value = "com.example.foodDeliveryApp.aspect.pointcuts.CommonPointcuts.AllPublicServiceMethods()")
    public void beforeMethodLogs(JoinPoint joinPoint) {
        log.info("Method {} in {} is started", joinPoint.getSignature().getName(), joinPoint.getTarget().getClass().getSimpleName());
    }

    @AfterReturning(value = "com.example.foodDeliveryApp.aspect.pointcuts.CommonPointcuts.AllPublicServiceMethods()", returning = "result")
    public void afterMethodSuccessLogs(JoinPoint joinPoint, Object result) {
        log.info("Method {} in {} done successfully, and result of method: {}", joinPoint.getSignature().getName(), joinPoint.getTarget().getClass().getSimpleName(), result);
    }
}
