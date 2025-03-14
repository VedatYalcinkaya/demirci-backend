package com.tobeto.banking.webapi.controllers;

import com.tobeto.banking.business.abstracts.CustomerService;
import com.tobeto.banking.business.dtos.responses.CustomerResponse;
import com.tobeto.banking.business.dtos.responses.PagedResponse;
import com.tobeto.banking.entities.concretes.Customer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Tüm müşteri işlemleri için controller sınıfı
 */
@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
@Tag(name = "Müşteriler", description = "Tüm müşteri işlemleri için API")
public class CustomersController {
    
    private final CustomerService customerService;
    
    /**
     * Tüm müşterileri listeler
     * @return Müşteri listesi
     */
    @GetMapping
    @Operation(summary = "Tüm müşterileri listeler", description = "Sistemdeki tüm müşterileri (bireysel ve kurumsal) DTO formatında döner")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Müşteriler başarıyla listelendi"),
            @ApiResponse(responseCode = "500", description = "Sunucu hatası")
    })
    public ResponseEntity<List<CustomerResponse>> getAll() {
        List<CustomerResponse> customers = customerService.getAllDto();
        return ResponseEntity.ok(customers);
    }
    
    /**
     * Sayfalanmış müşteri listesi döner
     * @param pageNo Sayfa numarası (0 tabanlı)
     * @param pageSize Sayfa başına öğe sayısı
     * @param sortBy Sıralama alanı
     * @param sortDir Sıralama yönü (asc/desc)
     * @return Sayfalanmış müşteri listesi
     */
    @GetMapping("/paged")
    @Operation(
        summary = "Sayfalanmış müşteri listesi döner", 
        description = "Sistemdeki tüm müşterileri (bireysel ve kurumsal) sayfalanmış şekilde DTO formatında döner. " +
                "Sayfa numarası 0'dan başlar. Sıralama alanı olarak herhangi bir entity alanı kullanılabilir. " +
                "Sıralama yönü olarak 'asc' (artan) veya 'desc' (azalan) kullanılabilir."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Müşteriler başarıyla listelendi"),
            @ApiResponse(responseCode = "400", description = "Geçersiz sayfalama parametreleri"),
            @ApiResponse(responseCode = "500", description = "Sunucu hatası")
    })
    public ResponseEntity<PagedResponse<CustomerResponse>> getAllPaged(
            @Parameter(description = "Sayfa numarası (0 tabanlı)", example = "0")
            @RequestParam(value = "pageNo", defaultValue = "0") int pageNo,
            
            @Parameter(description = "Sayfa başına öğe sayısı", example = "10")
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
            
            @Parameter(description = "Sıralama alanı", example = "id")
            @RequestParam(value = "sortBy", defaultValue = "id") String sortBy,
            
            @Parameter(description = "Sıralama yönü (asc/desc)", example = "asc")
            @RequestParam(value = "sortDir", defaultValue = "asc") String sortDir) {
        
        // Sayfa numarası ve boyutu için validasyon
        if (pageNo < 0) {
            pageNo = 0;
        }
        
        if (pageSize < 1 || pageSize > 100) {
            pageSize = 10;
        }
        
        // Sıralama yönü için validasyon
        if (!sortDir.equalsIgnoreCase("asc") && !sortDir.equalsIgnoreCase("desc")) {
            sortDir = "asc";
        }
        
        PagedResponse<CustomerResponse> response = 
                customerService.getAllPaged(pageNo, pageSize, sortBy, sortDir);
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * ID'ye göre müşteri getirir
     * @param id Müşteri ID
     * @return Müşteri
     */
    @GetMapping("/{id}")
    @Operation(summary = "ID'ye göre müşteri getirir", description = "Belirtilen ID'ye sahip müşteriyi DTO formatında döner")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Müşteri başarıyla bulundu"),
            @ApiResponse(responseCode = "404", description = "Müşteri bulunamadı"),
            @ApiResponse(responseCode = "500", description = "Sunucu hatası")
    })
    public ResponseEntity<CustomerResponse> getById(
            @Parameter(description = "Müşteri ID", required = true)
            @PathVariable Long id) {
        CustomerResponse customer = customerService.getDtoById(id);
        return ResponseEntity.ok(customer);
    }
    
    /**
     * E-posta adresine göre müşteri getirir
     * @param email E-posta adresi
     * @return Müşteri
     */
    @GetMapping("/by-email/{email}")
    @Operation(summary = "E-posta adresine göre müşteri getirir", description = "Belirtilen e-posta adresine sahip müşteriyi döner")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Müşteri başarıyla bulundu"),
            @ApiResponse(responseCode = "404", description = "Müşteri bulunamadı"),
            @ApiResponse(responseCode = "500", description = "Sunucu hatası")
    })
    public ResponseEntity<Customer> getByEmail(
            @Parameter(description = "E-posta adresi", required = true)
            @PathVariable String email) {
        Optional<Customer> customer = customerService.findByEmail(email);
        return customer.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
} 