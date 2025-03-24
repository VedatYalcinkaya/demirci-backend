package com.demirciyazilim.repositories;

import com.demirciyazilim.entities.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Long> {
    
    Page<Blog> findByIsActiveTrue(Pageable pageable);
    
    List<Blog> findTop5ByIsActiveTrueOrderByCreatedAtDesc();
    
    Page<Blog> findByTitleContainingIgnoreCaseAndIsActiveTrue(String title, Pageable pageable);
    
    Page<Blog> findByTagsContainingIgnoreCaseAndIsActiveTrue(String tag, Pageable pageable);
    
    // SEO için slug ile ilgili metotlar
    Optional<Blog> findBySlug(String slug);
    
    boolean existsBySlug(String slug);
    
    boolean existsBySlugAndIdNot(String slug, Long id);
} 