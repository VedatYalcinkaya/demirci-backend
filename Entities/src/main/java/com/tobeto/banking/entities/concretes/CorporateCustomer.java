package com.tobeto.banking.entities.concretes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * Kurumsal müşteri sınıfı
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "corporate_customers")
public class CorporateCustomer extends Customer {
    
    @Column(name = "company_name", nullable = false, length = 100)
    private String companyName;
    
    @Column(name = "tax_number", nullable = false, unique = true, length = 20)
    private String taxNumber;
    
    @Column(name = "registration_number", unique = true, length = 20)
    private String registrationNumber;
    
    @Column(name = "foundation_year")
    private Integer foundationYear;
    
    @Column(name = "sector", length = 50)
    private String sector;
    
    @Column(name = "employee_count")
    private Integer employeeCount;
    
    @Column(name = "annual_revenue")
    private Double annualRevenue;
    
    @Column(name = "website", length = 100)
    private String website;
    
    @Column(name = "contact_person_name", length = 100)
    private String contactPersonName;
    
    @Column(name = "contact_person_title", length = 50)
    private String contactPersonTitle;
} 