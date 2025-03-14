package com.tobeto.banking.business.dtos.requests;

import com.tobeto.banking.entities.concretes.CreditType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Kredi türü filtreleme isteği DTO'su
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetCreditTypeRequest {
    
    private Long id;
    private String name;
    private CreditType.CustomerType customerType;
    private Long parentId;
    private Boolean isActive;
} 