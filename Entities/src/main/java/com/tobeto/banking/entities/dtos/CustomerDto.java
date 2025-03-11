package com.tobeto.banking.entities.dtos;

import com.tobeto.banking.core.entities.IDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Müşteri veri transfer nesnesi
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto implements IDto {
    
    private Long id;
    private String email;
    private String phoneNumber;
    private String address;
    private boolean isActive;
} 