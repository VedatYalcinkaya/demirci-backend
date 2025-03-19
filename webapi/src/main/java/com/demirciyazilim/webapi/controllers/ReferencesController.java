package com.demirciyazilim.webapi.controllers;

import com.demirciyazilim.business.abstracts.ReferenceService;
import com.demirciyazilim.business.dtos.reference.requests.CreateReferenceRequest;
import com.demirciyazilim.business.dtos.reference.requests.CreateReferenceImageRequest;
import com.demirciyazilim.business.dtos.reference.requests.UpdateReferenceRequest;
import com.demirciyazilim.business.dtos.reference.responses.ReferenceResponse;
import com.demirciyazilim.business.dtos.reference.responses.ReferenceImageResponse;
import com.demirciyazilim.core.utilities.file.CloudinaryService;
import com.demirciyazilim.core.utilities.results.DataResult;
import com.demirciyazilim.core.utilities.results.ErrorDataResult;
import com.demirciyazilim.core.utilities.results.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@RestController
@RequestMapping("/api/v1/references")
@AllArgsConstructor
@Tag(name = "Reference", description = "Reference API")
public class ReferencesController {

    private final ReferenceService referenceService;
    private final CloudinaryService cloudinaryService;

    @GetMapping
    @Operation(summary = "Get all references", description = "Returns all references")
    public ResponseEntity<DataResult<List<ReferenceResponse>>> getAll() {
        return ResponseEntity.ok(referenceService.getAll());
    }

