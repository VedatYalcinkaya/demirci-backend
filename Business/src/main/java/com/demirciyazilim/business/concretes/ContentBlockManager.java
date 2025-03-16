package com.demirciyazilim.business.concretes;

import com.demirciyazilim.business.abstracts.ContentBlockService;
import com.demirciyazilim.business.constants.Messages;
import com.demirciyazilim.business.dtos.contentblock.requests.CreateContentBlockRequest;
import com.demirciyazilim.business.dtos.contentblock.requests.UpdateContentBlockRequest;
import com.demirciyazilim.business.dtos.contentblock.responses.ContentBlockResponse;
import com.demirciyazilim.business.mappers.ContentBlockMapper;
import com.demirciyazilim.business.rules.ContentBlockBusinessRules;
import com.demirciyazilim.core.utilities.results.*;
import com.demirciyazilim.entities.ContentBlock;
import com.demirciyazilim.repositories.ContentBlockRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ContentBlockManager implements ContentBlockService {
    
    private final ContentBlockRepository contentBlockRepository;
    private final ContentBlockBusinessRules contentBlockBusinessRules;
    private final ContentBlockMapper contentBlockMapper;
    
    @Override
    public DataResult<List<ContentBlockResponse>> getAll() {
        List<ContentBlock> contentBlocks = contentBlockRepository.findAll();
        List<ContentBlockResponse> contentBlockResponses = contentBlockMapper.toResponseList(contentBlocks);
        return new SuccessDataResult<>(contentBlockResponses, Messages.CONTENT_BLOCKS_LISTED_SUCCESSFULLY);
    }
    
    @Override
    public DataResult<List<ContentBlockResponse>> getAllActive() {
        List<ContentBlock> contentBlocks = contentBlockRepository.findByIsActiveTrue();
        List<ContentBlockResponse> contentBlockResponses = contentBlockMapper.toResponseList(contentBlocks);
        return new SuccessDataResult<>(contentBlockResponses, Messages.CONTENT_BLOCKS_LISTED_SUCCESSFULLY);
    }
    
    @Override
    public DataResult<ContentBlockResponse> getById(Long id) {
        Optional<ContentBlock> contentBlock = contentBlockRepository.findById(id);
        if (contentBlock.isEmpty()) {
            return new ErrorDataResult<>(Messages.CONTENT_BLOCK_NOT_FOUND);
        }
        ContentBlockResponse contentBlockResponse = contentBlockMapper.toResponse(contentBlock.get());
        return new SuccessDataResult<>(contentBlockResponse, Messages.CONTENT_BLOCK_FETCHED_SUCCESSFULLY);
    }
    
    @Override
    public DataResult<ContentBlockResponse> getByIdentifier(String identifier) {
        Optional<ContentBlock> contentBlock = contentBlockRepository.findByIdentifier(identifier);
        if (contentBlock.isEmpty()) {
            return new ErrorDataResult<>(Messages.CONTENT_BLOCK_NOT_FOUND);
        }
        ContentBlockResponse contentBlockResponse = contentBlockMapper.toResponse(contentBlock.get());
        return new SuccessDataResult<>(contentBlockResponse, Messages.CONTENT_BLOCK_FETCHED_SUCCESSFULLY);
    }
    
    @Override
    public DataResult<List<ContentBlockResponse>> getByType(String type) {
        List<ContentBlock> contentBlocks = contentBlockRepository.findByType(type);
        List<ContentBlockResponse> contentBlockResponses = contentBlockMapper.toResponseList(contentBlocks);
        return new SuccessDataResult<>(contentBlockResponses, Messages.CONTENT_BLOCKS_LISTED_SUCCESSFULLY);
    }
    
    @Override
    public DataResult<ContentBlockResponse> add(CreateContentBlockRequest request) {
        // İş kuralı kontrolü - tanımlayıcı benzersiz olmalı
        contentBlockBusinessRules.checkIfContentBlockIdentifierNotExists(request.getIdentifier());
        
        ContentBlock contentBlock = contentBlockMapper.toEntity(request);
        ContentBlock savedContentBlock = contentBlockRepository.save(contentBlock);
        ContentBlockResponse response = contentBlockMapper.toResponse(savedContentBlock);
        
        return new SuccessDataResult<>(response, Messages.CONTENT_BLOCK_ADDED_SUCCESSFULLY);
    }
    
    @Override
    public DataResult<ContentBlockResponse> update(UpdateContentBlockRequest request) {
        // İş kuralı kontrolü - içerik bloğu var mı?
        contentBlockBusinessRules.checkIfContentBlockExists(request.getId());
        
        // İş kuralı kontrolü - güncellenen tanımlayıcı başka bir içerik bloğunda kullanılıyor mu?
        Optional<ContentBlock> existingContentBlockByIdentifier = contentBlockRepository.findByIdentifier(request.getIdentifier());
        if (existingContentBlockByIdentifier.isPresent() && !existingContentBlockByIdentifier.get().getId().equals(request.getId())) {
            return new ErrorDataResult<>(Messages.CONTENT_BLOCK_IDENTIFIER_ALREADY_EXISTS);
        }
        
        ContentBlock existingContentBlock = contentBlockRepository.findById(request.getId()).get();
        ContentBlock contentBlock = contentBlockMapper.toEntity(request);
        contentBlock.setCreatedAt(existingContentBlock.getCreatedAt());
        
        ContentBlock updatedContentBlock = contentBlockRepository.save(contentBlock);
        ContentBlockResponse response = contentBlockMapper.toResponse(updatedContentBlock);
        
        return new SuccessDataResult<>(response, Messages.CONTENT_BLOCK_UPDATED_SUCCESSFULLY);
    }
    
    @Override
    public Result delete(Long id) {
        contentBlockBusinessRules.checkIfContentBlockExists(id);
        contentBlockRepository.deleteById(id);
        return new SuccessResult(Messages.CONTENT_BLOCK_DELETED_SUCCESSFULLY);
    }
    
    @Override
    public Result activate(Long id) {
        contentBlockBusinessRules.checkIfContentBlockExists(id);
        ContentBlock contentBlock = contentBlockRepository.findById(id).get();
        contentBlock.setActive(true);
        contentBlockRepository.save(contentBlock);
        return new SuccessResult(Messages.CONTENT_BLOCK_ACTIVATED_SUCCESSFULLY);
    }
    
    @Override
    public Result deactivate(Long id) {
        contentBlockBusinessRules.checkIfContentBlockExists(id);
        ContentBlock contentBlock = contentBlockRepository.findById(id).get();
        contentBlock.setActive(false);
        contentBlockRepository.save(contentBlock);
        return new SuccessResult(Messages.CONTENT_BLOCK_DEACTIVATED_SUCCESSFULLY);
    }
} 