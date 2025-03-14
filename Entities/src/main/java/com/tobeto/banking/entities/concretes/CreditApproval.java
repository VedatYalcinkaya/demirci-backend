package com.tobeto.banking.entities.concretes;

import com.tobeto.banking.core.entities.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Kredi onaylarını temsil eden entity sınıfı
 */
@Entity
@Table(name = "credit_approvals")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class CreditApproval extends BaseEntity<Long> {
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "credit_application_id", nullable = false, unique = true)
    private CreditApplication creditApplication;
    
    @Column(name = "approved_by", nullable = false)
    private String approvedBy;
    
    @Column(name = "approval_date", nullable = false)
    private LocalDateTime approvalDate;
    
    @Column(name = "notes", length = 1000)
    private String notes;
} 