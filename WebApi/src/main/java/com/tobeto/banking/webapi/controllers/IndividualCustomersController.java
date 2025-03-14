package com.tobeto.banking.webapi.controllers;

import com.tobeto.banking.business.abstracts.IndividualCustomerService;
import com.tobeto.banking.business.dtos.requests.CreateIndividualCustomerRequest;
import com.tobeto.banking.business.dtos.requests.UpdateIndividualCustomerRequest;
import com.tobeto.banking.business.dtos.responses.IndividualCustomerResponse;
import com.tobeto.banking.business.dtos.responses.PagedResponse;
import com.tobeto.banking.entities.concretes.IndividualCustomer;
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
 * Bireysel müşteri işlemleri için controller sınıfı
 */
@RestController
@RequestMapping("/api/individual-customers")
@RequiredArgsConstructor
@Tag(name = "Bireysel Müşteriler", description = "Bireysel müşteri işlemleri için API")
public class IndividualCustomersController {
    
    private final IndividualCustomerService individualCustomerService;
    
    /**
     * Tüm bireysel müşterileri listeler
     * @return Bireysel müşteri listesi
     */
    @GetMapping
    @Operation(summary = "Tüm bireysel müşterileri listeler", description = "Sistemdeki tüm bireysel müşterileri DTO formatında döner")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Müşteriler başarıyla listelendi"),
            @ApiResponse(responseCode = "500", description = "Sunucu hatası")
    })
    public ResponseEntity<List<IndividualCustomerResponse>> getAll() {
        List<IndividualCustomerResponse> customers = individualCustomerService.getAllDto();
        return ResponseEntity.ok(customers);
    }
    
    /**
     * Sayfalanmış bireysel müşteri listesi döner
     * @param pageNo Sayfa numarası (0 tabanlı)
     * @param pageSize Sayfa başına öğe sayısı
     * @param sortBy Sıralama alanı
     * @param sortDir Sıralama yönü (asc/desc)
     * @return Sayfalanmış bireysel müşteri listesi
     */
    @GetMapping("/paged")
    @Operation(
        summary = "Sayfalanmış bireysel müşteri listesi döner", 
        description = "Sistemdeki bireysel müşterileri sayfalanmış şekilde DTO formatında döner. " +
                "Sayfa numarası 0'dan başlar. Sıralama alanı olarak herhangi bir entity alanı kullanılabilir. " +
                "Sıralama yönü olarak 'asc' (artan) veya 'desc' (azalan) kullanılabilir."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Müşteriler başarıyla listelendi"),
            @ApiResponse(responseCode = "400", description = "Geçersiz sayfalama parametreleri"),
            @ApiResponse(responseCode = "500", description = "Sunucu hatası")
    })
    public ResponseEntity<PagedResponse<IndividualCustomerResponse>> getAllPaged(
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
        
        PagedResponse<IndividualCustomerResponse> response = 
                individualCustomerService.getAllPaged(pageNo, pageSize, sortBy, sortDir);
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * ID'ye göre bireysel müşteri getirir
     * @param id Müşteri ID
     * @return Bireysel müşteri
     */
    @GetMapping("/{id}")
    @Operation(summary = "ID'ye göre bireysel müşteri getirir", description = "Belirtilen ID'ye sahip bireysel müşteriyi DTO formatında döner")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Müşteri başarıyla bulundu"),
            @ApiResponse(responseCode = "404", description = "Müşteri bulunamadı"),
            @ApiResponse(responseCode = "500", description = "Sunucu hatası")
    })
    public ResponseEntity<IndividualCustomerResponse> getById(
            @Parameter(description = "Müşteri ID", required = true)
            @PathVariable Long id) {
        IndividualCustomerResponse customer = individualCustomerService.getDtoById(id);
        return ResponseEntity.ok(customer);
    }
    
    /**
     * TC kimlik numarasına göre bireysel müşteri getirir
     * @param identityNumber TC kimlik numarası
     * @return Bireysel müşteri
     */
    @GetMapping("/by-identity-number/{identityNumber}")
    @Operation(summary = "TC kimlik numarasına göre bireysel müşteri getirir", description = "Belirtilen TC kimlik numarasına sahip bireysel müşteriyi döner")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Müşteri başarıyla bulundu"),
            @ApiResponse(responseCode = "404", description = "Müşteri bulunamadı"),
            @ApiResponse(responseCode = "500", description = "Sunucu hatası")
    })
    public ResponseEntity<IndividualCustomer> getByIdentityNumber(
            @Parameter(description = "TC kimlik numarası", required = true)
            @PathVariable String identityNumber) {
        Optional<IndividualCustomer> customer = individualCustomerService.findByIdentityNumber(identityNumber);
        return customer.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    /**
     * E-posta adresine göre bireysel müşteri getirir
     * @param email E-posta adresi
     * @return Bireysel müşteri
     */
    @GetMapping("/by-email/{email}")
    @Operation(summary = "E-posta adresine göre bireysel müşteri getirir", description = "Belirtilen e-posta adresine sahip bireysel müşteriyi döner")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Müşteri başarıyla bulundu"),
            @ApiResponse(responseCode = "404", description = "Müşteri bulunamadı"),
            @ApiResponse(responseCode = "500", description = "Sunucu hatası")
    })
    public ResponseEntity<IndividualCustomer> getByEmail(
            @Parameter(description = "E-posta adresi", required = true)
            @PathVariable String email) {
        Optional<IndividualCustomer> customer = individualCustomerService.findByEmail(email);
        return customer.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    /**
     * Ad ve soyada göre bireysel müşteri listesi getirir
     * @param firstName Ad
     * @param lastName Soyad
     * @return Bireysel müşteri listesi
     */
    @GetMapping("/by-name")
    @Operation(summary = "Ad ve soyada göre bireysel müşteri listesi getirir", description = "Belirtilen ad ve soyada sahip bireysel müşterileri döner")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Müşteriler başarıyla listelendi"),
            @ApiResponse(responseCode = "500", description = "Sunucu hatası")
    })
    public ResponseEntity<List<IndividualCustomer>> getByFirstNameAndLastName(
            @Parameter(description = "Ad", required = true)
            @RequestParam String firstName,
            @Parameter(description = "Soyad", required = true)
            @RequestParam String lastName) {
        List<IndividualCustomer> customers = individualCustomerService.findByFirstNameAndLastName(firstName, lastName);
        return ResponseEntity.ok(customers);
    }
    
    /**
     * Yeni bireysel müşteri ekler
     * @param request Müşteri ekleme isteği
     * @return Eklenen müşteri
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Yeni bireysel müşteri ekler", description = "Yeni bir bireysel müşteri ekler ve eklenen müşteriyi DTO formatında döner")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Müşteri başarıyla eklendi",
                    content = @Content(schema = @Schema(implementation = IndividualCustomerResponse.class))),
            @ApiResponse(responseCode = "400", description = "Geçersiz istek"),
            @ApiResponse(responseCode = "500", description = "Sunucu hatası")
    })
    public ResponseEntity<IndividualCustomerResponse> add(
            @Parameter(description = "Müşteri ekleme isteği", required = true)
            @Valid @RequestBody CreateIndividualCustomerRequest request) {
        IndividualCustomerResponse response = individualCustomerService.addDto(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    /**
     * Bireysel müşteri günceller
     * @param request Müşteri güncelleme isteği
     * @return Güncellenen müşteri
     */
    @PutMapping
    @Operation(summary = "Bireysel müşteri günceller", description = "Mevcut bir bireysel müşteriyi günceller ve güncellenen müşteriyi DTO formatında döner")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Müşteri başarıyla güncellendi"),
            @ApiResponse(responseCode = "400", description = "Geçersiz istek"),
            @ApiResponse(responseCode = "404", description = "Müşteri bulunamadı"),
            @ApiResponse(responseCode = "500", description = "Sunucu hatası")
    })
    public ResponseEntity<IndividualCustomerResponse> update(
            @Parameter(description = "Müşteri güncelleme isteği", required = true)
            @Valid @RequestBody UpdateIndividualCustomerRequest request) {
        IndividualCustomerResponse response = individualCustomerService.updateDto(request);
        return ResponseEntity.ok(response);
    }
    
    /**
     * Bireysel müşteri siler
     * @param id Müşteri ID
     * @return Boş yanıt
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Bireysel müşteri siler", description = "Belirtilen ID'ye sahip bireysel müşteriyi siler")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Müşteri başarıyla silindi"),
            @ApiResponse(responseCode = "404", description = "Müşteri bulunamadı"),
            @ApiResponse(responseCode = "500", description = "Sunucu hatası")
    })
    public ResponseEntity<Void> delete(
            @Parameter(description = "Müşteri ID", required = true)
            @PathVariable Long id) {
        individualCustomerService.delete(id);
        return ResponseEntity.noContent().build();
    }
} 