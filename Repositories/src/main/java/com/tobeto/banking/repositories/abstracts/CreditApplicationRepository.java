package com.tobeto.banking.repositories.abstracts;

import com.tobeto.banking.entities.concretes.CreditApplication;
import com.tobeto.banking.entities.concretes.CreditType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Kredi başvuruları için repository arayüzü
 */
public interface CreditApplicationRepository extends JpaRepository<CreditApplication, Long> {
    
    /**
     * Müşteri ID'sine göre kredi başvurularını bulur
     * @param customerId Müşteri ID
     * @return Kredi başvuruları listesi
     */
    List<CreditApplication> findByCustomerId(Long customerId);
    
    /**
     * Müşteri ID'sine göre kredi başvurularını sayfalı olarak bulur
     * @param customerId Müşteri ID
     * @param pageable Sayfalama bilgisi
     * @return Kredi başvuruları sayfası
     */
    Page<CreditApplication> findByCustomerId(Long customerId, Pageable pageable);
    
    /**
     * Kredi türü ID'sine göre kredi başvurularını bulur
     * @param creditTypeId Kredi türü ID
     * @return Kredi başvuruları listesi
     */
    List<CreditApplication> findByCreditTypeId(Long creditTypeId);
    
    /**
     * Başvuru durumuna göre kredi başvurularını bulur
     * @param status Başvuru durumu
     * @return Kredi başvuruları listesi
     */
    List<CreditApplication> findByStatus(CreditApplication.ApplicationStatus status);
    
    /**
     * Başvuru durumuna göre kredi başvurularını sayfalı olarak bulur
     * @param status Başvuru durumu
     * @param pageable Sayfalama bilgisi
     * @return Kredi başvuruları sayfası
     */
    Page<CreditApplication> findByStatus(CreditApplication.ApplicationStatus status, Pageable pageable);
    
    /**
     * Müşteri ID'si ve başvuru durumuna göre kredi başvurularını bulur
     * @param customerId Müşteri ID
     * @param status Başvuru durumu
     * @return Kredi başvuruları listesi
     */
    List<CreditApplication> findByCustomerIdAndStatus(Long customerId, CreditApplication.ApplicationStatus status);
    
    /**
     * Belirli bir tarih aralığında yapılan kredi başvurularını bulur
     * @param startDate Başlangıç tarihi
     * @param endDate Bitiş tarihi
     * @return Kredi başvuruları listesi
     */
    List<CreditApplication> findByApplicationDateBetween(LocalDateTime startDate, LocalDateTime endDate);
    
    /**
     * Belirli bir tarih aralığında yapılan kredi başvurularını sayfalı olarak bulur
     * @param startDate Başlangıç tarihi
     * @param endDate Bitiş tarihi
     * @param pageable Sayfalama bilgisi
     * @return Kredi başvuruları sayfası
     */
    Page<CreditApplication> findByApplicationDateBetween(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
    
    /**
     * Müşteri tipi ve başvuru durumuna göre kredi başvurularını bulur
     * @param customerType Müşteri tipi
     * @param status Başvuru durumu
     * @return Kredi başvuruları listesi
     */
    @Query("SELECT ca FROM CreditApplication ca JOIN ca.creditType ct WHERE ct.customerType = :customerType AND ca.status = :status")
    List<CreditApplication> findByCreditTypeCustomerTypeAndStatus(@Param("customerType") CreditType.CustomerType customerType, @Param("status") CreditApplication.ApplicationStatus status);
    
    /**
     * Müşteri tipi ve başvuru durumuna göre kredi başvurularını sayfalı olarak bulur
     * @param customerType Müşteri tipi
     * @param status Başvuru durumu
     * @param pageable Sayfalama bilgisi
     * @return Kredi başvuruları sayfası
     */
    @Query("SELECT ca FROM CreditApplication ca JOIN ca.creditType ct WHERE ct.customerType = :customerType AND ca.status = :status")
    Page<CreditApplication> findByCreditTypeCustomerTypeAndStatus(@Param("customerType") CreditType.CustomerType customerType, @Param("status") CreditApplication.ApplicationStatus status, Pageable pageable);
} 