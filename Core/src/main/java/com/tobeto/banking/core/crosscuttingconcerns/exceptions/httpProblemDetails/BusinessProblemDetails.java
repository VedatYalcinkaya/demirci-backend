package com.tobeto.banking.core.crosscuttingconcerns.exceptions.httpProblemDetails;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * İş kuralı hatalarını temsil eden problem detayları sınıfı
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BusinessProblemDetails extends ProblemDetails {
    public BusinessProblemDetails() {
        setTitle("Business Rule Violation");
        setType("https://tobeto.com/exceptions/business");
        setStatus(400);
    }
    
    public BusinessProblemDetails(String detail) {
        this();
        setDetail(detail);
    }
} 