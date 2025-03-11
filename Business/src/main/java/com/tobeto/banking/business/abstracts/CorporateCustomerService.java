package com.tobeto.banking.business.abstracts;

import com.tobeto.banking.core.business.IService;
import com.tobeto.banking.entities.concretes.CorporateCustomer;
import com.tobeto.banking.entities.dtos.CorporateCustomerDto;

import java.util.List;
import java.util.Optional;

/**
 * Kurumsal müşteri servisi arayüzü
 */
public interface CorporateCustomerService extends IService<CorporateCustomer, Long> {
    
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
     * Şirket adına göre kurumsal müşteri arar (LIKE sorgusu)
     * @param keyword Aranacak kelime
     * @return Bulunan müşteri listesi
     */
    List<CorporateCustomer> searchByCompanyName(String keyword);
    
    /**
     * Belirli bir kuruluş yılından sonra kurulmuş şirketleri listeler
     * @param year Kuruluş yılı
     * @return Bulunan müşteri listesi
     */
    List<CorporateCustomer> findByFoundationYearGreaterThanEqual(int year);
    
    /**
     * Belirli bir sektördeki ve minimum çalışan sayısına sahip şirketleri listeler
     * @param sector Sektör
     * @param minEmployeeCount Minimum çalışan sayısı
     * @return Bulunan müşteri listesi
     */
    List<CorporateCustomer> findBySectorAndMinEmployeeCount(String sector, int minEmployeeCount);
    
    /**
     * Tüm kurumsal müşterileri DTO olarak döner
     * @return Kurumsal müşteri DTO listesi
     */
    List<CorporateCustomerDto> getAllDto();
    
    /**
     * ID'ye göre kurumsal müşteriyi DTO olarak döner
     * @param id Müşteri ID
     * @return Kurumsal müşteri DTO
     */
    CorporateCustomerDto getDtoById(Long id);
    
    /**
     * Kurumsal müşteri ekler
     * @param customerDto Eklenecek müşteri DTO
     * @return Eklenen müşteri DTO
     */
    CorporateCustomerDto addDto(CorporateCustomerDto customerDto);
    
    /**
     * Kurumsal müşteri günceller
     * @param customerDto Güncellenecek müşteri DTO
     * @return Güncellenen müşteri DTO
     */
    CorporateCustomerDto updateDto(CorporateCustomerDto customerDto);
} 