package com.tobeto.banking.core.crosscuttingconcerns.exceptions.httpProblemDetails;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * RFC 7807 standardına uygun problem detayları sınıfı
 * https://tools.ietf.org/html/rfc7807
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProblemDetails {
    private String title;
    private String detail;
    private String type;
    private int status;
    private Map<String, Object> extensions;
} 