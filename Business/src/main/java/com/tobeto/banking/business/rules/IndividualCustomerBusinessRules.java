package com.tobeto.banking.business.rules;

import com.tobeto.banking.business.constants.CustomerMessages;
import com.tobeto.banking.core.crosscuttingconcerns.exceptions.types.BusinessException;
import com.tobeto.banking.repositories.abstracts.IndividualCustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;

/**
 * Bireysel müşteri işlemleri için business kuralları
 */
@Service
@RequiredArgsConstructor
public class IndividualCustomerBusinessRules {
    
    private final IndividualCustomerRepository individualCustomerRepository;
    private final CustomerBusinessRules customerBusinessRules;
    
    /**
     * Bireysel müşteri ID'sinin varlığını kontrol eder
     * @param id Müşteri ID
     */
    public void checkIfIndividualCustomerExists(Long id) {
        if (!individualCustomerRepository.existsById(id)) {
            throw new BusinessException(CustomerMessages.INDIVIDUAL_CUSTOMER_NOT_FOUND);
        }
    }
    
    /**
     * TC kimlik numarasının benzersiz olup olmadığını kontrol eder
     * @param identityNumber TC kimlik numarası
     */
    public void checkIfIdentityNumberExists(String identityNumber) {
        if (individualCustomerRepository.findByIdentityNumber(identityNumber).isPresent()) {
            throw new BusinessException(CustomerMessages.IDENTITY_NUMBER_ALREADY_EXISTS);
        }
    }
    
    /**
     * E-posta adresinin benzersiz olup olmadığını kontrol eder
     * @param email E-posta adresi
     */
    public void checkIfEmailExists(String email) {
        if (individualCustomerRepository.findByEmail(email).isPresent()) {
            throw new BusinessException(CustomerMessages.EMAIL_ALREADY_EXISTS);
        }
    }
    
    /**
     * TC kimlik numarasının geçerli olup olmadığını kontrol eder
     * @param identityNumber TC kimlik numarası
     */
    public void checkIfIdentityNumberValid(String identityNumber) {
        // TC kimlik numarası 11 haneli olmalıdır
        if (identityNumber == null || identityNumber.length() != 11 || !identityNumber.matches("^[0-9]{11}$")) {
            throw new BusinessException(CustomerMessages.INVALID_IDENTITY_NUMBER);
        }
        
        // TC kimlik numarası algoritması kontrolü
        try {
            int[] digits = new int[11];
            for (int i = 0; i < 11; i++) {
                digits[i] = Integer.parseInt(identityNumber.substring(i, i + 1));
            }
            
            // İlk hane 0 olamaz
            if (digits[0] == 0) {
                throw new BusinessException(CustomerMessages.INVALID_IDENTITY_NUMBER);
            }
            
            // 10. hane kontrolü: (1, 3, 5, 7, 9. hanelerin toplamının 7 katı - 2, 4, 6, 8. hanelerin toplamı) % 10
            int tenthDigitCheck = ((digits[0] + digits[2] + digits[4] + digits[6] + digits[8]) * 7 - 
                                  (digits[1] + digits[3] + digits[5] + digits[7])) % 10;
            
            // 11. hane kontrolü: (ilk 10 hanenin toplamı) % 10
            int eleventhDigitCheck = (digits[0] + digits[1] + digits[2] + digits[3] + digits[4] + 
                                     digits[5] + digits[6] + digits[7] + digits[8] + digits[9]) % 10;
            
            if (digits[9] != tenthDigitCheck || digits[10] != eleventhDigitCheck) {
                throw new BusinessException(CustomerMessages.INVALID_IDENTITY_NUMBER);
            }
        } catch (NumberFormatException e) {
            throw new BusinessException(CustomerMessages.INVALID_IDENTITY_NUMBER);
        }
    }
    
    /**
     * Doğum tarihinin geçerli olup olmadığını kontrol eder
     * @param birthDate Doğum tarihi
     */
    public void checkIfBirthDateValid(LocalDate birthDate) {
        if (birthDate == null) {
            throw new BusinessException(CustomerMessages.INVALID_BIRTH_DATE);
        }
        
        // Doğum tarihi bugünden önce olmalıdır
        if (birthDate.isAfter(LocalDate.now())) {
            throw new BusinessException(CustomerMessages.INVALID_BIRTH_DATE);
        }
        
        // Yaş kontrolü (18 yaşından büyük olmalı)
        int age = Period.between(birthDate, LocalDate.now()).getYears();
        if (age < 18) {
            throw new BusinessException(CustomerMessages.INVALID_BIRTH_DATE);
        }
    }
    
    /**
     * Bireysel müşteri bilgilerinin geçerli olup olmadığını kontrol eder
     * @param email E-posta adresi
     * @param phoneNumber Telefon numarası
     * @param identityNumber TC kimlik numarası
     * @param birthDate Doğum tarihi
     */
    public void validateIndividualCustomer(String email, String phoneNumber, String identityNumber, LocalDate birthDate) {
        customerBusinessRules.checkIfEmailValid(email);
        customerBusinessRules.checkIfPhoneNumberValid(phoneNumber);
        checkIfIdentityNumberValid(identityNumber);
        checkIfBirthDateValid(birthDate);
    }
} 