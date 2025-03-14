package com.tobeto.banking.business.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Kredi koşulu filtreleme isteği DTO'su
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetCreditConditionRequest {
    
    private Long id;
    private Long creditTypeId;
    private String conditionName;
} 