package com.tobeto.banking.core.crosscuttingconcerns.exceptions.types;

/**
 * Yetkilendirme hatalarında fırlatılan exception
 */
public class AuthorizationException extends RuntimeException {
    
    public AuthorizationException(String message) {
        super(message);
    }
    
    public AuthorizationException(String message, Throwable cause) {
        super(message, cause);
    }
} 