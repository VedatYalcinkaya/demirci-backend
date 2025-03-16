package com.demirciyazilim.business.mappers;

import com.demirciyazilim.business.dtos.reference.requests.CreateReferenceImageRequest;
import com.demirciyazilim.business.dtos.reference.requests.UpdateReferenceImageRequest;
import com.demirciyazilim.business.dtos.reference.responses.ReferenceImageResponse;
import com.demirciyazilim.entities.Reference;
import com.demirciyazilim.entities.ReferenceImage;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ReferenceImageMapper {

    public ReferenceImage toEntity(CreateReferenceImageRequest request, Reference reference) {
        ReferenceImage entity = new ReferenceImage();
        entity.setImageUrl(request.getImageUrl());
        entity.setCaption(request.getCaption());
        entity.setDisplayOrder(request.getDisplayOrder());
        entity.setActive(request.isActive());
        entity.setReference(reference);
        entity.setCreatedAt(LocalDateTime.now());
        return entity;
    }

    public ReferenceImage toEntity(UpdateReferenceImageRequest request) {
        ReferenceImage entity = new ReferenceImage();
        entity.setId(request.getId());
        entity.setImageUrl(request.getImageUrl());
        entity.setCaption(request.getCaption());
        entity.setDisplayOrder(request.getDisplayOrder());
        entity.setActive(request.isActive());
        entity.setUpdatedAt(LocalDateTime.now());
        return entity;
    }

    public ReferenceImageResponse toResponse(ReferenceImage entity) {
        ReferenceImageResponse response = new ReferenceImageResponse();
        response.setId(entity.getId());
        response.setImageUrl(entity.getImageUrl());
        response.setCaption(entity.getCaption());
        response.setDisplayOrder(entity.getDisplayOrder());
        response.setActive(entity.isActive());
        response.setCreatedAt(toDate(entity.getCreatedAt()));
        response.setUpdatedAt(toDate(entity.getUpdatedAt()));
        return response;
    }

    public List<ReferenceImageResponse> toResponseList(List<ReferenceImage> entities) {
        return entities.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public void updateEntityFromRequest(UpdateReferenceImageRequest request, ReferenceImage entity) {
        entity.setImageUrl(request.getImageUrl());
        entity.setCaption(request.getCaption());
        entity.setDisplayOrder(request.getDisplayOrder());
        entity.setActive(request.isActive());
        entity.setUpdatedAt(LocalDateTime.now());
    }

    // Yardımcı dönüşüm metodu
    private Date toDate(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
} 