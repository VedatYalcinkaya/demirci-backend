package com.tobeto.banking.business.dtos.responses;

import com.tobeto.banking.core.entities.IDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Kredi skoru yanıt DTO'su
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreditScoreResponse implements IDto {
    
    private Long id;
    private Long customerId;
    private String customerEmail;
    private String customerType; // "INDIVIDUAL" veya "CORPORATE"
    private Integer score;
    private LocalDateTime calculationDate;
    private LocalDateTime expiryDate;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
} 