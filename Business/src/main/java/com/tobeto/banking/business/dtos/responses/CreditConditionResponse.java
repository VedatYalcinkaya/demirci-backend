package com.tobeto.banking.business.dtos.responses;

import com.tobeto.banking.core.entities.IDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Kredi koşulu yanıt DTO'su
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreditConditionResponse implements IDto {
    
    private Long id;
    private Long creditTypeId;
    private String creditTypeName;
    private String conditionName;
    private String conditionValue;
    private String description;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
} 