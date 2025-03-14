package com.tobeto.banking.business.abstracts;

import com.tobeto.banking.business.dtos.requests.CreateCreditScoreRequest;
import com.tobeto.banking.business.dtos.requests.GetCreditScoreRequest;
import com.tobeto.banking.business.dtos.requests.UpdateCreditScoreRequest;
import com.tobeto.banking.business.dtos.responses.CreditScoreResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Kredi skorları için servis arayüzü
 */
public interface CreditScoreService {
    
    /**
     * Yeni bir kredi skoru oluşturur
     * @param request Kredi skoru oluşturma isteği
     * @return Oluşturulan kredi skoru yanıtı
     */
    CreditScoreResponse create(CreateCreditScoreRequest request);
    
    /**
     * Var olan bir kredi skorunu günceller
     * @param request Kredi skoru güncelleme isteği
     * @return Güncellenen kredi skoru yanıtı
     */
    CreditScoreResponse update(UpdateCreditScoreRequest request);
    
    /**
     * ID'ye göre kredi skorunu getirir
     * @param id Kredi skoru ID
     * @return Kredi skoru yanıtı
     */
    CreditScoreResponse getById(Long id);
    
    /**
     * Tüm kredi skorlarını getirir
     * @return Kredi skorları listesi
     */
    List<CreditScoreResponse> getAll();
    
    /**
     * Filtreleme kriterlerine göre kredi skorlarını getirir
     * @param request Filtreleme kriterleri
     * @return Filtrelenmiş kredi skorları listesi
     */
    List<CreditScoreResponse> getAllByFilter(GetCreditScoreRequest request);
    
    /**
     * Sayfalama ile tüm kredi skorlarını getirir
     * @param pageable Sayfalama bilgisi
     * @return Sayfalanmış kredi skorları
     */
    Page<CreditScoreResponse> getAllPaged(Pageable pageable);
    
    /**
     * Müşteri ID'sine göre kredi skorlarını getirir
     * @param customerId Müşteri ID
     * @return Kredi skorları listesi
     */
    List<CreditScoreResponse> getAllByCustomerId(Long customerId);
    
    /**
     * Müşteri ID'sine göre en son kredi skorunu getirir
     * @param customerId Müşteri ID
     * @return En son kredi skoru
     */
    CreditScoreResponse getLatestByCustomerId(Long customerId);
    
    /**
     * Müşteri ID'sine göre geçerli kredi skorunu getirir
     * @param customerId Müşteri ID
     * @return Geçerli kredi skoru
     */
    CreditScoreResponse getValidByCustomerId(Long customerId);
    
    /**
     * Belirli bir skor aralığındaki kredi skorlarını getirir
     * @param minScore Minimum skor
     * @param maxScore Maksimum skor
     * @return Kredi skorları listesi
     */
    List<CreditScoreResponse> getAllByScoreRange(Integer minScore, Integer maxScore);
    
    /**
     * Belirli bir hesaplama tarihi aralığındaki kredi skorlarını getirir
     * @param startDate Başlangıç tarihi
     * @param endDate Bitiş tarihi
     * @return Kredi skorları listesi
     */
    List<CreditScoreResponse> getAllByCalculationDateRange(LocalDateTime startDate, LocalDateTime endDate);
    
    /**
     * Süresi dolmuş kredi skorlarını getirir
     * @return Süresi dolmuş kredi skorları listesi
     */
    List<CreditScoreResponse> getAllExpired();
    
    /**
     * Kredi skorunu siler
     * @param id Kredi skoru ID
     */
    void delete(Long id);
} 