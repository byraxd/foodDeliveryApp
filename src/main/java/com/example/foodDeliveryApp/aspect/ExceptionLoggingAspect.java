package com.example.foodDeliveryApp.aspect;

import com.example.foodDeliveryApp.aspect.pointcuts.CommonPointcuts;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(2)
@Slf4j
public class ExceptionLoggingAspect {

    @AfterThrowing(value = "com.example.foodDeliveryApp.aspect.pointcuts.CommonPointcuts.AllPublicServiceMethods()", throwing = "exception")
    public void afterThrowingMethodLogging(JoinPoint joinPoint, Exception exception) {
        log.info("Method {} in {} failed with exception: {}", joinPoint.getSignature().getName(), joinPoint.getTarget().getClass().getSimpleName(), exception.getMessage());
    }
}
