package com.tobeto.banking.business.rules;

import com.tobeto.banking.business.constants.CustomerMessages;
import com.tobeto.banking.core.crosscuttingconcerns.exceptions.types.BusinessException;
import com.tobeto.banking.repositories.abstracts.ICorporateCustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

/**
 * Kurumsal müşteri iş kuralları
 */
@Service
@RequiredArgsConstructor
public class CorporateCustomerBusinessRules {
    
    private final ICorporateCustomerRepository repository;
    private final CustomerBusinessRules customerBusinessRules;
    
    /**
     * Kurumsal müşterinin var olup olmadığını kontrol eder
     * @param id Kurumsal müşteri ID
     * @throws BusinessException Kurumsal müşteri bulunamazsa
     */
    public void checkIfCorporateCustomerExists(Long id) {
        if (!repository.existsById(id)) {
            throw new BusinessException(CustomerMessages.CORPORATE_CUSTOMER_NOT_FOUND);
        }
    }
    
    /**
     * Vergi numarasının daha önce kullanılıp kullanılmadığını kontrol eder
     * @param taxNumber Vergi numarası
     * @throws BusinessException Vergi numarası daha önce kullanılmışsa
     */
    public void checkIfTaxNumberExists(String taxNumber) {
        if (repository.findByTaxNumber(taxNumber).isPresent()) {
            throw new BusinessException(CustomerMessages.TAX_NUMBER_ALREADY_EXISTS);
        }
    }
    
    /**
     * E-posta adresinin daha önce kullanılıp kullanılmadığını kontrol eder
     * @param email E-posta adresi
     * @throws BusinessException E-posta adresi daha önce kullanılmışsa
     */
    public void checkIfEmailExists(String email) {
        if (repository.findByEmail(email).isPresent()) {
            throw new BusinessException(CustomerMessages.EMAIL_ALREADY_EXISTS);
        }
    }
    
    /**
     * Kurumsal müşteri bilgilerinin geçerliliğini kontrol eder
     * @param email E-posta adresi
     * @param phoneNumber Telefon numarası
     * @param taxNumber Vergi numarası
     * @param companyName Şirket adı
     * @param foundationYear Kuruluş yılı
     * @throws BusinessException Bilgiler geçersizse
     */
    public void validateCorporateCustomer(String email, String phoneNumber, String taxNumber, String companyName, int foundationYear) {
        // E-posta kontrolü
        if (email == null || email.isBlank() || !email.contains("@")) {
            throw new BusinessException(CustomerMessages.INVALID_EMAIL);
        }
        
        // Telefon numarası kontrolü
        if (phoneNumber == null || phoneNumber.isBlank() || phoneNumber.length() < 10) {
            throw new BusinessException(CustomerMessages.INVALID_PHONE_NUMBER);
        }
        
        // Vergi numarası kontrolü
        if (taxNumber == null || taxNumber.isBlank() || taxNumber.length() != 10) {
            throw new BusinessException(CustomerMessages.INVALID_TAX_NUMBER);
        }
        
        // Şirket adı kontrolü
        if (companyName == null || companyName.isBlank() || companyName.length() < 2) {
            throw new BusinessException(CustomerMessages.INVALID_COMPANY_NAME);
        }
        
        // Kuruluş yılı kontrolü
        int currentYear = java.time.Year.now().getValue();
        if (foundationYear < 1800 || foundationYear > currentYear) {
            throw new BusinessException(CustomerMessages.INVALID_FOUNDATION_YEAR);
        }
    }
} 