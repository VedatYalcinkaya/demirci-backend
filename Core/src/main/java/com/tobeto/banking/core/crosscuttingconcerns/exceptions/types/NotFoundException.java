package com.tobeto.banking.core.crosscuttingconcerns.exceptions.types;

/**
 * Kaynak bulunamama hatalarında fırlatılan exception
 */
public class NotFoundException extends RuntimeException {
    
    public NotFoundException(String message) {
        super(message);
    }
    
    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
} 