package com.tobeto.banking.business.abstracts;

import com.tobeto.banking.business.dtos.requests.CreateCreditTypeRequest;
import com.tobeto.banking.business.dtos.requests.GetCreditTypeRequest;
import com.tobeto.banking.business.dtos.requests.UpdateCreditTypeRequest;
import com.tobeto.banking.business.dtos.responses.CreditTypeResponse;
import com.tobeto.banking.entities.concretes.CreditType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Kredi türleri için servis arayüzü
 */
public interface CreditTypeService {
    
    /**
     * Yeni bir kredi türü oluşturur
     * @param request Kredi türü oluşturma isteği
     * @return Oluşturulan kredi türü yanıtı
     */
    CreditTypeResponse create(CreateCreditTypeRequest request);
    
    /**
     * Var olan bir kredi türünü günceller
     * @param request Kredi türü güncelleme isteği
     * @return Güncellenen kredi türü yanıtı
     */
    CreditTypeResponse update(UpdateCreditTypeRequest request);
    
    /**
     * ID'ye göre kredi türünü getirir
     * @param id Kredi türü ID
     * @return Kredi türü yanıtı
     */
    CreditTypeResponse getById(Long id);
    
    /**
     * Tüm kredi türlerini getirir
     * @return Kredi türleri listesi
     */
    List<CreditTypeResponse> getAll();
    
    /**
     * Filtreleme kriterlerine göre kredi türlerini getirir
     * @param request Filtreleme kriterleri
     * @return Filtrelenmiş kredi türleri listesi
     */
    List<CreditTypeResponse> getAllByFilter(GetCreditTypeRequest request);
    
    /**
     * Sayfalama ile tüm kredi türlerini getirir
     * @param pageable Sayfalama bilgisi
     * @return Sayfalanmış kredi türleri
     */
    Page<CreditTypeResponse> getAllPaged(Pageable pageable);
    
    /**
     * Müşteri tipine göre kredi türlerini getirir
     * @param customerType Müşteri tipi
     * @return Kredi türleri listesi
     */
    List<CreditTypeResponse> getAllByCustomerType(CreditType.CustomerType customerType);
    
    /**
     * Ana kredi türlerini getirir
     * @return Ana kredi türleri listesi
     */
    List<CreditTypeResponse> getAllParentTypes();
    
    /**
     * Belirli bir ana kredi türüne ait alt kredi türlerini getirir
     * @param parentId Ana kredi türü ID
     * @return Alt kredi türleri listesi
     */
    List<CreditTypeResponse> getAllByParentId(Long parentId);
    
    /**
     * Aktif kredi türlerini getirir
     * @return Aktif kredi türleri listesi
     */
    List<CreditTypeResponse> getAllActive();
    
    /**
     * Kredi türünü siler (soft delete)
     * @param id Kredi türü ID
     */
    void delete(Long id);
} 