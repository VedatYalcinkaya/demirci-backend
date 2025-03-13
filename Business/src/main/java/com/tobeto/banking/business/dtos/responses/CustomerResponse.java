package com.tobeto.banking.business.dtos.responses;

import com.tobeto.banking.core.entities.IDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Genel müşteri yanıtı
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerResponse implements IDto {
    
    private Long id;
    private String email;
    private String phoneNumber;
    private String address;
    private String customerType; // "INDIVIDUAL" veya "CORPORATE"
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private boolean isActive;
} 