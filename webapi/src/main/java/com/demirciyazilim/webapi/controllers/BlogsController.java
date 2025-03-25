package com.demirciyazilim.webapi.controllers;

import com.demirciyazilim.business.abstracts.BlogService;
import com.demirciyazilim.business.dtos.blog.requests.CreateBlogRequest;
import com.demirciyazilim.business.dtos.blog.requests.UpdateBlogRequest;
import com.demirciyazilim.business.dtos.blog.responses.BlogResponse;
import com.demirciyazilim.core.utilities.file.CloudinaryService;
import com.demirciyazilim.core.utilities.results.DataResult;
import com.demirciyazilim.core.utilities.results.ErrorDataResult;
import com.demirciyazilim.core.utilities.results.Result;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/blogs")
@AllArgsConstructor
@Tag(name = "Blog", description = "Blog API")
@CrossOrigin
public class BlogsController {

    private final BlogService blogService;
    private final CloudinaryService cloudinaryService;

    @PostMapping(value = "/upload-thumbnail", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(
        summary = "Blog küçük resmi yükle", 
        description = "Cloudinary'ye blog küçük resmi (thumbnail) yükler",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE)
        ),
        security = @SecurityRequirement(name = "bearer-key")
    )
    @PreAuthorize("hasAnyRole('ADMIN', 'EDITOR')")
    public ResponseEntity<DataResult<String>> uploadThumbnail(
            @Parameter(description = "Yüklenecek dosya", required = true, content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE))
            @RequestParam("file") MultipartFile file) {
        DataResult<String> result = cloudinaryService.uploadFile(file, "blogs/thumbnails");
        if (result.isSuccess()) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }
    
    @PostMapping(value = "/create-with-thumbnail", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(
        summary = "Blog oluştur ve thumbnail yükle", 
        description = "Yeni bir blog oluşturur ve thumbnail resmini Cloudinary'ye yükler",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE)
        ),
        security = @SecurityRequirement(name = "bearer-key")
    )
    @PreAuthorize("hasAnyRole('ADMIN', 'EDITOR')")
    public ResponseEntity<DataResult<BlogResponse>> createWithThumbnail(
            @Parameter(description = "Blog bilgileri (JSON formatında)", required = true)
            @RequestParam("blogData") String blogDataJson,
            @Parameter(description = "Thumbnail resmi", required = true, content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE))
            @RequestParam("thumbnail") MultipartFile thumbnail) {
        
        try {
            // Thumbnail'i Cloudinary'ye yükle
            DataResult<String> thumbnailResult = cloudinaryService.uploadFile(thumbnail, "blogs/thumbnails");
            
            if (!thumbnailResult.isSuccess()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ErrorDataResult<>(thumbnailResult.getMessage()));
            }
            
            // JSON verisini CreateBlogRequest nesnesine dönüştür
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            CreateBlogRequest createBlogRequest = objectMapper.readValue(blogDataJson, CreateBlogRequest.class);
            
            // Thumbnail URL'sini ayarla
            createBlogRequest.setThumbnailUrl(thumbnailResult.getData());
            
            // Blog'u veritabanına ekle
            DataResult<BlogResponse> result = blogService.add(createBlogRequest);
            
            if (result.isSuccess()) {
                return ResponseEntity.status(HttpStatus.CREATED).body(result);
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorDataResult<>("Blog oluşturulurken bir hata oluştu: " + e.getMessage()));
        }
    }
    
    @PostMapping(value = "/{id}/update-with-thumbnail", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(
        summary = "Blog güncelle ve thumbnail yükle", 
        description = "Var olan bir blogu günceller ve yeni thumbnail resmini Cloudinary'ye yükler",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE)
        ),
        security = @SecurityRequirement(name = "bearer-key")
    )
    @PreAuthorize("hasAnyRole('ADMIN', 'EDITOR')")
    public ResponseEntity<DataResult<BlogResponse>> updateWithThumbnail(
            @PathVariable Long id,
            @Parameter(description = "Blog bilgileri (JSON formatında)", required = true)
            @RequestParam("blogData") String blogDataJson,
            @Parameter(description = "Thumbnail resmi", required = false, content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE))
            @RequestParam(value = "thumbnail", required = false) MultipartFile thumbnail) {
        
        try {
            // JSON verisini UpdateBlogRequest nesnesine dönüştür
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            UpdateBlogRequest updateBlogRequest = objectMapper.readValue(blogDataJson, UpdateBlogRequest.class);
            
            // ID path variable'dan al ve ilgili yere ekle
            updateBlogRequest.setId(id);
            
            // Eğer yeni bir thumbnail yüklenmişse Cloudinary'ye yükle
            if (thumbnail != null && !thumbnail.isEmpty()) {
                DataResult<String> thumbnailResult = cloudinaryService.uploadFile(thumbnail, "blogs/thumbnails");
                
                if (!thumbnailResult.isSuccess()) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(new ErrorDataResult<>(thumbnailResult.getMessage()));
                }
                
                updateBlogRequest.setThumbnailUrl(thumbnailResult.getData());
            }
            
            // Blog'u güncelle
            DataResult<BlogResponse> result = blogService.update(id, updateBlogRequest);
            
            if (result.isSuccess()) {
                return ResponseEntity.ok(result);
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorDataResult<>("Blog güncellenirken bir hata oluştu: " + e.getMessage()));
        }
    }

    @GetMapping
    @Operation(summary = "Tüm blogları getir", description = "Tüm blogların listesini döndürür")
    public ResponseEntity<DataResult<List<BlogResponse>>> getAll() {
        return ResponseEntity.ok(blogService.getAll());
    }

    @GetMapping("/paginated")
    @Operation(summary = "Tüm blogları sayfalı getir", description = "Tüm blogları sayfalama ile döndürür")
    public ResponseEntity<DataResult<List<BlogResponse>>> getAllPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(blogService.getAll(page, size));
    }

    @GetMapping("/active")
    @Operation(summary = "Aktif blogları getir", description = "Aktif blogları sayfalama ile döndürür")
    public ResponseEntity<DataResult<List<BlogResponse>>> getAllActive(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(blogService.getAllActive(page, size));
    }

    @GetMapping("/{id}")
    @Operation(summary = "ID ile blog getir", description = "Belirtilen ID'ye sahip blogu getirir")
    public ResponseEntity<DataResult<BlogResponse>> getById(@PathVariable Long id) {
        DataResult<BlogResponse> result = blogService.getById(id);
        if (result.isSuccess()) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
    }

    @GetMapping("/slug/{slug}")
    @Operation(summary = "Slug ile blog getir", description = "Belirtilen slug'a sahip blogu getirir")
    public ResponseEntity<DataResult<BlogResponse>> getBySlug(@PathVariable String slug) {
        DataResult<BlogResponse> result = blogService.getBySlug(slug);
        if (result.isSuccess()) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
    }

    @GetMapping("/latest/{count}")
    @Operation(summary = "En son blogları getir", description = "Belirtilen sayıda en son eklenen blogları getirir")
    public ResponseEntity<DataResult<List<BlogResponse>>> getLatestBlogs(@PathVariable int count) {
        return ResponseEntity.ok(blogService.getLatestBlogs(count));
    }

    @GetMapping("/search/title")
    @Operation(summary = "Başlığa göre blog ara", description = "Başlıkta arama yaparak blogları döndürür")
    public ResponseEntity<DataResult<List<BlogResponse>>> searchByTitle(
            @RequestParam String title,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(blogService.searchByTitle(title, page, size));
    }

    @GetMapping("/search/tag")
    @Operation(summary = "Etikete göre blog ara", description = "Etikette arama yaparak blogları döndürür")
    public ResponseEntity<DataResult<List<BlogResponse>>> searchByTag(
            @RequestParam String tag,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(blogService.searchByTag(tag, page, size));
    }

    @PostMapping
    @Operation(
        summary = "Blog ekle", 
        description = "Yeni bir blog ekler",
        security = @SecurityRequirement(name = "bearer-key")
    )
    @PreAuthorize("hasAnyRole('ADMIN', 'EDITOR')")
    public ResponseEntity<DataResult<BlogResponse>> add(@Valid @RequestBody CreateBlogRequest createBlogRequest) {
        DataResult<BlogResponse> result = blogService.add(createBlogRequest);
        if (result.isSuccess()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }

    @PutMapping("/{id}")
    @Operation(
        summary = "Blog güncelle", 
        description = "Var olan bir blogu günceller",
        security = @SecurityRequirement(name = "bearer-key")
    )
    @PreAuthorize("hasAnyRole('ADMIN', 'EDITOR')")
    public ResponseEntity<DataResult<BlogResponse>> update(@PathVariable Long id, @Valid @RequestBody UpdateBlogRequest updateBlogRequest) {
        DataResult<BlogResponse> result = blogService.update(id, updateBlogRequest);
        if (result.isSuccess()) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }

    @DeleteMapping("/{id}")
    @Operation(
        summary = "Blog sil", 
        description = "Belirtilen ID'ye sahip blogu siler",
        security = @SecurityRequirement(name = "bearer-key")
    )
    @PreAuthorize("hasAnyRole('ADMIN', 'EDITOR')")
    public ResponseEntity<Result> delete(@PathVariable Long id) {
        Result result = blogService.delete(id);
        if (result.isSuccess()) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
    }

    @PatchMapping("/activate/{id}")
    @Operation(
        summary = "Blog aktifleştir", 
        description = "Belirtilen ID'ye sahip blogu aktifleştirir",
        security = @SecurityRequirement(name = "bearer-key")
    )
    @PreAuthorize("hasAnyRole('ADMIN', 'EDITOR')")
    public ResponseEntity<Result> activate(@PathVariable Long id) {
        Result result = blogService.activate(id);
        if (result.isSuccess()) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
    }

    @PatchMapping("/deactivate/{id}")
    @Operation(
        summary = "Blog deaktifleştir", 
        description = "Belirtilen ID'ye sahip blogu deaktifleştirir",
        security = @SecurityRequirement(name = "bearer-key")
    )
    @PreAuthorize("hasAnyRole('ADMIN', 'EDITOR')")
    public ResponseEntity<Result> deactivate(@PathVariable Long id) {
        Result result = blogService.deactivate(id);
        if (result.isSuccess()) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
    }
}