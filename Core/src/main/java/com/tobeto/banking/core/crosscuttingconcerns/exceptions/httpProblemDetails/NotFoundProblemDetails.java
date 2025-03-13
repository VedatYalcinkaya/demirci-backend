package com.tobeto.banking.core.crosscuttingconcerns.exceptions.httpProblemDetails;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Kaynak bulunamama hatalarını temsil eden problem detayları sınıfı
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class NotFoundProblemDetails extends ProblemDetails {
    public NotFoundProblemDetails() {
        setTitle("Resource Not Found");
        setType("https://tobeto.com/exceptions/not-found");
        setStatus(404);
    }
    
    public NotFoundProblemDetails(String detail) {
        this();
        setDetail(detail);
    }
} 