package com.demirciyazilim.business.rules;

import com.demirciyazilim.business.constants.Messages;
import com.demirciyazilim.core.utilities.exceptions.BusinessException;
import com.demirciyazilim.entities.Reference;
import com.demirciyazilim.entities.ReferenceImage;
import com.demirciyazilim.repositories.ReferenceImageRepository;
import com.demirciyazilim.repositories.ReferenceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ReferenceBusinessRules {
    
    private final ReferenceRepository referenceRepository;
    private final ReferenceImageRepository referenceImageRepository;
    
    public void checkIfReferenceExists(Long id) {
        if (!referenceRepository.existsById(id)) {
            throw new BusinessException(Messages.REFERENCE_NOT_FOUND);
        }
    }
    
    public void checkIfReferenceImageExists(Long id) {
        if (!referenceImageRepository.existsById(id)) {
            throw new BusinessException(Messages.REFERENCE_IMAGE_NOT_FOUND);
        }
    }
} 