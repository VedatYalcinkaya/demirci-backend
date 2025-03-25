package com.demirciyazilim.webapi.controllers;

import com.demirciyazilim.business.abstracts.ContentBlockService;
import com.demirciyazilim.business.dtos.contentblock.requests.CreateContentBlockRequest;
import com.demirciyazilim.business.dtos.contentblock.requests.UpdateContentBlockRequest;
import com.demirciyazilim.business.dtos.contentblock.responses.ContentBlockResponse;
import com.demirciyazilim.core.utilities.results.DataResult;
import com.demirciyazilim.core.utilities.results.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/content-blocks")
@AllArgsConstructor
@Tag(name = "Content Block", description = "İçerik Bloğu API")
@CrossOrigin
public class ContentBlocksController {

    private final ContentBlockService contentBlockService;

    @GetMapping
    @Operation(summary = "Tüm içerik bloklarını getir", description = "Tüm içerik bloklarının listesini döndürür")
    public ResponseEntity<DataResult<List<ContentBlockResponse>>> getAll() {
        return ResponseEntity.ok(contentBlockService.getAll());
    }

    @GetMapping("/active")
    @Operation(summary = "Aktif içerik bloklarını getir", description = "Tüm aktif içerik bloklarının listesini döndürür")
    public ResponseEntity<DataResult<List<ContentBlockResponse>>> getAllActive() {
        return ResponseEntity.ok(contentBlockService.getAllActive());
    }

    @GetMapping("/{id}")
    @Operation(summary = "ID ile içerik bloğu getir", description = "Belirtilen ID'ye sahip içerik bloğunu getirir")
    public ResponseEntity<DataResult<ContentBlockResponse>> getById(@PathVariable Long id) {
        DataResult<ContentBlockResponse> result = contentBlockService.getById(id);
        if (result.isSuccess()) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
    }

    @GetMapping("/identifier/{identifier}")
    @Operation(summary = "Tanımlayıcı ile içerik bloğu getir", description = "Belirtilen tanımlayıcıya sahip içerik bloğunu getirir")
    public ResponseEntity<DataResult<ContentBlockResponse>> getByIdentifier(@PathVariable String identifier) {
        DataResult<ContentBlockResponse> result = contentBlockService.getByIdentifier(identifier);
        if (result.isSuccess()) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
    }

    @GetMapping("/type/{type}")
    @Operation(summary = "Türe göre içerik bloklarını getir", description = "Belirtilen türe sahip içerik bloklarının listesini döndürür")
    public ResponseEntity<DataResult<List<ContentBlockResponse>>> getByType(@PathVariable String type) {
        return ResponseEntity.ok(contentBlockService.getByType(type));
    }

    @PostMapping
    @Operation(
        summary = "İçerik bloğu ekle", 
        description = "Yeni bir içerik bloğu ekler",
        security = @SecurityRequirement(name = "bearer-key")
    )
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DataResult<ContentBlockResponse>> add(@Valid @RequestBody CreateContentBlockRequest request) {
        DataResult<ContentBlockResponse> result = contentBlockService.add(request);
        if (result.isSuccess()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }

    @PutMapping
    @Operation(
        summary = "İçerik bloğu güncelle", 
        description = "Var olan bir içerik bloğunu günceller",
        security = @SecurityRequirement(name = "bearer-key")
    )
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DataResult<ContentBlockResponse>> update(@Valid @RequestBody UpdateContentBlockRequest request) {
        DataResult<ContentBlockResponse> result = contentBlockService.update(request);
        if (result.isSuccess()) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }

    @DeleteMapping("/{id}")
    @Operation(
        summary = "İçerik bloğu sil", 
        description = "Belirtilen ID'ye sahip içerik bloğunu siler",
        security = @SecurityRequirement(name = "bearer-key")
    )
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Result> delete(@PathVariable Long id) {
        Result result = contentBlockService.delete(id);
        if (result.isSuccess()) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
    }

    @PatchMapping("/activate/{id}")
    @Operation(
        summary = "İçerik bloğu aktifleştir", 
        description = "Belirtilen ID'ye sahip içerik bloğunu aktifleştirir",
        security = @SecurityRequirement(name = "bearer-key")
    )
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Result> activate(@PathVariable Long id) {
        Result result = contentBlockService.activate(id);
        if (result.isSuccess()) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
    }

    @PatchMapping("/deactivate/{id}")
    @Operation(
        summary = "İçerik bloğu deaktifleştir", 
        description = "Belirtilen ID'ye sahip içerik bloğunu deaktifleştirir",
        security = @SecurityRequirement(name = "bearer-key")
    )
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Result> deactivate(@PathVariable Long id) {
        Result result = contentBlockService.deactivate(id);
        if (result.isSuccess()) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
    }
} 