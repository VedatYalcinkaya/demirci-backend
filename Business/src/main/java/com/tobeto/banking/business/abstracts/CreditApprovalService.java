package com.tobeto.banking.business.abstracts;

import com.tobeto.banking.business.dtos.requests.CreateCreditApprovalRequest;
import com.tobeto.banking.business.dtos.requests.GetCreditApprovalRequest;
import com.tobeto.banking.business.dtos.requests.UpdateCreditApprovalRequest;
import com.tobeto.banking.business.dtos.responses.CreditApprovalResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Kredi onayları için servis arayüzü
 */
public interface CreditApprovalService {
    
    /**
     * Yeni bir kredi onayı oluşturur
     * @param request Kredi onayı oluşturma isteği
     * @return Oluşturulan kredi onayı yanıtı
     */
    CreditApprovalResponse create(CreateCreditApprovalRequest request);
    
    /**
     * Var olan bir kredi onayını günceller
     * @param request Kredi onayı güncelleme isteği
     * @return Güncellenen kredi onayı yanıtı
     */
    CreditApprovalResponse update(UpdateCreditApprovalRequest request);
    
    /**
     * ID'ye göre kredi onayını getirir
     * @param id Kredi onayı ID
     * @return Kredi onayı yanıtı
     */
    CreditApprovalResponse getById(Long id);
    
    /**
     * Tüm kredi onaylarını getirir
     * @return Kredi onayları listesi
     */
    List<CreditApprovalResponse> getAll();
    
    /**
     * Filtreleme kriterlerine göre kredi onaylarını getirir
     * @param request Filtreleme kriterleri
     * @return Filtrelenmiş kredi onayları listesi
     */
    List<CreditApprovalResponse> getAllByFilter(GetCreditApprovalRequest request);
    
    /**
     * Sayfalama ile tüm kredi onaylarını getirir
     * @param pageable Sayfalama bilgisi
     * @return Sayfalanmış kredi onayları
     */
    Page<CreditApprovalResponse> getAllPaged(Pageable pageable);
    
    /**
     * Kredi başvurusu ID'sine göre onayı getirir
     * @param creditApplicationId Kredi başvurusu ID
     * @return Kredi onayı yanıtı
     */
    CreditApprovalResponse getByCreditApplicationId(Long creditApplicationId);
    
    /**
     * Onaylayan kişiye göre onayları getirir
     * @param approvedBy Onaylayan kişi
     * @return Onay listesi
     */
    List<CreditApprovalResponse> getAllByApprovedBy(String approvedBy);
    
    /**
     * Belirli bir tarih aralığında yapılan onayları getirir
     * @param startDate Başlangıç tarihi
     * @param endDate Bitiş tarihi
     * @return Onay listesi
     */
    List<CreditApprovalResponse> getAllByApprovalDateRange(LocalDateTime startDate, LocalDateTime endDate);
    
    /**
     * Kredi onayını siler
     * @param id Kredi onayı ID
     */
    void delete(Long id);
} 