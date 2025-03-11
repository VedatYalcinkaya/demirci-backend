package com.tobeto.banking.repositories.concretes;

import com.tobeto.banking.entities.concretes.CorporateCustomer;
import com.tobeto.banking.repositories.abstracts.ICorporateCustomerRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * CorporateCustomer repository implementasyonu
 */
@Repository
public interface CorporateCustomerRepository extends ICorporateCustomerRepository {
    
    /**
     * Şirket adına göre kurumsal müşteri arar (LIKE sorgusu)
     * @param keyword Aranacak kelime
     * @return Bulunan müşteri listesi
     */
    @Query("SELECT c FROM CorporateCustomer c WHERE LOWER(c.companyName) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<CorporateCustomer> searchByCompanyName(@Param("keyword") String keyword);
    
    /**
     * Belirli bir kuruluş yılından sonra kurulmuş şirketleri listeler
     * @param year Kuruluş yılı
     * @return Bulunan müşteri listesi
     */
    @Query("SELECT c FROM CorporateCustomer c WHERE c.foundationYear >= :year")
    List<CorporateCustomer> findByFoundationYearGreaterThanEqual(@Param("year") int year);
    
    /**
     * Belirli bir sektördeki ve minimum çalışan sayısına sahip şirketleri listeler
     * @param sector Sektör
     * @param minEmployeeCount Minimum çalışan sayısı
     * @return Bulunan müşteri listesi
     */
    @Query("SELECT c FROM CorporateCustomer c WHERE c.sector = :sector AND c.employeeCount >= :minEmployeeCount")
    List<CorporateCustomer> findBySectorAndMinEmployeeCount(@Param("sector") String sector, @Param("minEmployeeCount") int minEmployeeCount);
} 