package com.demirciyazilim.repositories;

import com.demirciyazilim.entities.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Long> {
    
    Page<Blog> findByIsActiveTrue(Pageable pageable);
    
    List<Blog> findTop5ByIsActiveTrueOrderByCreatedAtDesc();
    
    Page<Blog> findByTitleContainingAndIsActiveTrue(String title, Pageable pageable);
    
    Page<Blog> findByTagsContainingAndIsActiveTrue(String tag, Pageable pageable);
} 