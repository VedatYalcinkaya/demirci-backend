package com.tobeto.banking.core.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

/**
 * Tüm entity sınıfları için temel sınıf
 * Ortak alanları içerir
 * @param <ID> ID tipi
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@MappedSuperclass
public abstract class BaseEntity<ID> implements IEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private ID id;
    
    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;
    
    @Column(name = "updated_date")
    private LocalDateTime updatedDate;
    
    @Column(name = "deleted_date")
    private LocalDateTime deletedDate;
    
    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;
    
    @PrePersist
    public void prePersist() {
        this.createdDate = LocalDateTime.now();
    }
    
    @PreUpdate
    public void preUpdate() {
        this.updatedDate = LocalDateTime.now();
    }
} 