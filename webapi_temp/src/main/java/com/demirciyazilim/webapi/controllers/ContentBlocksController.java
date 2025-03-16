package com.demirciyazilim.webapi.controllers;

import com.demirciyazilim.business.abstracts.ContentBlockService;
import com.demirciyazilim.business.dtos.contentblock.requests.CreateContentBlockRequest;
import com.demirciyazilim.business.dtos.contentblock.requests.UpdateContentBlockRequest;
import com.demirciyazilim.business.dtos.contentblock.responses.ContentBlockResponse;
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
@RequestMapping("/api/v1/content-blocks")
@AllArgsConstructor
@Tag(name = "Content Block", description = "Content Block API")
public class ContentBlocksController {

    private final ContentBlockService contentBlockService;

    @GetMapping
    @Operation(summary = "Get all content blocks", description = "Returns all content blocks")
    public ResponseEntity<DataResult<List<ContentBlockResponse>>> getAll() {
        return ResponseEntity.ok(contentBlockService.getAll());
    }

    @GetMapping("/active")
    @Operation(summary = "Get all active content blocks", description = "Returns all active content blocks")
    public ResponseEntity<DataResult<List<ContentBlockResponse>>> getAllActive() {
        return ResponseEntity.ok(contentBlockService.getAllActive());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get content block by id", description = "Returns content block by id")
    public ResponseEntity<DataResult<ContentBlockResponse>> getById(@PathVariable Long id) {
        DataResult<ContentBlockResponse> result = contentBlockService.getById(id);
        if (result.isSuccess()) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
    }

    @GetMapping("/identifier/{identifier}")
    @Operation(summary = "Get content block by identifier", description = "Returns content block by identifier")
    public ResponseEntity<DataResult<ContentBlockResponse>> getByIdentifier(@PathVariable String identifier) {
        DataResult<ContentBlockResponse> result = contentBlockService.getByIdentifier(identifier);
        if (result.isSuccess()) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
    }

    @GetMapping("/type/{type}")
    @Operation(summary = "Get content blocks by type", description = "Returns content blocks by type")
    public ResponseEntity<DataResult<List<ContentBlockResponse>>> getByType(@PathVariable String type) {
        return ResponseEntity.ok(contentBlockService.getByType(type));
    }

    @PostMapping
    @Operation(summary = "Add content block", description = "Adds a new content block")
    public ResponseEntity<DataResult<ContentBlockResponse>> add(@Valid @RequestBody CreateContentBlockRequest request) {
        DataResult<ContentBlockResponse> result = contentBlockService.add(request);
        if (result.isSuccess()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }

    @PutMapping
    @Operation(summary = "Update content block", description = "Updates an existing content block")
    public ResponseEntity<DataResult<ContentBlockResponse>> update(@Valid @RequestBody UpdateContentBlockRequest request) {
        DataResult<ContentBlockResponse> result = contentBlockService.update(request);
        if (result.isSuccess()) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete content block", description = "Deletes a content block by id")
    public ResponseEntity<Result> delete(@PathVariable Long id) {
        Result result = contentBlockService.delete(id);
        if (result.isSuccess()) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
    }

    @PatchMapping("/activate/{id}")
    @Operation(summary = "Activate content block", description = "Activates a content block by id")
    public ResponseEntity<Result> activate(@PathVariable Long id) {
        Result result = contentBlockService.activate(id);
        if (result.isSuccess()) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
    }

    @PatchMapping("/deactivate/{id}")
    @Operation(summary = "Deactivate content block", description = "Deactivates a content block by id")
    public ResponseEntity<Result> deactivate(@PathVariable Long id) {
        Result result = contentBlockService.deactivate(id);
        if (result.isSuccess()) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
    }
} 