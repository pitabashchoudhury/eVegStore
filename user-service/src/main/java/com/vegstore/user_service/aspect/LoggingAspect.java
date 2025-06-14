package com.vegstore.user_service.aspect;

import com.vegstore.user_service.log.LogPublisher;
import com.vegstore.user_service.dao.LogMessage;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

@Aspect
@Component

public class LoggingAspect {

    private final LogPublisher logPublisher;

    public LoggingAspect(LogPublisher logPublisher) {
        this.logPublisher = logPublisher;
    }

    @Pointcut("execution(* com.vegstore.user_service.controller..*(..))")
    public void logRequest() {
        // Pointcut method - no implementation needed

    }

    @Before("logRequest()")
    public void logServiceMethods(JoinPoint joinPoint) {
        System.out.println("LOGGONG");
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        LogMessage message = new LogMessage(className, methodName);
        logPublisher.publish(message);


    }
}
