package com.tobeto.banking.business.abstracts;

import com.tobeto.banking.business.dtos.requests.CreateCreditApplicationRequest;
import com.tobeto.banking.business.dtos.requests.GetCreditApplicationRequest;
import com.tobeto.banking.business.dtos.requests.UpdateCreditApplicationRequest;
import com.tobeto.banking.business.dtos.responses.CreditApplicationResponse;
import com.tobeto.banking.entities.concretes.CreditApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Kredi başvuruları için servis arayüzü
 */
public interface CreditApplicationService {
    
    /**
     * Yeni bir kredi başvurusu oluşturur
     * @param request Kredi başvurusu oluşturma isteği
     * @return Oluşturulan kredi başvurusu yanıtı
     */
    CreditApplicationResponse create(CreateCreditApplicationRequest request);
    
    /**
     * Var olan bir kredi başvurusunu günceller
     * @param request Kredi başvurusu güncelleme isteği
     * @return Güncellenen kredi başvurusu yanıtı
     */
    CreditApplicationResponse update(UpdateCreditApplicationRequest request);
    
    /**
     * ID'ye göre kredi başvurusunu getirir
     * @param id Kredi başvurusu ID
     * @return Kredi başvurusu yanıtı
     */
    CreditApplicationResponse getById(Long id);
    
    /**
     * Tüm kredi başvurularını getirir
     * @return Kredi başvuruları listesi
     */
    List<CreditApplicationResponse> getAll();
    
    /**
     * Filtreleme kriterlerine göre kredi başvurularını getirir
     * @param request Filtreleme kriterleri
     * @return Filtrelenmiş kredi başvuruları listesi
     */
    List<CreditApplicationResponse> getAllByFilter(GetCreditApplicationRequest request);
    
    /**
     * Sayfalama ile tüm kredi başvurularını getirir
     * @param pageable Sayfalama bilgisi
     * @return Sayfalanmış kredi başvuruları
     */
    Page<CreditApplicationResponse> getAllPaged(Pageable pageable);
    
    /**
     * Müşteri ID'sine göre kredi başvurularını getirir
     * @param customerId Müşteri ID
     * @return Kredi başvuruları listesi
     */
    List<CreditApplicationResponse> getAllByCustomerId(Long customerId);
    
    /**
     * Kredi türü ID'sine göre kredi başvurularını getirir
     * @param creditTypeId Kredi türü ID
     * @return Kredi başvuruları listesi
     */
    List<CreditApplicationResponse> getAllByCreditTypeId(Long creditTypeId);
    
    /**
     * Başvuru durumuna göre kredi başvurularını getirir
     * @param status Başvuru durumu
     * @return Kredi başvuruları listesi
     */
    List<CreditApplicationResponse> getAllByStatus(CreditApplication.ApplicationStatus status);
    
    /**
     * Belirli bir tarih aralığında yapılan kredi başvurularını getirir
     * @param startDate Başlangıç tarihi
     * @param endDate Bitiş tarihi
     * @return Kredi başvuruları listesi
     */
    List<CreditApplicationResponse> getAllByDateRange(LocalDateTime startDate, LocalDateTime endDate);
    
    /**
     * Kredi başvurusunu siler
     * @param id Kredi başvurusu ID
     */
    void delete(Long id);
} 