package com.demirciyazilim.business.abstracts;

import com.demirciyazilim.business.dtos.blog.requests.CreateBlogRequest;
import com.demirciyazilim.business.dtos.blog.requests.UpdateBlogRequest;
import com.demirciyazilim.business.dtos.blog.responses.BlogResponse;
import com.demirciyazilim.core.utilities.results.DataResult;
import com.demirciyazilim.core.utilities.results.Result;

import java.util.List;

public interface BlogService {
    
    DataResult<List<BlogResponse>> getAll();
    
    DataResult<List<BlogResponse>> getAllActive(int page, int size);
    
    DataResult<BlogResponse> getById(Long id);
    
    DataResult<BlogResponse> add(CreateBlogRequest createBlogRequest);
    
    DataResult<BlogResponse> update(Long id, UpdateBlogRequest updateBlogRequest);
    
    Result delete(Long id);
    
    Result activate(Long id);
    
    Result deactivate(Long id);
    
    DataResult<List<BlogResponse>> getLatestBlogs(int count);
    
    DataResult<List<BlogResponse>> searchByTitle(String title, int page, int size);
    
    DataResult<List<BlogResponse>> searchByTag(String tag, int page, int size);
} 