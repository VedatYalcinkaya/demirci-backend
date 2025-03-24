package com.demirciyazilim.repositories;

import com.demirciyazilim.entities.Reference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReferenceRepository extends JpaRepository<Reference, Long> {
    
    Page<Reference> findByIsActiveTrue(Pageable pageable);
    
    List<Reference> findTop5ByIsActiveTrueOrderByCompletionDateDesc();
    
    Page<Reference> findByTitleContainingIgnoreCaseAndIsActiveTrue(String title, Pageable pageable);
    
    Page<Reference> findByTechnologiesContainingIgnoreCaseAndIsActiveTrue(String technology, Pageable pageable);
} 