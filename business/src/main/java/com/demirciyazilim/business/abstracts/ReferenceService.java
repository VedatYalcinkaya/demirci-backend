package com.demirciyazilim.business.abstracts;

import com.demirciyazilim.business.dtos.reference.requests.CreateReferenceRequest;
import com.demirciyazilim.business.dtos.reference.requests.CreateReferenceImageRequest;
import com.demirciyazilim.business.dtos.reference.requests.UpdateReferenceRequest;
import com.demirciyazilim.business.dtos.reference.requests.UpdateReferenceImageRequest;
import com.demirciyazilim.business.dtos.reference.responses.ReferenceResponse;
import com.demirciyazilim.business.dtos.reference.responses.ReferenceImageResponse;
import com.demirciyazilim.core.utilities.results.DataResult;
import com.demirciyazilim.core.utilities.results.Result;

import java.util.List;

public interface ReferenceService {
    
    DataResult<List<ReferenceResponse>> getAll();
    
    DataResult<List<ReferenceResponse>> getAllActive(int page, int size);
    
    DataResult<ReferenceResponse> getById(Long id);
    
    DataResult<ReferenceResponse> add(CreateReferenceRequest request);
    
    DataResult<ReferenceResponse> update(UpdateReferenceRequest request);
    
    Result delete(Long id);
    
    Result activate(Long id);
    
    Result deactivate(Long id);
    
    DataResult<List<ReferenceResponse>> getLatestReferences(int count);
    
    DataResult<List<ReferenceResponse>> searchByTitle(String title, int page, int size);
    
    DataResult<List<ReferenceResponse>> searchByTechnology(String technology, int page, int size);
    
    DataResult<ReferenceImageResponse> addImage(Long referenceId, CreateReferenceImageRequest request);
    
    Result deleteImage(Long imageId);
    
    DataResult<List<ReferenceImageResponse>> getImagesByReferenceId(Long referenceId);
} 