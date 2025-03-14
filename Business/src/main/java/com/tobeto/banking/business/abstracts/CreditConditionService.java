package com.tobeto.banking.business.abstracts;

import com.tobeto.banking.business.dtos.requests.CreateCreditConditionRequest;
import com.tobeto.banking.business.dtos.requests.GetCreditConditionRequest;
import com.tobeto.banking.business.dtos.requests.UpdateCreditConditionRequest;
import com.tobeto.banking.business.dtos.responses.CreditConditionResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Kredi koşulları için servis arayüzü
 */
public interface CreditConditionService {
    
    /**
     * Yeni bir kredi koşulu oluşturur
     * @param request Kredi koşulu oluşturma isteği
     * @return Oluşturulan kredi koşulu yanıtı
     */
    CreditConditionResponse create(CreateCreditConditionRequest request);
    
    /**
     * Var olan bir kredi koşulunu günceller
     * @param request Kredi koşulu güncelleme isteği
     * @return Güncellenen kredi koşulu yanıtı
     */
    CreditConditionResponse update(UpdateCreditConditionRequest request);
    
    /**
     * ID'ye göre kredi koşulunu getirir
     * @param id Kredi koşulu ID
     * @return Kredi koşulu yanıtı
     */
    CreditConditionResponse getById(Long id);
    
    /**
     * Tüm kredi koşullarını getirir
     * @return Kredi koşulları listesi
     */
    List<CreditConditionResponse> getAll();
    
    /**
     * Filtreleme kriterlerine göre kredi koşullarını getirir
     * @param request Filtreleme kriterleri
     * @return Filtrelenmiş kredi koşulları listesi
     */
    List<CreditConditionResponse> getAllByFilter(GetCreditConditionRequest request);
    
    /**
     * Sayfalama ile tüm kredi koşullarını getirir
     * @param pageable Sayfalama bilgisi
     * @return Sayfalanmış kredi koşulları
     */
    Page<CreditConditionResponse> getAllPaged(Pageable pageable);
    
    /**
     * Kredi türü ID'sine göre koşulları getirir
     * @param creditTypeId Kredi türü ID
     * @return Koşul listesi
     */
    List<CreditConditionResponse> getAllByCreditTypeId(Long creditTypeId);
    
    /**
     * Koşul adına göre koşulları getirir
     * @param conditionName Koşul adı
     * @return Koşul listesi
     */
    List<CreditConditionResponse> getAllByConditionName(String conditionName);
    
    /**
     * Kredi türü ID'si ve koşul adına göre koşulları getirir
     * @param creditTypeId Kredi türü ID
     * @param conditionName Koşul adı
     * @return Koşul listesi
     */
    List<CreditConditionResponse> getAllByCreditTypeIdAndConditionName(Long creditTypeId, String conditionName);
    
    /**
     * Kredi koşulunu siler
     * @param id Kredi koşulu ID
     */
    void delete(Long id);
} 