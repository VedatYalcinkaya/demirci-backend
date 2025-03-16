package com.demirciyazilim.entities;

import com.demirciyazilim.core.entities.BaseEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "reference_images")
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class ReferenceImage extends BaseEntity {
    
    @Column(nullable = false)
    private String imageUrl;
    
    @Column
    private String caption;
    
    @Column
    private int displayOrder;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reference_id", nullable = false)
    @JsonBackReference
    private Reference reference;
} 