package com.tobeto.banking.entities.concretes;

import com.tobeto.banking.core.entities.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Kredi başvurularını temsil eden entity sınıfı
 */
@Entity
@Table(name = "credit_applications")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class CreditApplication extends BaseEntity<Long> {
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "credit_type_id", nullable = false)
    private CreditType creditType;
    
    @Column(name = "amount", nullable = false)
    private BigDecimal amount;
    
    @Column(name = "term", nullable = false)
    private Integer term;
    
    @Column(name = "interest_rate", nullable = false, precision = 5, scale = 2)
    private BigDecimal interestRate;
    
    @Column(name = "monthly_payment", nullable = false)
    private BigDecimal monthlyPayment;
    
    @Column(name = "total_payment", nullable = false)
    private BigDecimal totalPayment;
    
    @Column(name = "application_date", nullable = false)
    private LocalDateTime applicationDate;
    
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;
    
    @Column(name = "approval_date")
    private LocalDateTime approvalDate;
    
    @Column(name = "rejection_reason", length = 1000)
    private String rejectionReason;
    
    @OneToMany(mappedBy = "creditApplication", cascade = CascadeType.ALL)
    private List<CreditDocument> documents;
    
    @OneToOne(mappedBy = "creditApplication", cascade = CascadeType.ALL)
    private CreditApproval approval;
    
    /**
     * Başvuru durumu enum'u
     */
    public enum ApplicationStatus {
        PENDING,
        APPROVED,
        REJECTED,
        CANCELLED
    }
} 