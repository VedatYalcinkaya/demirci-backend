package com.tobeto.banking.business.rules;

import com.tobeto.banking.business.constants.CustomerMessages;
import com.tobeto.banking.core.crosscuttingconcerns.exceptions.types.BusinessException;
import com.tobeto.banking.repositories.abstracts.ICustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Müşteri işlemleri için business kuralları
 */
@Service
@RequiredArgsConstructor
public class CustomerBusinessRules {
    
    private final ICustomerRepository customerRepository;
    
    /**
     * Müşteri ID'sinin varlığını kontrol eder
     * @param id Müşteri ID
     */
    public void checkIfCustomerExists(Long id) {
        if (!customerRepository.existsById(id)) {
            throw new BusinessException(CustomerMessages.CUSTOMER_NOT_FOUND);
        }
    }
    
    /**
     * E-posta adresinin benzersiz olup olmadığını kontrol eder
     * @param email E-posta adresi
     */
    public void checkIfEmailExists(String email) {
        if (customerRepository.findByEmail(email).isPresent()) {
            throw new BusinessException(CustomerMessages.CUSTOMER_ALREADY_EXISTS);
        }
    }
    
    /**
     * E-posta adresinin geçerli olup olmadığını kontrol eder
     * @param email E-posta adresi
     */
    public void checkIfEmailValid(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        if (email == null || !email.matches(emailRegex)) {
            throw new BusinessException("Geçersiz e-posta adresi");
        }
    }
    
    /**
     * Telefon numarasının geçerli olup olmadığını kontrol eder
     * @param phoneNumber Telefon numarası
     */
    public void checkIfPhoneNumberValid(String phoneNumber) {
        String phoneRegex = "^[0-9]{10,15}$";
        if (phoneNumber == null || !phoneNumber.matches(phoneRegex)) {
            throw new BusinessException("Geçersiz telefon numarası");
        }
    }
} 