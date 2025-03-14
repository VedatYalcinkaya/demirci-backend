package com.tobeto.banking.business.dtos.requests;

import com.tobeto.banking.entities.concretes.CreditApplication;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Kredi başvurusu getirme isteği DTO'su
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetCreditApplicationRequest {
    
    private Long id;
    private Long customerId;
    private Long creditTypeId;
    private CreditApplication.ApplicationStatus status;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
} 