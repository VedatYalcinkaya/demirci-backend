package com.tobeto.banking.business.dtos.responses;

import com.tobeto.banking.core.entities.IDto;
import com.tobeto.banking.entities.concretes.CreditApplication;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Kredi başvurusu yanıt DTO'su
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreditApplicationResponse implements IDto {
    
    private Long id;
    private Long customerId;
    private String customerEmail;
    private String customerType; // "INDIVIDUAL" veya "CORPORATE"
    private Long creditTypeId;
    private String creditTypeName;
    private BigDecimal amount;
    private Integer term;
    private BigDecimal interestRate;
    private BigDecimal monthlyPayment;
    private BigDecimal totalPayment;
    private LocalDateTime applicationDate;
    private CreditApplication.ApplicationStatus status;
    private LocalDateTime approvalDate;
    private String rejectionReason;
    private List<CreditDocumentResponse> documents;
    private CreditApprovalResponse approval;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
} 