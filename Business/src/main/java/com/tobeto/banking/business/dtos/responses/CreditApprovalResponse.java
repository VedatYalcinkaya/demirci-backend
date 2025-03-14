package com.tobeto.banking.business.dtos.responses;

import com.tobeto.banking.core.entities.IDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Kredi onayı yanıt DTO'su
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreditApprovalResponse implements IDto {
    
    private Long id;
    private Long creditApplicationId;
    private String approvedBy;
    private LocalDateTime approvalDate;
    private String notes;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
} 