package com.tobeto.banking.webapi.controllers;

import com.tobeto.banking.business.abstracts.CorporateCustomerService;
import com.tobeto.banking.business.dtos.requests.CreateCorporateCustomerRequest;
import com.tobeto.banking.business.dtos.requests.UpdateCorporateCustomerRequest;
import com.tobeto.banking.business.dtos.responses.CorporateCustomerResponse;
import com.tobeto.banking.business.dtos.responses.PagedResponse;
import com.tobeto.banking.entities.concretes.CorporateCustomer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Kurumsal müşteri işlemleri için controller sınıfı
 */
@RestController
@RequestMapping("/api/corporate-customers")
@RequiredArgsConstructor
@Tag(name = "Kurumsal Müşteriler", description = "Kurumsal müşteri işlemleri için API")
public class CorporateCustomersController {
    
    private final CorporateCustomerService corporateCustomerService;
    
    /**
     * Tüm kurumsal müşterileri listeler
     * @return Kurumsal müşteri listesi
     */
    @GetMapping
    @Operation(summary = "Tüm kurumsal müşterileri listeler", description = "Sistemdeki tüm kurumsal müşterileri DTO formatında döner")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Müşteriler başarıyla listelendi"),
            @ApiResponse(responseCode = "500", description = "Sunucu hatası")
    })
    public ResponseEntity<List<CorporateCustomerResponse>> getAll() {
        List<CorporateCustomerResponse> customers = corporateCustomerService.getAllDto();
        return ResponseEntity.ok(customers);
    }
    
    /**
     * Sayfalanmış kurumsal müşteri listesi döner
     * @param pageNo Sayfa numarası (0 tabanlı)
     * @param pageSize Sayfa başına öğe sayısı
     * @param sortBy Sıralama alanı
     * @param sortDir Sıralama yönü (asc/desc)
     * @return Sayfalanmış kurumsal müşteri listesi
     */
    @GetMapping("/paged")
    @Operation(
        summary = "Sayfalanmış kurumsal müşteri listesi döner", 
        description = "Sistemdeki kurumsal müşterileri sayfalanmış şekilde DTO formatında döner. " +
                "Sayfa numarası 0'dan başlar. Sıralama alanı olarak herhangi bir entity alanı kullanılabilir. " +
                "Sıralama yönü olarak 'asc' (artan) veya 'desc' (azalan) kullanılabilir."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Müşteriler başarıyla listelendi"),
            @ApiResponse(responseCode = "400", description = "Geçersiz sayfalama parametreleri"),
            @ApiResponse(responseCode = "500", description = "Sunucu hatası")
    })
    public ResponseEntity<PagedResponse<CorporateCustomerResponse>> getAllPaged(
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
        
        PagedResponse<CorporateCustomerResponse> response = 
                corporateCustomerService.getAllPaged(pageNo, pageSize, sortBy, sortDir);
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * ID'ye göre kurumsal müşteri getirir
     * @param id Müşteri ID
     * @return Kurumsal müşteri
     */
    @GetMapping("/{id}")
    @Operation(summary = "ID'ye göre kurumsal müşteri getirir", description = "Belirtilen ID'ye sahip kurumsal müşteriyi DTO formatında döner")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Müşteri başarıyla bulundu"),
            @ApiResponse(responseCode = "404", description = "Müşteri bulunamadı"),
            @ApiResponse(responseCode = "500", description = "Sunucu hatası")
    })
    public ResponseEntity<CorporateCustomerResponse> getById(
            @Parameter(description = "Müşteri ID", required = true)
            @PathVariable Long id) {
        CorporateCustomerResponse customer = corporateCustomerService.getDtoById(id);
        return ResponseEntity.ok(customer);
    }
    
    /**
     * Vergi numarasına göre kurumsal müşteri getirir
     * @param taxNumber Vergi numarası
     * @return Kurumsal müşteri
     */
    @GetMapping("/by-tax-number/{taxNumber}")
    @Operation(summary = "Vergi numarasına göre kurumsal müşteri getirir", description = "Belirtilen vergi numarasına sahip kurumsal müşteriyi döner")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Müşteri başarıyla bulundu"),
            @ApiResponse(responseCode = "404", description = "Müşteri bulunamadı"),
            @ApiResponse(responseCode = "500", description = "Sunucu hatası")
    })
    public ResponseEntity<CorporateCustomer> getByTaxNumber(
            @Parameter(description = "Vergi numarası", required = true)
            @PathVariable String taxNumber) {
        Optional<CorporateCustomer> customer = corporateCustomerService.findByTaxNumber(taxNumber);
        return customer.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    /**
     * Şirket adına göre kurumsal müşteri getirir
     * @param companyName Şirket adı
     * @return Kurumsal müşteri
     */
    @GetMapping("/by-company-name/{companyName}")
    @Operation(summary = "Şirket adına göre kurumsal müşteri getirir", description = "Belirtilen şirket adına sahip kurumsal müşteriyi döner")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Müşteri başarıyla bulundu"),
            @ApiResponse(responseCode = "404", description = "Müşteri bulunamadı"),
            @ApiResponse(responseCode = "500", description = "Sunucu hatası")
    })
    public ResponseEntity<CorporateCustomer> getByCompanyName(
            @Parameter(description = "Şirket adı", required = true)
            @PathVariable String companyName) {
        Optional<CorporateCustomer> customer = corporateCustomerService.findByCompanyName(companyName);
        return customer.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    /**
     * E-posta adresine göre kurumsal müşteri getirir
     * @param email E-posta adresi
     * @return Kurumsal müşteri
     */
    @GetMapping("/by-email/{email}")
    @Operation(summary = "E-posta adresine göre kurumsal müşteri getirir", description = "Belirtilen e-posta adresine sahip kurumsal müşteriyi döner")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Müşteri başarıyla bulundu"),
            @ApiResponse(responseCode = "404", description = "Müşteri bulunamadı"),
            @ApiResponse(responseCode = "500", description = "Sunucu hatası")
    })
    public ResponseEntity<CorporateCustomer> getByEmail(
            @Parameter(description = "E-posta adresi", required = true)
            @PathVariable String email) {
        Optional<CorporateCustomer> customer = corporateCustomerService.findByEmail(email);
        return customer.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    /**
     * Sektöre göre kurumsal müşteri listesi getirir
     * @param sector Sektör
     * @return Kurumsal müşteri listesi
     */
    @GetMapping("/by-sector")
    @Operation(summary = "Sektöre göre kurumsal müşteri listesi getirir", description = "Belirtilen sektördeki kurumsal müşterileri döner")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Müşteriler başarıyla listelendi"),
            @ApiResponse(responseCode = "500", description = "Sunucu hatası")
    })
    public ResponseEntity<List<CorporateCustomer>> getBySector(
            @Parameter(description = "Sektör", required = true)
            @RequestParam String sector) {
        List<CorporateCustomer> customers = corporateCustomerService.findBySector(sector);
        return ResponseEntity.ok(customers);
    }
    
    /**
     * Çalışan sayısı aralığına göre kurumsal müşteri listesi getirir
     * @param minCount Minimum çalışan sayısı
     * @param maxCount Maksimum çalışan sayısı
     * @return Kurumsal müşteri listesi
     */
    @GetMapping("/by-employee-count")
    @Operation(summary = "Çalışan sayısı aralığına göre kurumsal müşteri listesi getirir", description = "Belirtilen çalışan sayısı aralığındaki kurumsal müşterileri döner")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Müşteriler başarıyla listelendi"),
            @ApiResponse(responseCode = "500", description = "Sunucu hatası")
    })
    public ResponseEntity<List<CorporateCustomer>> getByEmployeeCountBetween(
            @Parameter(description = "Minimum çalışan sayısı", required = true)
            @RequestParam int minCount,
            @Parameter(description = "Maksimum çalışan sayısı", required = true)
            @RequestParam int maxCount) {
        List<CorporateCustomer> customers = corporateCustomerService.findByEmployeeCountBetween(minCount, maxCount);
        return ResponseEntity.ok(customers);
    }
    
    /**
     * Şirket adına göre kurumsal müşteri arar
     * @param keyword Arama kelimesi
     * @return Kurumsal müşteri listesi
     */
    @GetMapping("/search")
    @Operation(summary = "Şirket adına göre kurumsal müşteri arar", description = "Belirtilen kelimeyi şirket adında içeren kurumsal müşterileri döner")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Müşteriler başarıyla listelendi"),
            @ApiResponse(responseCode = "500", description = "Sunucu hatası")
    })
    public ResponseEntity<List<CorporateCustomer>> searchByCompanyName(
            @Parameter(description = "Arama kelimesi", required = true)
            @RequestParam String keyword) {
        List<CorporateCustomer> customers = corporateCustomerService.searchByCompanyName(keyword);
        return ResponseEntity.ok(customers);
    }
    
    /**
     * Belirli bir yıldan sonra kurulmuş kurumsal müşterileri getirir
     * @param year Kuruluş yılı
     * @return Kurumsal müşteri listesi
     */
    @GetMapping("/by-foundation-year")
    @Operation(summary = "Belirli bir yıldan sonra kurulmuş kurumsal müşterileri getirir", description = "Belirtilen yıldan sonra kurulmuş kurumsal müşterileri döner")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Müşteriler başarıyla listelendi"),
            @ApiResponse(responseCode = "500", description = "Sunucu hatası")
    })
    public ResponseEntity<List<CorporateCustomer>> getByFoundationYearGreaterThanEqual(
            @Parameter(description = "Kuruluş yılı", required = true)
            @RequestParam int year) {
        List<CorporateCustomer> customers = corporateCustomerService.findByFoundationYearGreaterThanEqual(year);
        return ResponseEntity.ok(customers);
    }
    
    /**
     * Sektör ve minimum çalışan sayısına göre kurumsal müşteri listesi getirir
     * @param sector Sektör
     * @param minEmployeeCount Minimum çalışan sayısı
     * @return Kurumsal müşteri listesi
     */
    @GetMapping("/by-sector-and-min-employee-count")
    @Operation(summary = "Sektör ve minimum çalışan sayısına göre kurumsal müşteri listesi getirir", description = "Belirtilen sektördeki ve minimum çalışan sayısına sahip kurumsal müşterileri döner")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Müşteriler başarıyla listelendi"),
            @ApiResponse(responseCode = "500", description = "Sunucu hatası")
    })
    public ResponseEntity<List<CorporateCustomer>> getBySectorAndMinEmployeeCount(
            @Parameter(description = "Sektör", required = true)
            @RequestParam String sector,
            @Parameter(description = "Minimum çalışan sayısı", required = true)
            @RequestParam int minEmployeeCount) {
        List<CorporateCustomer> customers = corporateCustomerService.findBySectorAndMinEmployeeCount(sector, minEmployeeCount);
        return ResponseEntity.ok(customers);
    }
    
    /**
     * Yeni kurumsal müşteri ekler
     * @param request Kurumsal müşteri ekleme isteği
     * @return Eklenen kurumsal müşteri
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Yeni kurumsal müşteri ekler", description = "Yeni bir kurumsal müşteri ekler ve eklenen müşteriyi DTO formatında döner")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Müşteri başarıyla eklendi",
                    content = @Content(schema = @Schema(implementation = CorporateCustomerResponse.class))),
            @ApiResponse(responseCode = "400", description = "Geçersiz istek"),
            @ApiResponse(responseCode = "500", description = "Sunucu hatası")
    })
    public ResponseEntity<CorporateCustomerResponse> add(
            @Parameter(description = "Kurumsal müşteri ekleme isteği", required = true)
            @Valid @RequestBody CreateCorporateCustomerRequest request) {
        CorporateCustomerResponse response = corporateCustomerService.addDto(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    /**
     * Kurumsal müşteri günceller
     * @param request Kurumsal müşteri güncelleme isteği
     * @return Güncellenen kurumsal müşteri
     */
    @PutMapping
    @Operation(summary = "Kurumsal müşteri günceller", description = "Mevcut bir kurumsal müşteriyi günceller ve güncellenen müşteriyi DTO formatında döner")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Müşteri başarıyla güncellendi"),
            @ApiResponse(responseCode = "400", description = "Geçersiz istek"),
            @ApiResponse(responseCode = "404", description = "Müşteri bulunamadı"),
            @ApiResponse(responseCode = "500", description = "Sunucu hatası")
    })
    public ResponseEntity<CorporateCustomerResponse> update(
            @Parameter(description = "Kurumsal müşteri güncelleme isteği", required = true)
            @Valid @RequestBody UpdateCorporateCustomerRequest request) {
        CorporateCustomerResponse response = corporateCustomerService.updateDto(request);
        return ResponseEntity.ok(response);
    }
    
    /**
     * Kurumsal müşteri siler
     * @param id Müşteri ID
     * @return Boş yanıt
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Kurumsal müşteri siler", description = "Belirtilen ID'ye sahip kurumsal müşteriyi siler")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Müşteri başarıyla silindi"),
            @ApiResponse(responseCode = "404", description = "Müşteri bulunamadı"),
            @ApiResponse(responseCode = "500", description = "Sunucu hatası")
    })
    public ResponseEntity<Void> delete(
            @Parameter(description = "Müşteri ID", required = true)
            @PathVariable Long id) {
        corporateCustomerService.delete(id);
        return ResponseEntity.noContent().build();
    }
} 