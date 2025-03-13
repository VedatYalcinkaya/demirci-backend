package com.tobeto.banking.core.crosscuttingconcerns.exceptions.httpProblemDetails;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Sunucu hatalarını temsil eden problem detayları sınıfı
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class InternalServerErrorProblemDetails extends ProblemDetails {
    public InternalServerErrorProblemDetails() {
        setTitle("Internal Server Error");
        setType("https://tobeto.com/exceptions/internal");
        setStatus(500);
    }
    
    public InternalServerErrorProblemDetails(String detail) {
        this();
        setDetail(detail);
    }
} 