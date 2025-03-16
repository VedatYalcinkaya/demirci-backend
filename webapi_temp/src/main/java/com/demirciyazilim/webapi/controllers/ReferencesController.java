package com.demirciyazilim.webapi.controllers;

import com.demirciyazilim.business.abstracts.ReferenceService;
import com.demirciyazilim.business.dtos.reference.requests.CreateReferenceRequest;
import com.demirciyazilim.business.dtos.reference.requests.CreateReferenceImageRequest;
import com.demirciyazilim.business.dtos.reference.requests.UpdateReferenceRequest;
import com.demirciyazilim.business.dtos.reference.responses.ReferenceResponse;
import com.demirciyazilim.business.dtos.reference.responses.ReferenceImageResponse;
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
@RequestMapping("/api/v1/references")
@AllArgsConstructor
@Tag(name = "Reference", description = "Reference API")
public class ReferencesController {

    private final ReferenceService referenceService;

    @GetMapping
    @Operation(summary = "Get all references", description = "Returns all references")
    public ResponseEntity<DataResult<List<ReferenceResponse>>> getAll() {
        return ResponseEntity.ok(referenceService.getAll());
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

    @DeleteMapping("/images/{imageId}")
    @Operation(summary = "Delete image", description = "Deletes an image by id")
    public ResponseEntity<Result> deleteImage(@PathVariable Long imageId) {
        Result result = referenceService.deleteImage(imageId);
        if (result.isSuccess()) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
    }
} 