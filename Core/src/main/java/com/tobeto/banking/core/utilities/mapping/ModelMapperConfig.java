package com.tobeto.banking.core.utilities.mapping;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ModelMapper için konfigürasyon sınıfı
 */
@Configuration
public class ModelMapperConfig {
    
    /**
     * ModelMapper bean'i oluşturur
     * @return ModelMapper
     */
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
} 