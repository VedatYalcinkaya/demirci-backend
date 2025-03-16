package com.demirciyazilim.repositories;

import com.demirciyazilim.entities.ContentBlock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContentBlockRepository extends JpaRepository<ContentBlock, Long> {
    
    Optional<ContentBlock> findByIdentifier(String identifier);
    
    List<ContentBlock> findByType(String type);
    
    List<ContentBlock> findByIsActiveTrue();
} 