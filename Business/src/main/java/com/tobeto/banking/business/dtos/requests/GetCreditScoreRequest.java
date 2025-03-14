package com.tobeto.banking.business.dtos.requests;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Kredi skoru getirme isteği DTO'su
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetCreditScoreRequest {
    
    private Long id;
    private Long customerId;
    private Integer minScore;
    private Integer maxScore;
    private LocalDateTime calculationStartDate;
    private LocalDateTime calculationEndDate;
    private Boolean isValid;
} 