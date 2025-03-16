package com.demirciyazilim.webapi.controllers;

import com.demirciyazilim.business.abstracts.BlogService;
import com.demirciyazilim.business.dtos.blog.requests.CreateBlogRequest;
import com.demirciyazilim.business.dtos.blog.requests.UpdateBlogRequest;
import com.demirciyazilim.business.dtos.blog.responses.BlogResponse;
import com.demirciyazilim.core.utilities.results.DataResult;
import com.demirciyazilim.core.utilities.results.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/blogs")
@AllArgsConstructor
@Tag(name = "Blog", description = "Blog API")
public class BlogsController {

    private final BlogService blogService;

    @GetMapping
    @Operation(summary = "Get all blogs", description = "Returns all blogs")
    public ResponseEntity<DataResult<List<BlogResponse>>> getAll() {
        return ResponseEntity.ok(blogService.getAll());
    }

    @GetMapping("/active")
    @Operation(summary = "Get all active blogs", description = "Returns all active blogs with pagination")
    public ResponseEntity<DataResult<List<BlogResponse>>> getAllActive(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(blogService.getAllActive(page, size));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get blog by id", description = "Returns blog by id")
    public ResponseEntity<DataResult<BlogResponse>> getById(@PathVariable Long id) {
        DataResult<BlogResponse> result = blogService.getById(id);
        if (result.isSuccess()) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
    }

    @GetMapping("/latest/{count}")
    @Operation(summary = "Get latest blogs", description = "Returns latest blogs by count")
    public ResponseEntity<DataResult<List<BlogResponse>>> getLatestBlogs(@PathVariable int count) {
        return ResponseEntity.ok(blogService.getLatestBlogs(count));
    }

    @GetMapping("/search/title")
    @Operation(summary = "Search blogs by title", description = "Returns blogs by title search")
    public ResponseEntity<DataResult<List<BlogResponse>>> searchByTitle(
            @RequestParam String title,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(blogService.searchByTitle(title, page, size));
    }

    @GetMapping("/search/tag")
    @Operation(summary = "Search blogs by tag", description = "Returns blogs by tag search")
    public ResponseEntity<DataResult<List<BlogResponse>>> searchByTag(
            @RequestParam String tag,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(blogService.searchByTag(tag, page, size));
    }

    @PostMapping
    @Operation(summary = "Add blog", description = "Adds a new blog")
    public ResponseEntity<DataResult<BlogResponse>> add(@Valid @RequestBody CreateBlogRequest createBlogRequest) {
        DataResult<BlogResponse> result = blogService.add(createBlogRequest);
        if (result.isSuccess()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update blog", description = "Updates an existing blog")
    public ResponseEntity<DataResult<BlogResponse>> update(@PathVariable Long id, @Valid @RequestBody UpdateBlogRequest updateBlogRequest) {
        DataResult<BlogResponse> result = blogService.update(id, updateBlogRequest);
        if (result.isSuccess()) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete blog", description = "Deletes a blog by id")
    public ResponseEntity<Result> delete(@PathVariable Long id) {
        Result result = blogService.delete(id);
        if (result.isSuccess()) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
    }

    @PatchMapping("/activate/{id}")
    @Operation(summary = "Activate blog", description = "Activates a blog by id")
    public ResponseEntity<Result> activate(@PathVariable Long id) {
        Result result = blogService.activate(id);
        if (result.isSuccess()) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
    }

    @PatchMapping("/deactivate/{id}")
    @Operation(summary = "Deactivate blog", description = "Deactivates a blog by id")
    public ResponseEntity<Result> deactivate(@PathVariable Long id) {
        Result result = blogService.deactivate(id);
        if (result.isSuccess()) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
    }
}