package com.tobeto.banking.repositories.abstracts;

import com.tobeto.banking.core.data.IRepository;
import com.tobeto.banking.entities.concretes.IndividualCustomer;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * IndividualCustomer entity'si için repository arayüzü
 */
@Repository
public interface IIndividualCustomerRepository extends IRepository<IndividualCustomer, Long> {
    
    /**
     * TC kimlik numarasına göre bireysel müşteri arar
     * @param identityNumber TC kimlik numarası
     * @return Bulunan müşteri veya boş Optional
     */
    Optional<IndividualCustomer> findByIdentityNumber(String identityNumber);
    
    /**
     * E-posta adresine göre bireysel müşteri arar
     * @param email E-posta adresi
     * @return Bulunan müşteri veya boş Optional
     */
    Optional<IndividualCustomer> findByEmail(String email);
    
    /**
     * Ad ve soyada göre bireysel müşteri listesi döner
     * @param firstName Ad
     * @param lastName Soyad
     * @return Bulunan müşteri listesi
     */
    List<IndividualCustomer> findByFirstNameAndLastName(String firstName, String lastName);
    
    /**
     * Doğum tarihine göre bireysel müşteri listesi döner
     * @param year Doğum yılı
     * @return Bulunan müşteri listesi
     */
    List<IndividualCustomer> findByBirthDateYear(int year);
    
    /**
     * Aktif bireysel müşterileri listeler
     * @return Aktif müşteri listesi
     */
    List<IndividualCustomer> findByIsActiveTrue();
} 