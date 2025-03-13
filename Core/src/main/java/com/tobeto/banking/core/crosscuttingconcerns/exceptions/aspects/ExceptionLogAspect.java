package com.tobeto.banking.core.crosscuttingconcerns.exceptions.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Exception fırlatıldığında loglama yapan aspect sınıfı
 */
@Aspect
@Component
public class ExceptionLogAspect {
    private static final Logger logger = LoggerFactory.getLogger(ExceptionLogAspect.class);
    
    /**
     * Controller sınıflarında oluşan hataları yakalar ve loglar
     * @param joinPoint Metot çağrısı bilgisi
     * @param exception Fırlatılan exception
     */
    @AfterThrowing(
            pointcut = "execution(* com.tobeto.banking.*.api.controllers.*.*(..))",
            throwing = "exception"
    )
    public void logControllerExceptions(JoinPoint joinPoint, Exception exception) {
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        
        logger.error("Exception in {}.{}: {} - {}", 
                className, 
                methodName, 
                exception.getClass().getSimpleName(), 
                exception.getMessage());
        
        // Stack trace'i debug seviyesinde logla
        logger.debug("Exception stack trace:", exception);
    }
    
    /**
     * Service sınıflarında oluşan hataları yakalar ve loglar
     * @param joinPoint Metot çağrısı bilgisi
     * @param exception Fırlatılan exception
     */
    @AfterThrowing(
            pointcut = "execution(* com.tobeto.banking.*.business.concretes.*.*(..))",
            throwing = "exception"
    )
    public void logServiceExceptions(JoinPoint joinPoint, Exception exception) {
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        
        logger.error("Exception in service {}.{}: {} - {}", 
                className, 
                methodName, 
                exception.getClass().getSimpleName(), 
                exception.getMessage());
        
        // Stack trace'i debug seviyesinde logla
        logger.debug("Exception stack trace:", exception);
    }
} 