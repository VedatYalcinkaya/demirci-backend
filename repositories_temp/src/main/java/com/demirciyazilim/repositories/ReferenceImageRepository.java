package com.demirciyazilim.repositories;

import com.demirciyazilim.entities.ReferenceImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReferenceImageRepository extends JpaRepository<ReferenceImage, Long> {
    
    List<ReferenceImage> findByReferenceIdOrderByDisplayOrder(Long referenceId);
    
    void deleteByReferenceId(Long referenceId);
} 