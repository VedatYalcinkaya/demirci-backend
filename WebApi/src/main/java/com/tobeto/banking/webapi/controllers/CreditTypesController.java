package com.tobeto.banking.webapi.controllers;

import com.tobeto.banking.business.abstracts.CreditTypeService;
import com.tobeto.banking.business.dtos.requests.CreateCreditTypeRequest;
import com.tobeto.banking.business.dtos.requests.GetCreditTypeRequest;
import com.tobeto.banking.business.dtos.requests.UpdateCreditTypeRequest;
import com.tobeto.banking.business.dtos.responses.CreditTypeResponse;
import com.tobeto.banking.business.dtos.responses.PagedResponse;
import com.tobeto.banking.entities.concretes.CreditType;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Kredi türleri için REST API controller
 */
@RestController
@RequestMapping("/api/credit-types")
@AllArgsConstructor
public class CreditTypesController {
    
    private final CreditTypeService creditTypeService;
    
    /**
     * Yeni bir kredi türü oluşturur
     * @param request Kredi türü oluşturma isteği
     * @return Oluşturulan kredi türü
     */
    @PostMapping
    public ResponseEntity<CreditTypeResponse> create(@Valid @RequestBody CreateCreditTypeRequest request) {
        CreditTypeResponse response = creditTypeService.create(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    
    /**
     * Var olan bir kredi türünü günceller
     * @param request Kredi türü güncelleme isteği
     * @return Güncellenen kredi türü
     */
    @PutMapping
    public ResponseEntity<CreditTypeResponse> update(@Valid @RequestBody UpdateCreditTypeRequest request) {
        CreditTypeResponse response = creditTypeService.update(request);
        return ResponseEntity.ok(response);
    }
    
    /**
     * ID'ye göre kredi türünü getirir
     * @param id Kredi türü ID
     * @return Kredi türü
     */
    @GetMapping("/{id}")
    public ResponseEntity<CreditTypeResponse> getById(@PathVariable Long id) {
        CreditTypeResponse response = creditTypeService.getById(id);
        return ResponseEntity.ok(response);
    }
    
    /**
     * Tüm kredi türlerini getirir
     * @return Kredi türleri listesi
     */
    @GetMapping
    public ResponseEntity<List<CreditTypeResponse>> getAll() {
        List<CreditTypeResponse> responses = creditTypeService.getAll();
        return ResponseEntity.ok(responses);
    }
    
    /**
     * Filtreye göre kredi türlerini getirir
     * @param request Filtreleme isteği
     * @return Filtrelenmiş kredi türleri listesi
     */
    @GetMapping("/filter")
    public ResponseEntity<List<CreditTypeResponse>> getAllByFilter(@RequestBody GetCreditTypeRequest request) {
        List<CreditTypeResponse> responses = creditTypeService.getAllByFilter(request);
        return ResponseEntity.ok(responses);
    }
    
    /**
     * Sayfalanmış kredi türlerini getirir
     * @param page Sayfa numarası (0'dan başlar)
     * @param size Sayfa boyutu
     * @param sort Sıralama alanı
     * @param direction Sıralama yönü (ASC, DESC)
     * @return Sayfalanmış kredi türleri
     */
    @GetMapping("/paged")
    public ResponseEntity<PagedResponse<CreditTypeResponse>> getAllPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort,
            @RequestParam(defaultValue = "ASC") String direction) {
        
        Sort.Direction sortDirection = direction.equalsIgnoreCase("DESC") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, sortDirection, sort);
        
        Page<CreditTypeResponse> pageResponse = creditTypeService.getAllPaged(pageable);
        
        PagedResponse<CreditTypeResponse> response = new PagedResponse<>();
        response.setContent(pageResponse.getContent());
        response.setPageNo(pageResponse.getNumber());
        response.setPageSize(pageResponse.getSize());
        response.setTotalElements(pageResponse.getTotalElements());
        response.setTotalPages(pageResponse.getTotalPages());
        response.setLast(pageResponse.isLast());
        response.setFirst(pageResponse.isFirst());
        response.setEmpty(pageResponse.isEmpty());
        response.setNumberOfElements(pageResponse.getNumberOfElements());
        response.setHasNext(pageResponse.hasNext());
        response.setHasPrevious(pageResponse.hasPrevious());
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * Müşteri tipine göre kredi türlerini getirir
     * @param customerType Müşteri tipi (INDIVIDUAL, CORPORATE)
     * @return Kredi türleri listesi
     */
    @GetMapping("/by-customer-type/{customerType}")
    public ResponseEntity<List<CreditTypeResponse>> getAllByCustomerType(@PathVariable CreditType.CustomerType customerType) {
        List<CreditTypeResponse> responses = creditTypeService.getAllByCustomerType(customerType);
        return ResponseEntity.ok(responses);
    }
    
    /**
     * Ana kredi türlerini alt türleriyle birlikte getirir
     * @return Ana kredi türleri listesi
     */
    @GetMapping("/parent-types")
    public ResponseEntity<List<CreditTypeResponse>> getAllParentTypes() {
        List<CreditTypeResponse> responses = creditTypeService.getAllParentTypes();
        return ResponseEntity.ok(responses);
    }
    
    /**
     * Belirli bir ana kredi türüne ait alt türleri getirir
     * @param parentId Ana kredi türü ID
     * @return Alt kredi türleri listesi
     */
    @GetMapping("/by-parent/{parentId}")
    public ResponseEntity<List<CreditTypeResponse>> getAllByParentId(@PathVariable Long parentId) {
        List<CreditTypeResponse> responses = creditTypeService.getAllByParentId(parentId);
        return ResponseEntity.ok(responses);
    }
    
    /**
     * Aktif kredi türlerini getirir
     * @return Aktif kredi türleri listesi
     */
    @GetMapping("/active")
    public ResponseEntity<List<CreditTypeResponse>> getAllActive() {
        List<CreditTypeResponse> responses = creditTypeService.getAllActive();
        return ResponseEntity.ok(responses);
    }
    
    /**
     * Kredi türünü siler (soft delete)
     * @param id Silinecek kredi türü ID
     * @return Durum mesajı
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        creditTypeService.delete(id);
        return ResponseEntity.ok("Kredi türü başarıyla silindi");
    }
} 