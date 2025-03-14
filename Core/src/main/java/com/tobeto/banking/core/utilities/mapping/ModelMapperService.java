package com.tobeto.banking.core.utilities.mapping;

import org.modelmapper.ModelMapper;

/**
 * Model dönüşümleri için servis arayüzü
 */
public interface ModelMapperService {
    
    /**
     * Request DTO'larını entity'lere dönüştürmek için kullanılır
     * @return ModelMapper
     */
    ModelMapper forRequest();
    
    /**
     * Entity'leri response DTO'larına dönüştürmek için kullanılır
     * @return ModelMapper
     */
    ModelMapper forResponse();
} 