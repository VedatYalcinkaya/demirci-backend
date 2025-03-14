package com.tobeto.banking.business.abstracts;

import com.tobeto.banking.business.dtos.requests.CreateCreditDocumentRequest;
import com.tobeto.banking.business.dtos.requests.GetCreditDocumentRequest;
import com.tobeto.banking.business.dtos.requests.UpdateCreditDocumentRequest;
import com.tobeto.banking.business.dtos.responses.CreditDocumentResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Kredi belgeleri için servis arayüzü
 */
public interface CreditDocumentService {
    
    /**
     * Yeni bir kredi belgesi oluşturur
     * @param request Kredi belgesi oluşturma isteği
     * @return Oluşturulan kredi belgesi yanıtı
     */
    CreditDocumentResponse create(CreateCreditDocumentRequest request);
    
    /**
     * Var olan bir kredi belgesini günceller
     * @param request Kredi belgesi güncelleme isteği
     * @return Güncellenen kredi belgesi yanıtı
     */
    CreditDocumentResponse update(UpdateCreditDocumentRequest request);
    
    /**
     * ID'ye göre kredi belgesini getirir
     * @param id Kredi belgesi ID
     * @return Kredi belgesi yanıtı
     */
    CreditDocumentResponse getById(Long id);
    
    /**
     * Tüm kredi belgelerini getirir
     * @return Kredi belgeleri listesi
     */
    List<CreditDocumentResponse> getAll();
    
    /**
     * Filtreleme kriterlerine göre kredi belgelerini getirir
     * @param request Filtreleme kriterleri
     * @return Filtrelenmiş kredi belgeleri listesi
     */
    List<CreditDocumentResponse> getAllByFilter(GetCreditDocumentRequest request);
    
    /**
     * Sayfalama ile tüm kredi belgelerini getirir
     * @param pageable Sayfalama bilgisi
     * @return Sayfalanmış kredi belgeleri
     */
    Page<CreditDocumentResponse> getAllPaged(Pageable pageable);
    
    /**
     * Kredi başvurusu ID'sine göre belgeleri getirir
     * @param creditApplicationId Kredi başvurusu ID
     * @return Belge listesi
     */
    List<CreditDocumentResponse> getAllByCreditApplicationId(Long creditApplicationId);
    
    /**
     * Belge türüne göre belgeleri getirir
     * @param documentType Belge türü
     * @return Belge listesi
     */
    List<CreditDocumentResponse> getAllByDocumentType(String documentType);
    
    /**
     * Kredi başvurusu ID'si ve belge türüne göre belgeleri getirir
     * @param creditApplicationId Kredi başvurusu ID
     * @param documentType Belge türü
     * @return Belge listesi
     */
    List<CreditDocumentResponse> getAllByCreditApplicationIdAndDocumentType(Long creditApplicationId, String documentType);
    
    /**
     * Belirli bir tarih aralığında yüklenen belgeleri getirir
     * @param startDate Başlangıç tarihi
     * @param endDate Bitiş tarihi
     * @return Belge listesi
     */
    List<CreditDocumentResponse> getAllByUploadDateRange(LocalDateTime startDate, LocalDateTime endDate);
    
    /**
     * Kredi belgesini siler
     * @param id Kredi belgesi ID
     */
    void delete(Long id);
}