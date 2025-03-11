package com.tobeto.banking.entities.dtos;

import com.tobeto.banking.core.entities.IDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Kurumsal müşteri veri transfer nesnesi
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CorporateCustomerDto implements IDto {
    
    private Long id;
    private String companyName;
    private String taxNumber;
    private String registrationNumber;
    private Integer foundationYear;
    private String sector;
    private Integer employeeCount;
    private Double annualRevenue;
    private String website;
    private String email;
    private String phoneNumber;
    private String address;
    private String contactPersonName;
    private String contactPersonTitle;
    private boolean isActive;
} 