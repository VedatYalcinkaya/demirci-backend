package com.tobeto.banking.business.dtos.requests;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Kredi onayı getirme isteği DTO'su
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetCreditApprovalRequest {
    
    private Long id;
    private Long creditApplicationId;
    private String approvedBy;
    private LocalDateTime approvalStartDate;
    private LocalDateTime approvalEndDate;
} 