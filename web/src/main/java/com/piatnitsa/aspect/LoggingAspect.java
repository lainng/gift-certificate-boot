package com.piatnitsa.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * This class used to log the application.
 */
@Aspect
@Component
public class LoggingAspect {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Pointcut("within (com.piatnitsa..*) ")
    public void anyClassMethods() {}

    @Before("anyClassMethods()")
    public void logBeforeExecutionTime(JoinPoint jp) {
        LOGGER.debug("Start executing method: " + jp.getSignature()
                + " with arguments " + Arrays.asList(jp.getArgs()));
    }

    @AfterReturning(value = "anyClassMethods()", returning = "object")
    public void logAfterReturningValue(JoinPoint jp, Object object) {
        LOGGER.debug("Method " + jp.getSignature()
                + " successfully executed and returned the value [" + object + "]");
    }

    @AfterThrowing(value = "anyClassMethods()", throwing = "ex")
    public void logAfterThrowingException(JoinPoint jp, Exception ex) {
        LOGGER.error("Method " + jp.getSignature() + " throws " + ex);
    }
}
