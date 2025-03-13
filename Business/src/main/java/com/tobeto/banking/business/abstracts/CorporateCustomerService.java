package com.tobeto.banking.business.abstracts;

import com.tobeto.banking.business.dtos.requests.CreateCorporateCustomerRequest;
import com.tobeto.banking.business.dtos.requests.UpdateCorporateCustomerRequest;
import com.tobeto.banking.business.dtos.responses.CorporateCustomerResponse;
import com.tobeto.banking.entities.concretes.CorporateCustomer;

import java.util.List;
import java.util.Optional;

/**
 * Kurumsal müşteri servisi arayüzü
 */
public interface CorporateCustomerService {
    
    /**
     * Vergi numarasına göre kurumsal müşteri bulur
     * @param taxNumber Vergi numarası
     * @return Kurumsal müşteri
     */
    Optional<CorporateCustomer> findByTaxNumber(String taxNumber);
    
    /**
     * Şirket adına göre kurumsal müşteri bulur
     * @param companyName Şirket adı
     * @return Kurumsal müşteri
     */
    Optional<CorporateCustomer> findByCompanyName(String companyName);
    
    /**
     * E-posta adresine göre kurumsal müşteri bulur
     * @param email E-posta adresi
     * @return Kurumsal müşteri
     */
    Optional<CorporateCustomer> findByEmail(String email);
    
    /**
     * Sektöre göre kurumsal müşterileri bulur
     * @param sector Sektör
     * @return Kurumsal müşteri listesi
     */
    List<CorporateCustomer> findBySector(String sector);
    
    /**
     * Çalışan sayısı aralığına göre kurumsal müşterileri bulur
     * @param minCount Minimum çalışan sayısı
     * @param maxCount Maksimum çalışan sayısı
     * @return Kurumsal müşteri listesi
     */
    List<CorporateCustomer> findByEmployeeCountBetween(int minCount, int maxCount);
    
    /**
     * Şirket adına göre kurumsal müşterileri arar
     * @param keyword Arama kelimesi
     * @return Kurumsal müşteri listesi
     */
    List<CorporateCustomer> searchByCompanyName(String keyword);
    
    /**
     * Belirli bir yıldan sonra kurulmuş kurumsal müşterileri bulur
     * @param year Kuruluş yılı
     * @return Kurumsal müşteri listesi
     */
    List<CorporateCustomer> findByFoundationYearGreaterThanEqual(int year);
    
    /**
     * Sektör ve minimum çalışan sayısına göre kurumsal müşterileri bulur
     * @param sector Sektör
     * @param minEmployeeCount Minimum çalışan sayısı
     * @return Kurumsal müşteri listesi
     */
    List<CorporateCustomer> findBySectorAndMinEmployeeCount(String sector, int minEmployeeCount);
    
    /**
     * Tüm kurumsal müşterileri DTO olarak getirir
     * @return Kurumsal müşteri DTO listesi
     */
    List<CorporateCustomerResponse> getAllDto();
    
    /**
     * ID'ye göre kurumsal müşteri DTO'sunu getirir
     * @param id Kurumsal müşteri ID
     * @return Kurumsal müşteri DTO
     */
    CorporateCustomerResponse getDtoById(Long id);
    
    /**
     * Yeni kurumsal müşteri ekler
     * @param request Kurumsal müşteri ekleme isteği
     * @return Eklenen kurumsal müşteri DTO
     */
    CorporateCustomerResponse addDto(CreateCorporateCustomerRequest request);
    
    /**
     * Kurumsal müşteri bilgilerini günceller
     * @param request Kurumsal müşteri güncelleme isteği
     * @return Güncellenen kurumsal müşteri DTO
     */
    CorporateCustomerResponse updateDto(UpdateCorporateCustomerRequest request);
} 