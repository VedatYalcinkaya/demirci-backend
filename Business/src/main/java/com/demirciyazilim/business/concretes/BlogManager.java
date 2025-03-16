package com.demirciyazilim.business.concretes;

import com.demirciyazilim.business.abstracts.BlogService;
import com.demirciyazilim.business.constants.Messages;
import com.demirciyazilim.business.dtos.blog.requests.CreateBlogRequest;
import com.demirciyazilim.business.dtos.blog.requests.UpdateBlogRequest;
import com.demirciyazilim.business.dtos.blog.responses.BlogResponse;
import com.demirciyazilim.business.mappers.BlogMapper;
import com.demirciyazilim.business.rules.BlogBusinessRules;
import com.demirciyazilim.core.utilities.results.*;
import com.demirciyazilim.entities.Blog;
import com.demirciyazilim.repositories.BlogRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BlogManager implements BlogService {
    
    private final BlogRepository blogRepository;
    private final BlogBusinessRules blogBusinessRules;
    private final BlogMapper blogMapper;
    
    @Override
    public DataResult<List<BlogResponse>> getAll() {
        List<Blog> blogs = blogRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
        List<BlogResponse> blogResponses = blogs.stream()
                .map(blogMapper::toResponse)
                .collect(Collectors.toList());
        return new SuccessDataResult<>(blogResponses, Messages.BLOGS_LISTED_SUCCESSFULLY);
    }
    
    @Override
    public DataResult<List<BlogResponse>> getAllActive(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Blog> result = blogRepository.findByIsActiveTrue(pageable);
        List<BlogResponse> blogResponses = result.getContent().stream()
                .map(blogMapper::toResponse)
                .collect(Collectors.toList());
        return new SuccessDataResult<>(blogResponses, Messages.BLOGS_LISTED_SUCCESSFULLY);
    }
    
    @Override
    public DataResult<BlogResponse> getById(Long id) {
        Optional<Blog> blog = blogRepository.findById(id);
        if (blog.isEmpty()) {
            return new ErrorDataResult<>(Messages.BLOG_NOT_FOUND);
        }
        BlogResponse blogResponse = blogMapper.toResponse(blog.get());
        return new SuccessDataResult<>(blogResponse, Messages.BLOG_FETCHED_SUCCESSFULLY);
    }
    
    @Override
    public DataResult<BlogResponse> add(CreateBlogRequest createBlogRequest) {
        Blog blog = blogMapper.toEntity(createBlogRequest);
        blog.setCreatedAt(LocalDateTime.now());
        blog.setActive(true);
        Blog savedBlog = blogRepository.save(blog);
        BlogResponse blogResponse = blogMapper.toResponse(savedBlog);
        return new SuccessDataResult<>(blogResponse, Messages.BLOG_ADDED_SUCCESSFULLY);
    }
    
    @Override
    public DataResult<BlogResponse> update(Long id, UpdateBlogRequest updateBlogRequest) {
        blogBusinessRules.checkIfBlogExists(id);
        Blog existingBlog = blogRepository.findById(id).get();
        blogMapper.updateEntityFromRequest(updateBlogRequest, existingBlog);
        existingBlog.setUpdatedAt(LocalDateTime.now());
        Blog updatedBlog = blogRepository.save(existingBlog);
        BlogResponse blogResponse = blogMapper.toResponse(updatedBlog);
        return new SuccessDataResult<>(blogResponse, Messages.BLOG_UPDATED_SUCCESSFULLY);
    }
    
    @Override
    public Result delete(Long id) {
        blogBusinessRules.checkIfBlogExists(id);
        blogRepository.deleteById(id);
        return new SuccessResult(Messages.BLOG_DELETED_SUCCESSFULLY);
    }
    
    @Override
    public Result activate(Long id) {
        blogBusinessRules.checkIfBlogExists(id);
        Blog blog = blogRepository.findById(id).get();
        blog.setActive(true);
        blog.setUpdatedAt(LocalDateTime.now());
        blogRepository.save(blog);
        return new SuccessResult(Messages.BLOG_ACTIVATED_SUCCESSFULLY);
    }
    
    @Override
    public Result deactivate(Long id) {
        blogBusinessRules.checkIfBlogExists(id);
        Blog blog = blogRepository.findById(id).get();
        blog.setActive(false);
        blog.setUpdatedAt(LocalDateTime.now());
        blogRepository.save(blog);
        return new SuccessResult(Messages.BLOG_DEACTIVATED_SUCCESSFULLY);
    }
    
    @Override
    public DataResult<List<BlogResponse>> getLatestBlogs(int count) {
        List<Blog> blogs = blogRepository.findTop5ByIsActiveTrueOrderByCreatedAtDesc();
        List<BlogResponse> blogResponses = blogs.stream()
                .map(blogMapper::toResponse)
                .collect(Collectors.toList());
        return new SuccessDataResult<>(blogResponses, Messages.BLOGS_LISTED_SUCCESSFULLY);
    }
    
    @Override
    public DataResult<List<BlogResponse>> searchByTitle(String title, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Blog> result = blogRepository.findByTitleContainingAndIsActiveTrue(title, pageable);
        List<BlogResponse> blogResponses = result.getContent().stream()
                .map(blogMapper::toResponse)
                .collect(Collectors.toList());
        return new SuccessDataResult<>(blogResponses, Messages.BLOGS_LISTED_SUCCESSFULLY);
    }
    
    @Override
    public DataResult<List<BlogResponse>> searchByTag(String tag, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Blog> result = blogRepository.findByTagsContainingAndIsActiveTrue(tag, pageable);
        List<BlogResponse> blogResponses = result.getContent().stream()
                .map(blogMapper::toResponse)
                .collect(Collectors.toList());
        return new SuccessDataResult<>(blogResponses, Messages.BLOGS_LISTED_SUCCESSFULLY);
    }
} 