package com.demirciyazilim.business.mappers;

import com.demirciyazilim.business.dtos.reference.requests.CreateReferenceRequest;
import com.demirciyazilim.business.dtos.reference.requests.UpdateReferenceRequest;
import com.demirciyazilim.business.dtos.reference.responses.ReferenceResponse;
import com.demirciyazilim.entities.Reference;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ReferenceMapper {
    
    private final ReferenceImageMapper referenceImageMapper;
    
    public ReferenceMapper(ReferenceImageMapper referenceImageMapper) {
        this.referenceImageMapper = referenceImageMapper;
    }
    
    public Reference toEntity(CreateReferenceRequest request) {
        Reference entity = new Reference();
        entity.setTitle(request.getTitle());
        entity.setSummary(request.getSummary());
        entity.setDescription(request.getDescription());
        entity.setThumbnailUrl(request.getThumbnailUrl());
        entity.setClientName(request.getClientName());
        entity.setClientLogo(request.getClientLogo());
        entity.setProjectUrl(request.getProjectUrl());
        entity.setCompletionDate(toLocalDateTime(request.getCompletionDate()));
        entity.setTechnologies(request.getTechnologies());
        entity.setActive(request.isActive());
        entity.setCreatedAt(LocalDateTime.now());
        return entity;
    }
    
    public Reference toEntity(UpdateReferenceRequest request) {
        Reference entity = new Reference();
        entity.setId(request.getId());
        entity.setTitle(request.getTitle());
        entity.setSummary(request.getSummary());
        entity.setDescription(request.getDescription());
        entity.setThumbnailUrl(request.getThumbnailUrl());
        entity.setClientName(request.getClientName());
        entity.setClientLogo(request.getClientLogo());
        entity.setProjectUrl(request.getProjectUrl());
        entity.setCompletionDate(toLocalDateTime(request.getCompletionDate()));
        entity.setTechnologies(request.getTechnologies());
        entity.setActive(request.isActive());
        entity.setUpdatedAt(LocalDateTime.now());
        return entity;
    }
    
    public ReferenceResponse toResponse(Reference entity) {
        ReferenceResponse response = new ReferenceResponse();
        response.setId(entity.getId());
        response.setTitle(entity.getTitle());
        response.setSummary(entity.getSummary());
        response.setDescription(entity.getDescription());
        response.setThumbnailUrl(entity.getThumbnailUrl());
        response.setClientName(entity.getClientName());
        response.setClientLogo(entity.getClientLogo());
        response.setProjectUrl(entity.getProjectUrl());
        response.setCompletionDate(toDate(entity.getCompletionDate()));
        response.setTechnologies(entity.getTechnologies());
        response.setActive(entity.isActive());
        response.setCreatedAt(toDate(entity.getCreatedAt()));
        response.setUpdatedAt(toDate(entity.getUpdatedAt()));
        
        if (entity.getImages() != null && !entity.getImages().isEmpty()) {
            response.setImages(referenceImageMapper.toResponseList(entity.getImages()));
        }
        
        return response;
    }
    
    public List<ReferenceResponse> toResponseList(List<Reference> entities) {
        return entities.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
    
    public void updateEntityFromRequest(UpdateReferenceRequest request, Reference entity) {
        entity.setTitle(request.getTitle());
        entity.setSummary(request.getSummary());
        entity.setDescription(request.getDescription());
        entity.setThumbnailUrl(request.getThumbnailUrl());
        entity.setClientName(request.getClientName());
        entity.setClientLogo(request.getClientLogo());
        entity.setProjectUrl(request.getProjectUrl());
        entity.setCompletionDate(toLocalDateTime(request.getCompletionDate()));
        entity.setTechnologies(request.getTechnologies());
        entity.setActive(request.isActive());
        entity.setUpdatedAt(LocalDateTime.now());
    }
    
    // Yardımcı dönüşüm metodları
    private LocalDateTime toLocalDateTime(Date date) {
        if (date == null) {
            return null;
        }
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }
    
    private Date toDate(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
} 