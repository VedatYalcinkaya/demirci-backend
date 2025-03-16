package com.demirciyazilim.business.abstracts;

import com.demirciyazilim.business.dtos.contentblock.requests.CreateContentBlockRequest;
import com.demirciyazilim.business.dtos.contentblock.requests.UpdateContentBlockRequest;
import com.demirciyazilim.business.dtos.contentblock.responses.ContentBlockResponse;
import com.demirciyazilim.core.utilities.results.DataResult;
import com.demirciyazilim.core.utilities.results.Result;

import java.util.List;

public interface ContentBlockService {
    
    DataResult<List<ContentBlockResponse>> getAll();
    
    DataResult<List<ContentBlockResponse>> getAllActive();
    
    DataResult<ContentBlockResponse> getById(Long id);
    
    DataResult<ContentBlockResponse> getByIdentifier(String identifier);
    
    DataResult<List<ContentBlockResponse>> getByType(String type);
    
    DataResult<ContentBlockResponse> add(CreateContentBlockRequest request);
    
    DataResult<ContentBlockResponse> update(UpdateContentBlockRequest request);
    
    Result delete(Long id);
    
    Result activate(Long id);
    
    Result deactivate(Long id);
} 