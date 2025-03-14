package com.tobeto.banking.entities.concretes;

import com.tobeto.banking.core.entities.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.List;

/**
 * Müşteri temel sınıfı
 * IndividualCustomer ve CorporateCustomer sınıfları bu sınıftan türetilir
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "customers")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Customer extends BaseEntity<Long> {
    
    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;
    
    @Column(name = "phone_number", nullable = false, length = 15)
    private String phoneNumber;
    
    @Column(name = "address", length = 255)
    private String address;
    
    @OneToMany(mappedBy = "customer")
    private List<CreditApplication> creditApplications;
    
    @OneToMany(mappedBy = "customer")
    private List<CreditScore> creditScores;
} 