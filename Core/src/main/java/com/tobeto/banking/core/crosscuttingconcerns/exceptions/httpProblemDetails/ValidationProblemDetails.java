package com.tobeto.banking.core.crosscuttingconcerns.exceptions.httpProblemDetails;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Map;

/**
 * Validasyon hatalarını temsil eden problem detayları sınıfı
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ValidationProblemDetails extends ProblemDetails {
    private Map<String, String> validationErrors;
    
    public ValidationProblemDetails() {
        setTitle("Validation Error");
        setType("https://tobeto.com/exceptions/validation");
        setStatus(400);
    }
    
    public ValidationProblemDetails(String detail, Map<String, String> validationErrors) {
        this();
        setDetail(detail);
        this.validationErrors = validationErrors;
    }
} 