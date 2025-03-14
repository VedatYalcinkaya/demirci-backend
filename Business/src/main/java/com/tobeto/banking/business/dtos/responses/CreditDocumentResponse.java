package com.tobeto.banking.business.dtos.responses;

import com.tobeto.banking.core.entities.IDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Kredi belgesi yanıt DTO'su
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreditDocumentResponse implements IDto {
    
    private Long id;
    private Long creditApplicationId;
    private String documentType;
    private String documentName;
    private String documentPath;
    private LocalDateTime uploadDate;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
} 