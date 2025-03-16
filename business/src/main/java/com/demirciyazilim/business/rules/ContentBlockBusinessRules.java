package com.demirciyazilim.business.rules;

import com.demirciyazilim.business.constants.Messages;
import com.demirciyazilim.core.utilities.exceptions.BusinessException;
import com.demirciyazilim.entities.ContentBlock;
import com.demirciyazilim.repositories.ContentBlockRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ContentBlockBusinessRules {
    
    private final ContentBlockRepository contentBlockRepository;
    
    public void checkIfContentBlockExists(Long id) {
        if (!contentBlockRepository.existsById(id)) {
            throw new BusinessException(Messages.CONTENT_BLOCK_NOT_FOUND);
        }
    }
    
    public void checkIfContentBlockExistsByIdentifier(String identifier) {
        Optional<ContentBlock> contentBlock = contentBlockRepository.findByIdentifier(identifier);
        if (contentBlock.isEmpty()) {
            throw new BusinessException(Messages.CONTENT_BLOCK_NOT_FOUND);
        }
    }
    
    public void checkIfContentBlockIdentifierNotExists(String identifier) {
        Optional<ContentBlock> contentBlock = contentBlockRepository.findByIdentifier(identifier);
        if (contentBlock.isPresent()) {
            throw new BusinessException(Messages.CONTENT_BLOCK_IDENTIFIER_ALREADY_EXISTS);
        }
    }
} 