package com.tobeto.banking.webapi.controllers;

import com.tobeto.banking.business.abstracts.CreditConditionService;
import com.tobeto.banking.business.dtos.requests.CreateCreditConditionRequest;
import com.tobeto.banking.business.dtos.requests.GetCreditConditionRequest;
import com.tobeto.banking.business.dtos.requests.UpdateCreditConditionRequest;
import com.tobeto.banking.business.dtos.responses.CreditConditionResponse;
import com.tobeto.banking.business.dtos.responses.PagedResponse;
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
 * Kredi koşulları için REST API controller
 */
@RestController
@RequestMapping("/api/credit-conditions")
@AllArgsConstructor
public class CreditConditionsController {
    
    private final CreditConditionService creditConditionService;
    
    /**
     * Yeni bir kredi koşulu oluşturur
     * @param request Kredi koşulu oluşturma isteği
     * @return Oluşturulan kredi koşulu
     */
    @PostMapping
    public ResponseEntity<CreditConditionResponse> create(@Valid @RequestBody CreateCreditConditionRequest request) {
        CreditConditionResponse response = creditConditionService.create(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    
    /**
     * Var olan bir kredi koşulunu günceller
     * @param request Kredi koşulu güncelleme isteği
     * @return Güncellenen kredi koşulu
     */
    @PutMapping
    public ResponseEntity<CreditConditionResponse> update(@Valid @RequestBody UpdateCreditConditionRequest request) {
        CreditConditionResponse response = creditConditionService.update(request);
        return ResponseEntity.ok(response);
    }
    
    /**
     * ID'ye göre kredi koşulunu getirir
     * @param id Kredi koşulu ID
     * @return Kredi koşulu
     */
    @GetMapping("/{id}")
    public ResponseEntity<CreditConditionResponse> getById(@PathVariable Long id) {
        CreditConditionResponse response = creditConditionService.getById(id);
        return ResponseEntity.ok(response);
    }
    
    /**
     * Tüm kredi koşullarını getirir
     * @return Kredi koşulları listesi
     */
    @GetMapping
    public ResponseEntity<List<CreditConditionResponse>> getAll() {
        List<CreditConditionResponse> responses = creditConditionService.getAll();
        return ResponseEntity.ok(responses);
    }
    
    /**
     * Filtreye göre kredi koşullarını getirir
     * @param request Filtreleme isteği
     * @return Filtrelenmiş kredi koşulları listesi
     */
    @GetMapping("/filter")
    public ResponseEntity<List<CreditConditionResponse>> getAllByFilter(@RequestBody GetCreditConditionRequest request) {
        List<CreditConditionResponse> responses = creditConditionService.getAllByFilter(request);
        return ResponseEntity.ok(responses);
    }
    
    /**
     * Sayfalanmış kredi koşullarını getirir
     * @param page Sayfa numarası (0'dan başlar)
     * @param size Sayfa boyutu
     * @param sort Sıralama alanı
     * @param direction Sıralama yönü (ASC, DESC)
     * @return Sayfalanmış kredi koşulları
     */
    @GetMapping("/paged")
    public ResponseEntity<PagedResponse<CreditConditionResponse>> getAllPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort,
            @RequestParam(defaultValue = "ASC") String direction) {
        
        Sort.Direction sortDirection = direction.equalsIgnoreCase("DESC") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, sortDirection, sort);
        
        Page<CreditConditionResponse> pageResponse = creditConditionService.getAllPaged(pageable);
        
        PagedResponse<CreditConditionResponse> response = new PagedResponse<>();
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
     * Kredi türü ID'sine göre koşulları getirir
     * @param creditTypeId Kredi türü ID
     * @return Koşul listesi
     */
    @GetMapping("/by-credit-type/{creditTypeId}")
    public ResponseEntity<List<CreditConditionResponse>> getAllByCreditTypeId(@PathVariable Long creditTypeId) {
        List<CreditConditionResponse> responses = creditConditionService.getAllByCreditTypeId(creditTypeId);
        return ResponseEntity.ok(responses);
    }
    
    /**
     * Koşul adına göre koşulları getirir
     * @param conditionName Koşul adı
     * @return Koşul listesi
     */
    @GetMapping("/by-condition-name")
    public ResponseEntity<List<CreditConditionResponse>> getAllByConditionName(@RequestParam String conditionName) {
        List<CreditConditionResponse> responses = creditConditionService.getAllByConditionName(conditionName);
        return ResponseEntity.ok(responses);
    }
    
    /**
     * Kredi türü ID'si ve koşul adına göre koşulları getirir
     * @param creditTypeId Kredi türü ID
     * @param conditionName Koşul adı
     * @return Koşul listesi
     */
    @GetMapping("/by-credit-type-and-condition-name")
    public ResponseEntity<List<CreditConditionResponse>> getAllByCreditTypeIdAndConditionName(
            @RequestParam Long creditTypeId, 
            @RequestParam String conditionName) {
        List<CreditConditionResponse> responses = creditConditionService.getAllByCreditTypeIdAndConditionName(creditTypeId, conditionName);
        return ResponseEntity.ok(responses);
    }
    
    /**
     * Kredi koşulunu siler
     * @param id Silinecek kredi koşulu ID
     * @return Durum mesajı
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        creditConditionService.delete(id);
        return ResponseEntity.ok("Kredi koşulu başarıyla silindi");
    }
} 