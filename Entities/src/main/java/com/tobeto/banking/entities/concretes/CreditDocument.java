package com.tobeto.banking.entities.concretes;

import com.tobeto.banking.core.entities.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Kredi belgelerini temsil eden entity sınıfı
 */
@Entity
@Table(name = "credit_documents")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class CreditDocument extends BaseEntity<Long> {
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "credit_application_id", nullable = false)
    private CreditApplication creditApplication;
    
    @Column(name = "document_type", nullable = false)
    private String documentType;
    
    @Column(name = "document_name", nullable = false)
    private String documentName;
    
    @Column(name = "document_path", nullable = false)
    private String documentPath;
    
    @Column(name = "upload_date", nullable = false)
    private LocalDateTime uploadDate;
} 