package com.tobeto.banking.repositories.abstracts;

import com.tobeto.banking.core.data.IRepository;
import com.tobeto.banking.entities.concretes.CorporateCustomer;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * CorporateCustomer entity'si için repository arayüzü
 */
@Repository
public interface ICorporateCustomerRepository extends IRepository<CorporateCustomer, Long> {
    
    /**
     * Vergi numarasına göre kurumsal müşteri arar
     * @param taxNumber Vergi numarası
     * @return Bulunan müşteri veya boş Optional
     */
    Optional<CorporateCustomer> findByTaxNumber(String taxNumber);
    
    /**
     * Şirket adına göre kurumsal müşteri arar
     * @param companyName Şirket adı
     * @return Bulunan müşteri veya boş Optional
     */
    Optional<CorporateCustomer> findByCompanyName(String companyName);
    
    /**
     * E-posta adresine göre kurumsal müşteri arar
     * @param email E-posta adresi
     * @return Bulunan müşteri veya boş Optional
     */
    Optional<CorporateCustomer> findByEmail(String email);
    
    /**
     * Sektöre göre kurumsal müşteri listesi döner
     * @param sector Sektör
     * @return Bulunan müşteri listesi
     */
    List<CorporateCustomer> findBySector(String sector);
    
    /**
     * Çalışan sayısına göre kurumsal müşteri listesi döner
     * @param minCount Minimum çalışan sayısı
     * @param maxCount Maksimum çalışan sayısı
     * @return Bulunan müşteri listesi
     */
    List<CorporateCustomer> findByEmployeeCountBetween(int minCount, int maxCount);
    
    /**
     * Yıllık gelire göre kurumsal müşteri listesi döner
     * @param minRevenue Minimum yıllık gelir
     * @return Bulunan müşteri listesi
     */
    List<CorporateCustomer> findByAnnualRevenueGreaterThanEqual(double minRevenue);
    
    /**
     * Aktif kurumsal müşterileri listeler
     * @return Aktif müşteri listesi
     */
    List<CorporateCustomer> findByIsActiveTrue();
} 