package com.tobeto.banking.webapi.controllers;

import com.tobeto.banking.business.abstracts.CorporateCustomerService;
import com.tobeto.banking.business.abstracts.CustomerService;
import com.tobeto.banking.business.abstracts.IndividualCustomerService;
import com.tobeto.banking.entities.dtos.CorporateCustomerDto;
import com.tobeto.banking.entities.dtos.CustomerDto;
import com.tobeto.banking.entities.dtos.IndividualCustomerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Müşteri işlemleri için REST API controller
 */
@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomersController {
    
    private final CustomerService customerService;
    private final IndividualCustomerService individualCustomerService;
    private final CorporateCustomerService corporateCustomerService;
    
    /**
     * Tüm müşterileri listeler
     * @return Müşteri listesi
     */
    @GetMapping
    public ResponseEntity<List<CustomerDto>> getAllCustomers() {
        return ResponseEntity.ok(customerService.getAllDto());
    }
    
    /**
     * ID'ye göre müşteri getirir
     * @param id Müşteri ID
     * @return Müşteri
     */
    @GetMapping("/{id}")
    public ResponseEntity<CustomerDto> getCustomerById(@PathVariable Long id) {
        return ResponseEntity.ok(customerService.getDtoById(id));
    }
    
    /**
     * Tüm bireysel müşterileri listeler
     * @return Bireysel müşteri listesi
     */
    @GetMapping("/individual")
    public ResponseEntity<List<IndividualCustomerDto>> getAllIndividualCustomers() {
        return ResponseEntity.ok(individualCustomerService.getAllDto());
    }
    
    /**
     * ID'ye göre bireysel müşteri getirir
     * @param id Müşteri ID
     * @return Bireysel müşteri
     */
    @GetMapping("/individual/{id}")
    public ResponseEntity<IndividualCustomerDto> getIndividualCustomerById(@PathVariable Long id) {
        return ResponseEntity.ok(individualCustomerService.getDtoById(id));
    }
    
    /**
     * Yeni bireysel müşteri ekler
     * @param customerDto Eklenecek müşteri
     * @return Eklenen müşteri
     */
    @PostMapping("/individual")
    public ResponseEntity<IndividualCustomerDto> addIndividualCustomer(@RequestBody IndividualCustomerDto customerDto) {
        return new ResponseEntity<>(individualCustomerService.addDto(customerDto), HttpStatus.CREATED);
    }
    
    /**
     * Bireysel müşteri günceller
     * @param id Müşteri ID
     * @param customerDto Güncellenecek müşteri
     * @return Güncellenen müşteri
     */
    @PutMapping("/individual/{id}")
    public ResponseEntity<IndividualCustomerDto> updateIndividualCustomer(@PathVariable Long id, @RequestBody IndividualCustomerDto customerDto) {
        customerDto.setId(id);
        return ResponseEntity.ok(individualCustomerService.updateDto(customerDto));
    }
    
    /**
     * Bireysel müşteri siler
     * @param id Müşteri ID
     * @return Boş yanıt
     */
    @DeleteMapping("/individual/{id}")
    public ResponseEntity<Void> deleteIndividualCustomer(@PathVariable Long id) {
        individualCustomerService.delete(id);
        return ResponseEntity.noContent().build();
    }
    
    /**
     * Tüm kurumsal müşterileri listeler
     * @return Kurumsal müşteri listesi
     */
    @GetMapping("/corporate")
    public ResponseEntity<List<CorporateCustomerDto>> getAllCorporateCustomers() {
        return ResponseEntity.ok(corporateCustomerService.getAllDto());
    }
    
    /**
     * ID'ye göre kurumsal müşteri getirir
     * @param id Müşteri ID
     * @return Kurumsal müşteri
     */
    @GetMapping("/corporate/{id}")
    public ResponseEntity<CorporateCustomerDto> getCorporateCustomerById(@PathVariable Long id) {
        return ResponseEntity.ok(corporateCustomerService.getDtoById(id));
    }
    
    /**
     * Yeni kurumsal müşteri ekler
     * @param customerDto Eklenecek müşteri
     * @return Eklenen müşteri
     */
    @PostMapping("/corporate")
    public ResponseEntity<CorporateCustomerDto> addCorporateCustomer(@RequestBody CorporateCustomerDto customerDto) {
        return new ResponseEntity<>(corporateCustomerService.addDto(customerDto), HttpStatus.CREATED);
    }
    
    /**
     * Kurumsal müşteri günceller
     * @param id Müşteri ID
     * @param customerDto Güncellenecek müşteri
     * @return Güncellenen müşteri
     */
    @PutMapping("/corporate/{id}")
    public ResponseEntity<CorporateCustomerDto> updateCorporateCustomer(@PathVariable Long id, @RequestBody CorporateCustomerDto customerDto) {
        customerDto.setId(id);
        return ResponseEntity.ok(corporateCustomerService.updateDto(customerDto));
    }
    
    /**
     * Kurumsal müşteri siler
     * @param id Müşteri ID
     * @return Boş yanıt
     */
    @DeleteMapping("/corporate/{id}")
    public ResponseEntity<Void> deleteCorporateCustomer(@PathVariable Long id) {
        corporateCustomerService.delete(id);
        return ResponseEntity.noContent().build();
    }
} 