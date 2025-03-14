package com.tobeto.banking.entities.concretes;

import com.tobeto.banking.core.entities.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * Kredi türlerini temsil eden entity sınıfı
 */
@Entity
@Table(name = "credit_types")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class CreditType extends BaseEntity<Long> {
    
    @Column(name = "name", nullable = false)
    private String name;
    
    @Column(name = "description", length = 1000)
    private String description;
    
    @Column(name = "customer_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private CustomerType customerType;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private CreditType parent;
    
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private List<CreditType> subTypes;
    
    @Column(name = "interest_rate", nullable = false, precision = 5, scale = 2)
    private BigDecimal interestRate;
    
    @Column(name = "min_amount", nullable = false)
    private BigDecimal minAmount;
    
    @Column(name = "max_amount", nullable = false)
    private BigDecimal maxAmount;
    
    @Column(name = "min_term", nullable = false)
    private Integer minTerm;
    
    @Column(name = "max_term", nullable = false)
    private Integer maxTerm;
    
    @OneToMany(mappedBy = "creditType", cascade = CascadeType.ALL)
    private List<CreditCondition> conditions;
    
    @OneToMany(mappedBy = "creditType")
    private List<CreditApplication> applications;
    
    /**
     * Müşteri tipi enum'u
     */
    public enum CustomerType {
        INDIVIDUAL,
        CORPORATE
    }
} 