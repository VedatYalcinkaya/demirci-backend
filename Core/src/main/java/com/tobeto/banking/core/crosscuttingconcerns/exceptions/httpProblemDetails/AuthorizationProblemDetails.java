package com.tobeto.banking.core.crosscuttingconcerns.exceptions.httpProblemDetails;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Yetkilendirme hatalarını temsil eden problem detayları sınıfı
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AuthorizationProblemDetails extends ProblemDetails {
    public AuthorizationProblemDetails() {
        setTitle("Authorization Error");
        setType("https://tobeto.com/exceptions/authorization");
        setStatus(401);
    }
    
    public AuthorizationProblemDetails(String detail) {
        this();
        setDetail(detail);
    }
} 