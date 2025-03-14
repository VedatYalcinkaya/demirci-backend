package com.tobeto.banking.repositories.abstracts;

import com.tobeto.banking.core.data.IRepository;
import com.tobeto.banking.entities.concretes.Customer;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Customer entity'si için repository arayüzü
 */
@Repository
public interface CustomerRepository extends IRepository<Customer, Long> {
    
    /**
     * E-posta adresine göre müşteri arar
     * @param email E-posta adresi
     * @return Bulunan müşteri veya boş Optional
     */
    Optional<Customer> findByEmail(String email);
} 