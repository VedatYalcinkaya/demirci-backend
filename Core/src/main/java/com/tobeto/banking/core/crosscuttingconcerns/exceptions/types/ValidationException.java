package com.tobeto.banking.core.crosscuttingconcerns.exceptions.types;

import lombok.Getter;

import java.util.Map;

/**
 * Validasyon hatalarında fırlatılan exception
 */
@Getter
public class ValidationException extends RuntimeException {
    private final Map<String, String> validationErrors;
    
    public ValidationException(String message, Map<String, String> validationErrors) {
        super(message);
        this.validationErrors = validationErrors;
    }
    
    public ValidationException(String message, Map<String, String> validationErrors, Throwable cause) {
        super(message, cause);
        this.validationErrors = validationErrors;
    }
} 