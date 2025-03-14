package com.tobeto.banking.entities.concretes;

import com.tobeto.banking.core.entities.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Kredi koşullarını temsil eden entity sınıfı
 */
@Entity
@Table(name = "credit_conditions")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class CreditCondition extends BaseEntity<Long> {
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "credit_type_id", nullable = false)
    private CreditType creditType;
    
    @Column(name = "condition_name", nullable = false)
    private String conditionName;
    
    @Column(name = "condition_value", nullable = false)
    private String conditionValue;
    
    @Column(name = "description", length = 1000)
    private String description;
} 