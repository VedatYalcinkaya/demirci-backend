package com.tobeto.banking.entities.dtos;

import com.tobeto.banking.core.entities.IDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Bireysel müşteri veri transfer nesnesi
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IndividualCustomerDto implements IDto {
    
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
    private boolean isActive;
} 