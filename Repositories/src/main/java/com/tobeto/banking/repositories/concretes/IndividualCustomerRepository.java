package com.tobeto.banking.repositories.concretes;

import com.tobeto.banking.entities.concretes.IndividualCustomer;
import com.tobeto.banking.repositories.abstracts.IIndividualCustomerRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * IndividualCustomer repository implementasyonu
 */
@Repository
public interface IndividualCustomerRepository extends IIndividualCustomerRepository {
    
    /**
     * Belirli bir yaş aralığındaki bireysel müşterileri listeler
     * @param minAge Minimum yaş
     * @param maxAge Maksimum yaş
     * @return Yaş aralığındaki müşteri listesi
     */
    @Query("SELECT i FROM IndividualCustomer i WHERE FUNCTION('YEAR', CURRENT_DATE) - FUNCTION('YEAR', i.birthDate) BETWEEN :minAge AND :maxAge")
    List<IndividualCustomer> findByAgeBetween(@Param("minAge") int minAge, @Param("maxAge") int maxAge);
    
    /**
     * Ad veya soyada göre bireysel müşteri arar (LIKE sorgusu)
     * @param keyword Aranacak kelime
     * @return Bulunan müşteri listesi
     */
    @Query("SELECT i FROM IndividualCustomer i WHERE LOWER(i.firstName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(i.lastName) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<IndividualCustomer> searchByName(@Param("keyword") String keyword);
} 