package com.demirciyazilim.business.mappers;

import com.demirciyazilim.business.dtos.contentblock.requests.CreateContentBlockRequest;
import com.demirciyazilim.business.dtos.contentblock.requests.UpdateContentBlockRequest;
import com.demirciyazilim.business.dtos.contentblock.responses.ContentBlockResponse;
import com.demirciyazilim.entities.ContentBlock;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ContentBlockMapper {
    
    public ContentBlock toEntity(CreateContentBlockRequest request) {
        ContentBlock entity = new ContentBlock();
        entity.setIdentifier(request.getIdentifier());
        entity.setTitle(request.getTitle());
        entity.setContent(request.getContent());
        entity.setType(request.getType());
        entity.setActive(request.isActive());
        entity.setCreatedAt(LocalDateTime.now());
        return entity;
    }
    
    public ContentBlock toEntity(UpdateContentBlockRequest request) {
        ContentBlock entity = new ContentBlock();
        entity.setId(request.getId());
        entity.setIdentifier(request.getIdentifier());
        entity.setTitle(request.getTitle());
        entity.setContent(request.getContent());
        entity.setType(request.getType());
        entity.setActive(request.isActive());
        entity.setUpdatedAt(LocalDateTime.now());
        return entity;
    }
    
    public ContentBlockResponse toResponse(ContentBlock entity) {
        ContentBlockResponse response = new ContentBlockResponse();
        response.setId(entity.getId());
        response.setIdentifier(entity.getIdentifier());
        response.setTitle(entity.getTitle());
        response.setContent(entity.getContent());
        response.setType(entity.getType());
        response.setActive(entity.isActive());
        response.setCreatedAt(entity.getCreatedAt());
        response.setUpdatedAt(entity.getUpdatedAt());
        return response;
    }
    
    public List<ContentBlockResponse> toResponseList(List<ContentBlock> entities) {
        return entities.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
    
    public void updateEntityFromRequest(UpdateContentBlockRequest request, ContentBlock entity) {
        entity.setIdentifier(request.getIdentifier());
        entity.setTitle(request.getTitle());
        entity.setContent(request.getContent());
        entity.setType(request.getType());
        entity.setActive(request.isActive());
        entity.setUpdatedAt(LocalDateTime.now());
    }
} 