package com.tobeto.banking.entities.concretes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

/**
 * Bireysel müşteri sınıfı
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "individual_customers")
public class IndividualCustomer extends Customer {
    
    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;
    
    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;
    
    @Column(name = "identity_number", nullable = false, unique = true, length = 11)
    private String identityNumber;
    
    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;
    
    @Column(name = "nationality", length = 50)
    private String nationality;
    
    @Column(name = "gender", length = 10)
    private String gender;
    
    @Column(name = "marital_status", length = 20)
    private String maritalStatus;
    
    @Column(name = "education_level", length = 50)
    private String educationLevel;
    
    @Column(name = "profession", length = 50)
    private String profession;
} 