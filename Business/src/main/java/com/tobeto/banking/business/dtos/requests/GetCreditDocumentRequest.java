package com.tobeto.banking.business.dtos.requests;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Kredi belgesi getirme isteği DTO'su
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetCreditDocumentRequest {
    
    private Long id;
    private Long creditApplicationId;
    private String documentType;
    private LocalDateTime uploadStartDate;
    private LocalDateTime uploadEndDate;
} 