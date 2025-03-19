package com.demirciyazilim.business.mappers;

import com.demirciyazilim.business.dtos.blog.requests.CreateBlogRequest;
import com.demirciyazilim.business.dtos.blog.requests.UpdateBlogRequest;
import com.demirciyazilim.business.dtos.blog.responses.BlogResponse;
import com.demirciyazilim.entities.Blog;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BlogMapper {

    public Blog toEntity(CreateBlogRequest request) {
        Blog blog = new Blog();
        blog.setTitle(request.getTitle());
        blog.setSummary(request.getSummary());
        blog.setContent(request.getContent());
        blog.setImageUrl(request.getThumbnailUrl());
        blog.setAuthor(request.getAuthor());
        blog.setTags(request.getTags());
        blog.setActive(request.isActive());
        
        // SEO alanları
        blog.setSlug(request.getSlug());
        blog.setMetaTitle(request.getMetaTitle());
        blog.setMetaDescription(request.getMetaDescription());
        blog.setMetaKeywords(request.getMetaKeywords());
        blog.setCanonicalUrl(request.getCanonicalUrl());
        
        return blog;
    }
    
    public Blog toEntity(UpdateBlogRequest request) {
        Blog blog = new Blog();
        blog.setId(request.getId());
        blog.setTitle(request.getTitle());
        blog.setSummary(request.getSummary());
        blog.setContent(request.getContent());
        blog.setImageUrl(request.getThumbnailUrl());
        blog.setAuthor(request.getAuthor());
        blog.setTags(request.getTags());
        blog.setActive(request.isActive());
        
        // SEO alanları
        blog.setSlug(request.getSlug());
        blog.setMetaTitle(request.getMetaTitle());
        blog.setMetaDescription(request.getMetaDescription());
        blog.setMetaKeywords(request.getMetaKeywords());
        blog.setCanonicalUrl(request.getCanonicalUrl());
        
        return blog;
    }

    public BlogResponse toResponse(Blog entity) {
        BlogResponse response = new BlogResponse();
        response.setId(entity.getId());
        response.setTitle(entity.getTitle());
        response.setSummary(entity.getSummary());
        response.setContent(entity.getContent());
        response.setThumbnailUrl(entity.getImageUrl());
        response.setAuthor(entity.getAuthor());
        response.setTags(entity.getTags());
        response.setActive(entity.isActive());
        response.setCreatedAt(entity.getCreatedAt());
        response.setUpdatedAt(entity.getUpdatedAt());
        
        // SEO alanları
        response.setSlug(entity.getSlug());
        response.setMetaTitle(entity.getMetaTitle());
        response.setMetaDescription(entity.getMetaDescription());
        response.setMetaKeywords(entity.getMetaKeywords());
        response.setCanonicalUrl(entity.getCanonicalUrl());
        
        return response;
    }

    public List<BlogResponse> toResponseList(List<Blog> entities) {
        return entities.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public void updateEntityFromRequest(UpdateBlogRequest request, Blog entity) {
        entity.setTitle(request.getTitle());
        entity.setSummary(request.getSummary());
        entity.setContent(request.getContent());
        entity.setImageUrl(request.getThumbnailUrl());
        entity.setAuthor(request.getAuthor());
        entity.setTags(request.getTags());
        entity.setActive(request.isActive());
        
        // SEO alanları
        entity.setSlug(request.getSlug());
        entity.setMetaTitle(request.getMetaTitle());
        entity.setMetaDescription(request.getMetaDescription());
        entity.setMetaKeywords(request.getMetaKeywords());
        entity.setCanonicalUrl(request.getCanonicalUrl());
    }
} 