package com.demirciyazilim.core.utilities.mapping;

import org.modelmapper.ModelMapper;

public interface ModelMapperService {
    
    ModelMapper forResponse();
    
    ModelMapper forRequest();
} 