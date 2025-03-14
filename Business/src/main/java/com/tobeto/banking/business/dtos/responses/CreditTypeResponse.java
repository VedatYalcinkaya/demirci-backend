package com.tobeto.banking.business.dtos.responses;

import com.tobeto.banking.entities.concretes.CreditType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * Kredi türü yanıt DTO'su
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreditTypeResponse {
    
    private Long id;
    private String name;
    private String description;
    private CreditType.CustomerType customerType;
    private BigDecimal interestRate;
    private BigDecimal minAmount;
    private BigDecimal maxAmount;
    private Integer minTerm;
    private Integer maxTerm;
    private Boolean isActive;
    private Long parentId;
    private String parentName;
    private List<CreditTypeResponse> subTypes;
} 