package com.tobeto.banking.entities.concretes;

import com.tobeto.banking.core.entities.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Kredi skorlarını temsil eden entity sınıfı
 */
@Entity
@Table(name = "credit_scores")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class CreditScore extends BaseEntity<Long> {
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;
    
    @Column(name = "score", nullable = false)
    private Integer score;
    
    @Column(name = "calculation_date", nullable = false)
    private LocalDateTime calculationDate;
    
    @Column(name = "expiry_date", nullable = false)
    private LocalDateTime expiryDate;
} 