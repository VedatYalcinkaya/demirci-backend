package com.tobeto.banking.repositories.abstracts;

import com.tobeto.banking.core.data.IRepository;
import com.tobeto.banking.entities.concretes.IndividualCustomer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * IndividualCustomer entity'si için repository arayüzü
 */
@Repository
public interface IndividualCustomerRepository extends IRepository<IndividualCustomer, Long> {
    
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
     * Aktif bireysel müşterileri listeler
     * @return Aktif müşteri listesi
     */
    List<IndividualCustomer> findByIsActiveTrue();
    
    /**
     * Belirli bir yaş aralığındaki bireysel müşterileri listeler
     * @param minAge Minimum yaş
     * @param maxAge Maksimum yaş
     * @return Yaş aralığındaki müşteri listesi
     */
    @Query("SELECT i FROM IndividualCustomer i WHERE EXTRACT(YEAR FROM CURRENT_DATE) - EXTRACT(YEAR FROM i.birthDate) BETWEEN :minAge AND :maxAge")
    List<IndividualCustomer> findByAgeBetween(@Param("minAge") int minAge, @Param("maxAge") int maxAge);
    
    /**
     * Ad veya soyada göre bireysel müşteri arar (LIKE sorgusu)
     * @param keyword Aranacak kelime
     * @return Bulunan müşteri listesi
     */
    @Query("SELECT i FROM IndividualCustomer i WHERE LOWER(i.firstName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(i.lastName) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<IndividualCustomer> searchByName(@Param("keyword") String keyword);
    
    /**
     * Doğum tarihine göre bireysel müşteri listesi döner
     * @param year Doğum yılı
     * @return Bulunan müşteri listesi
     */
    @Query("SELECT i FROM IndividualCustomer i WHERE EXTRACT(YEAR FROM i.birthDate) = :year")
    List<IndividualCustomer> findByBirthDateYear(@Param("year") int year);
} 