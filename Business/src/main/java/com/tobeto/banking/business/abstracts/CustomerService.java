package com.tobeto.banking.business.abstracts;

import com.tobeto.banking.business.dtos.responses.CustomerResponse;
import com.tobeto.banking.core.business.IService;
import com.tobeto.banking.entities.concretes.Customer;

import java.util.List;
import java.util.Optional;

/**
 * Müşteri servisi arayüzü
 */
public interface CustomerService extends IService<Customer, Long> {
    
    /**
     * E-posta adresine göre müşteri arar
     * @param email E-posta adresi
     * @return Bulunan müşteri veya boş Optional
     */
    Optional<Customer> findByEmail(String email);
    
    /**
     * Tüm müşterileri DTO olarak döner
     * @return Müşteri DTO listesi
     */
    List<CustomerResponse> getAllDto();
    
    /**
     * ID'ye göre müşteriyi DTO olarak döner
     * @param id Müşteri ID
     * @return Müşteri DTO
     */
    CustomerResponse getDtoById(Long id);
} 