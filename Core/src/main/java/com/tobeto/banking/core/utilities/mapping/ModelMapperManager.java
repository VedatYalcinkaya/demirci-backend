package com.tobeto.banking.core.utilities.mapping;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;

/**
 * Model dönüşümleri için servis implementasyonu
 */
@Service
public class ModelMapperManager implements ModelMapperService {
    
    private final ModelMapper modelMapper;
    
    public ModelMapperManager(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }
    
    @Override
    public ModelMapper forRequest() {
        modelMapper.getConfiguration()
                .setAmbiguityIgnored(true)
                .setMatchingStrategy(MatchingStrategies.STANDARD);
        return modelMapper;
    }
    
    @Override
    public ModelMapper forResponse() {
        modelMapper.getConfiguration()
                .setAmbiguityIgnored(true)
                .setMatchingStrategy(MatchingStrategies.LOOSE);
        return modelMapper;
    }
} 