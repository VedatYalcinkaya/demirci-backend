package com.tobeto.banking.core.crosscuttingconcerns.exceptions.types;

/**
 * İş kuralı ihlallerinde fırlatılan exception
 */
public class BusinessException extends RuntimeException {
    
    public BusinessException(String message) {
        super(message);
    }
    
    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }
} 