    @GetMapping("/paginated")
    @Operation(summary = "Get all references with pagination", description = "Returns all references with pagination")
    public ResponseEntity<DataResult<List<ReferenceResponse>>> getAllPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(referenceService.getAll(page, size));
    }

    @GetMapping("/active")
    @Operation(summary = "Get all active references", description = "Returns all active references with pagination")
    public ResponseEntity<DataResult<List<ReferenceResponse>>> getAllActive(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(referenceService.getAllActive(page, size));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get reference by id", description = "Returns reference by id")
    public ResponseEntity<DataResult<ReferenceResponse>> getById(@PathVariable Long id) {
        DataResult<ReferenceResponse> result = referenceService.getById(id);
        if (result.isSuccess()) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
    }

    @GetMapping("/latest/{count}")
    @Operation(summary = "Get latest references", description = "Returns latest references by count")
    public ResponseEntity<DataResult<List<ReferenceResponse>>> getLatestReferences(@PathVariable int count) {
        return ResponseEntity.ok(referenceService.getLatestReferences(count));
    }

    @GetMapping("/search/title")
    @Operation(summary = "Search references by title", description = "Returns references by title search")
    public ResponseEntity<DataResult<List<ReferenceResponse>>> searchByTitle(
            @RequestParam String title,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(referenceService.searchByTitle(title, page, size));
    }

    @GetMapping("/search/technology")
    @Operation(summary = "Search references by technology", description = "Returns references by technology search")
    public ResponseEntity<DataResult<List<ReferenceResponse>>> searchByTechnology(
            @RequestParam String technology,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(referenceService.searchByTechnology(technology, page, size));
    }

    @PostMapping
    @Operation(summary = "Add reference", description = "Adds a new reference")
    public ResponseEntity<DataResult<ReferenceResponse>> add(@Valid @RequestBody CreateReferenceRequest request) {
        DataResult<ReferenceResponse> result = referenceService.add(request);
        if (result.isSuccess()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }

    @PutMapping
    @Operation(summary = "Update reference", description = "Updates an existing reference")
    public ResponseEntity<DataResult<ReferenceResponse>> update(@Valid @RequestBody UpdateReferenceRequest request) {
        DataResult<ReferenceResponse> result = referenceService.update(request);
        if (result.isSuccess()) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete reference", description = "Deletes a reference by id")
    public ResponseEntity<Result> delete(@PathVariable Long id) {
        Result result = referenceService.delete(id);
        if (result.isSuccess()) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
    }

    @PatchMapping("/activate/{id}")
    @Operation(summary = "Activate reference", description = "Activates a reference by id")
    public ResponseEntity<Result> activate(@PathVariable Long id) {
        Result result = referenceService.activate(id);
        if (result.isSuccess()) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
    }

    @PatchMapping("/deactivate/{id}")
    @Operation(summary = "Deactivate reference", description = "Deactivates a reference by id")
    public ResponseEntity<Result> deactivate(@PathVariable Long id) {
        Result result = referenceService.deactivate(id);
        if (result.isSuccess()) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
    }

    @GetMapping("/{referenceId}/images")
    @Operation(summary = "Get images by reference id", description = "Returns images by reference id")
    public ResponseEntity<DataResult<List<ReferenceImageResponse>>> getImagesByReferenceId(@PathVariable Long referenceId) {
        return ResponseEntity.ok(referenceService.getImagesByReferenceId(referenceId));
    }

    @PostMapping("/{referenceId}/images")
    @Operation(summary = "Add image to reference", description = "Adds a new image to reference")
    public ResponseEntity<DataResult<ReferenceImageResponse>> addImage(
            @PathVariable Long referenceId,
            @Valid @RequestBody CreateReferenceImageRequest request) {
        DataResult<ReferenceImageResponse> result = referenceService.addImage(referenceId, request);
        if (result.isSuccess()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }

    @PostMapping(value = "/{referenceId}/upload-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(
        summary = "Referans resmi yükle ve ekle", 
        description = "Cloudinary'ye resim yükler ve referansa ekler",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE)
        )
    )
    public ResponseEntity<DataResult<ReferenceImageResponse>> uploadAndAddImage(
            @PathVariable Long referenceId,
            @Parameter(description = "Yüklenecek dosya", required = true, content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE))
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "caption", required = false) String caption,
            @RequestParam(value = "displayOrder", defaultValue = "0") int displayOrder) {
        
        // Önce dosyayı Cloudinary'ye yükle
        DataResult<String> uploadResult = cloudinaryService.uploadFile(file, "references");
        
        if (!uploadResult.isSuccess()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorDataResult<>(uploadResult.getMessage()));
        }
        
        // Yüklenen dosyanın URL'ini al ve referans resmi oluştur
        String imageUrl = uploadResult.getData();
        CreateReferenceImageRequest imageRequest = new CreateReferenceImageRequest();
        imageRequest.setImageUrl(imageUrl);
        imageRequest.setCaption(caption);
        imageRequest.setDisplayOrder(displayOrder);
        imageRequest.setActive(true);
        
        // Referansa resmi ekle
        DataResult<ReferenceImageResponse> result = referenceService.addImage(referenceId, imageRequest);
        
        if (result.isSuccess()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }

    @DeleteMapping("/images/{imageId}")
    @Operation(summary = "Delete image", description = "Deletes an image by id")
    public ResponseEntity<Result> deleteImage(@PathVariable Long imageId) {
        Result result = referenceService.deleteImage(imageId);
        if (result.isSuccess()) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
    }

    @PostMapping(value = "/upload-thumbnail", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(
        summary = "Referans küçük resmi yükle", 
        description = "Cloudinary'ye referans küçük resmi (thumbnail) yükler",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE)
        )
    )
    public ResponseEntity<DataResult<String>> uploadThumbnail(
            @Parameter(description = "Yüklenecek dosya", required = true, content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE))
            @RequestParam("file") MultipartFile file) {
        DataResult<String> result = cloudinaryService.uploadFile(file, "references/thumbnails");
        if (result.isSuccess()) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }
    
    @PostMapping(value = "/upload-client-logo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(
        summary = "Müşteri logosu yükle", 
        description = "Cloudinary'ye müşteri logosu yükler",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE)
        )
    )
    public ResponseEntity<DataResult<String>> uploadClientLogo(
            @Parameter(description = "Yüklenecek dosya", required = true, content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE))
            @RequestParam("file") MultipartFile file) {
        DataResult<String> result = cloudinaryService.uploadFile(file, "references/client-logos");
        if (result.isSuccess()) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }
    
    @PostMapping(value = "/create-with-files", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(
        summary = "Dosyalarla birlikte referans oluştur", 
        description = "Thumbnail ve client logo dosyalarını yükleyerek referans oluşturur",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE)
        )
    )
    public ResponseEntity<DataResult<ReferenceResponse>> createWithFiles(
            @Parameter(description = "Referans bilgileri (JSON formatında)", required = true)
            @RequestParam("referenceData") String referenceDataJson,
            @Parameter(description = "Küçük resim dosyası", required = true, content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE))
            @RequestParam("thumbnail") MultipartFile thumbnail,
            @Parameter(description = "Müşteri logosu (opsiyonel)", required = false, content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE))
            @RequestParam(value = "clientLogo", required = false) MultipartFile clientLogo) {
        
        try {
            // Dosyaları Cloudinary'ye yükle
            DataResult<String> thumbnailResult = cloudinaryService.uploadFile(thumbnail, "references/thumbnails");
            if (!thumbnailResult.isSuccess()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ErrorDataResult<>(thumbnailResult.getMessage()));
            }
            
            String clientLogoUrl = null;
            if (clientLogo != null && !clientLogo.isEmpty()) {
                DataResult<String> logoResult = cloudinaryService.uploadFile(clientLogo, "references/client-logos");
                if (!logoResult.isSuccess()) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(new ErrorDataResult<>(logoResult.getMessage()));
                }
                clientLogoUrl = logoResult.getData();
            }
            
            // JSON verisini CreateReferenceRequest nesnesine dönüştür
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            CreateReferenceRequest request = objectMapper.readValue(referenceDataJson, CreateReferenceRequest.class);
            
            // Yüklenen dosyaların URL'lerini ayarla
            request.setThumbnailUrl(thumbnailResult.getData());
            if (clientLogoUrl != null) {
                request.setClientLogo(clientLogoUrl);
            }
            
            // Referansı ekle
            DataResult<ReferenceResponse> result = referenceService.add(request);
            if (result.isSuccess()) {
                return ResponseEntity.status(HttpStatus.CREATED).body(result);
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorDataResult<>("Referans oluşturulurken bir hata oluştu: " + e.getMessage()));
        }
    }

    @PostMapping(value = "/update-with-files", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(
        summary = "Dosyalarla birlikte referans güncelle", 
        description = "Thumbnail ve client logo dosyalarını yükleyerek referans günceller",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE)
        )
    )
    public ResponseEntity<DataResult<ReferenceResponse>> updateWithFiles(
            @Parameter(description = "Referans bilgileri (JSON formatında)", required = true)
            @RequestParam("referenceData") String referenceDataJson,
            @Parameter(description = "Küçük resim dosyası (opsiyonel)", required = false, content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE))
            @RequestParam(value = "thumbnail", required = false) MultipartFile thumbnail,
            @Parameter(description = "Müşteri logosu (opsiyonel)", required = false, content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE))
            @RequestParam(value = "clientLogo", required = false) MultipartFile clientLogo) {
        
        try {
            // JSON verisini UpdateReferenceRequest nesnesine dönüştür
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            UpdateReferenceRequest request = objectMapper.readValue(referenceDataJson, UpdateReferenceRequest.class);
            
            // Thumbnail dosyası varsa yükle ve URL'i güncelle
            if (thumbnail != null && !thumbnail.isEmpty()) {
                DataResult<String> thumbnailResult = cloudinaryService.uploadFile(thumbnail, "references/thumbnails");
                if (!thumbnailResult.isSuccess()) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(new ErrorDataResult<>(thumbnailResult.getMessage()));
                }
                request.setThumbnailUrl(thumbnailResult.getData());
            }
            
            // Client logo dosyası varsa yükle ve URL'i güncelle
            if (clientLogo != null && !clientLogo.isEmpty()) {
                DataResult<String> logoResult = cloudinaryService.uploadFile(clientLogo, "references/client-logos");
                if (!logoResult.isSuccess()) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(new ErrorDataResult<>(logoResult.getMessage()));
                }
                request.setClientLogo(logoResult.getData());
            }
            
            // Referansı güncelle
            DataResult<ReferenceResponse> result = referenceService.update(request);
            if (result.isSuccess()) {
                return ResponseEntity.ok(result);
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorDataResult<>("Referans güncellenirken bir hata oluştu: " + e.getMessage()));
        }
    }
} 