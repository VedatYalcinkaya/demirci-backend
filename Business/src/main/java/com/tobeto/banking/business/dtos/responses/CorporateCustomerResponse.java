package com.tobeto.banking.business.dtos.responses;

import com.tobeto.banking.core.entities.IDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Kurumsal müşteri yanıtı
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CorporateCustomerResponse implements IDto {
    
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
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private boolean isActive;
} 