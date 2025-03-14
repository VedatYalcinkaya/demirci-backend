package com.tobeto.banking.webapi.controllers;

import com.tobeto.banking.business.abstracts.CreditApplicationService;
import com.tobeto.banking.business.dtos.requests.CreateCreditApplicationRequest;
import com.tobeto.banking.business.dtos.requests.GetCreditApplicationRequest;
import com.tobeto.banking.business.dtos.requests.UpdateCreditApplicationRequest;
import com.tobeto.banking.business.dtos.responses.CreditApplicationResponse;
import com.tobeto.banking.business.dtos.responses.PagedResponse;
import com.tobeto.banking.entities.concretes.CreditApplication;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Kredi başvuruları için REST API controller
 */
@RestController
@RequestMapping("/api/credit-applications")
@AllArgsConstructor
public class CreditApplicationsController {
    
    private final CreditApplicationService creditApplicationService;
    
    /**
     * Yeni bir kredi başvurusu oluşturur
     * @param request Kredi başvurusu oluşturma isteği
     * @return Oluşturulan kredi başvurusu
     */
    @PostMapping
    public ResponseEntity<CreditApplicationResponse> create(@Valid @RequestBody CreateCreditApplicationRequest request) {
        CreditApplicationResponse response = creditApplicationService.create(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    
    /**
     * Var olan bir kredi başvurusunu günceller
     * @param request Kredi başvurusu güncelleme isteği
     * @return Güncellenen kredi başvurusu
     */
    @PutMapping
    public ResponseEntity<CreditApplicationResponse> update(@Valid @RequestBody UpdateCreditApplicationRequest request) {
        CreditApplicationResponse response = creditApplicationService.update(request);
        return ResponseEntity.ok(response);
    }
    
    /**
     * ID'ye göre kredi başvurusunu getirir
     * @param id Kredi başvurusu ID
     * @return Kredi başvurusu
     */
    @GetMapping("/{id}")
    public ResponseEntity<CreditApplicationResponse> getById(@PathVariable Long id) {
        CreditApplicationResponse response = creditApplicationService.getById(id);
        return ResponseEntity.ok(response);
    }
    
    /**
     * Tüm kredi başvurularını getirir
     * @return Kredi başvuruları listesi
     */
    @GetMapping
    public ResponseEntity<List<CreditApplicationResponse>> getAll() {
        List<CreditApplicationResponse> responses = creditApplicationService.getAll();
        return ResponseEntity.ok(responses);
    }
    
    /**
     * Filtreye göre kredi başvurularını getirir
     * @param request Filtreleme isteği
     * @return Filtrelenmiş kredi başvuruları listesi
     */
    @GetMapping("/filter")
    public ResponseEntity<List<CreditApplicationResponse>> getAllByFilter(@RequestBody GetCreditApplicationRequest request) {
        List<CreditApplicationResponse> responses = creditApplicationService.getAllByFilter(request);
        return ResponseEntity.ok(responses);
    }
    
    /**
     * Sayfalanmış kredi başvurularını getirir
     * @param page Sayfa numarası (0'dan başlar)
     * @param size Sayfa boyutu
     * @param sort Sıralama alanı
     * @param direction Sıralama yönü (ASC, DESC)
     * @return Sayfalanmış kredi başvuruları
     */
    @GetMapping("/paged")
    public ResponseEntity<PagedResponse<CreditApplicationResponse>> getAllPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort,
            @RequestParam(defaultValue = "ASC") String direction) {
        
        Sort.Direction sortDirection = direction.equalsIgnoreCase("DESC") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, sortDirection, sort);
        
        Page<CreditApplicationResponse> pageResponse = creditApplicationService.getAllPaged(pageable);
        
        PagedResponse<CreditApplicationResponse> response = new PagedResponse<>();
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
     * Müşteri ID'sine göre kredi başvurularını getirir
     * @param customerId Müşteri ID
     * @return Kredi başvuruları listesi
     */
    @GetMapping("/by-customer/{customerId}")
    public ResponseEntity<List<CreditApplicationResponse>> getAllByCustomerId(@PathVariable Long customerId) {
        List<CreditApplicationResponse> responses = creditApplicationService.getAllByCustomerId(customerId);
        return ResponseEntity.ok(responses);
    }
    
    /**
     * Kredi türü ID'sine göre kredi başvurularını getirir
     * @param creditTypeId Kredi türü ID
     * @return Kredi başvuruları listesi
     */
    @GetMapping("/by-credit-type/{creditTypeId}")
    public ResponseEntity<List<CreditApplicationResponse>> getAllByCreditTypeId(@PathVariable Long creditTypeId) {
        List<CreditApplicationResponse> responses = creditApplicationService.getAllByCreditTypeId(creditTypeId);
        return ResponseEntity.ok(responses);
    }
    
    /**
     * Başvuru durumuna göre kredi başvurularını getirir
     * @param status Başvuru durumu
     * @return Kredi başvuruları listesi
     */
    @GetMapping("/by-status/{status}")
    public ResponseEntity<List<CreditApplicationResponse>> getAllByStatus(@PathVariable CreditApplication.ApplicationStatus status) {
        List<CreditApplicationResponse> responses = creditApplicationService.getAllByStatus(status);
        return ResponseEntity.ok(responses);
    }
    
    /**
     * Belirli bir tarih aralığında yapılan kredi başvurularını getirir
     * @param startDate Başlangıç tarihi
     * @param endDate Bitiş tarihi
     * @return Kredi başvuruları listesi
     */
    @GetMapping("/by-date-range")
    public ResponseEntity<List<CreditApplicationResponse>> getAllByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        List<CreditApplicationResponse> responses = creditApplicationService.getAllByDateRange(startDate, endDate);
        return ResponseEntity.ok(responses);
    }
    
    /**
     * Kredi başvurusunu siler
     * @param id Silinecek kredi başvurusu ID
     * @return Durum mesajı
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        creditApplicationService.delete(id);
        return ResponseEntity.ok("Kredi başvurusu başarıyla silindi");
    }
}
 