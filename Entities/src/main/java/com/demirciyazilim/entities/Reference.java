package com.demirciyazilim.entities;

import com.demirciyazilim.core.entities.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "reference_table")
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class Reference extends BaseEntity {
    
    @Column(nullable = false, length = 200)
    private String title;
    
    @Column(nullable = false, length = 500)
    private String summary;
    
    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;
    
    @Column(nullable = false)
    private String thumbnailUrl;
    
    @Column
    private String clientName;
    
    @Column
    private String clientLogo;
    
    @Column
    private String projectUrl;
    
    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime completionDate;
    
    @Column(length = 200)
    private String technologies;
    
    @OneToMany(mappedBy = "reference", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<ReferenceImage> images = new ArrayList<>();
    
    public void addImage(ReferenceImage image) {
        images.add(image);
        image.setReference(this);
    }
    
    public void removeImage(ReferenceImage image) {
        images.remove(image);
        image.setReference(null);
    }
} 