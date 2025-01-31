package com.example.foodDeliveryApp.aspect.pointcuts;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CommonPointcuts {

    @Pointcut("execution(public * com.example.foodDeliveryApp.*.service.impl.*.*(..)) && !execution(* com.example.foodDeliveryApp.jwt..*(..))")
    public void AllPublicServiceMethods(){}
}
