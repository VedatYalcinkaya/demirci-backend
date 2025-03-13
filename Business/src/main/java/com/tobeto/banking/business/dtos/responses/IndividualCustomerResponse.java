package com.tobeto.banking.business.dtos.responses;

import com.tobeto.banking.core.entities.IDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Bireysel müşteri yanıtı
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IndividualCustomerResponse implements IDto {
    
    private Long id;
    private String firstName;
    private String lastName;
    private String identityNumber;
    private LocalDate birthDate;
    private String email;
    private String phoneNumber;
    private String address;
    private String nationality;
    private String gender;
    private String maritalStatus;
    private String educationLevel;
    private String profession;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private boolean isActive;
} 