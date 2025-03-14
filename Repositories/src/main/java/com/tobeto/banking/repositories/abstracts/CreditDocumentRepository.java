package com.tobeto.banking.repositories.abstracts;

import com.tobeto.banking.entities.concretes.CreditDocument;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Kredi belgeleri için repository arayüzü
 */
public interface CreditDocumentRepository extends JpaRepository<CreditDocument, Long> {
    
    /**
     * Kredi başvurusu ID'sine göre belgeleri bulur
     * @param creditApplicationId Kredi başvurusu ID
     * @return Belge listesi
     */
    List<CreditDocument> findByCreditApplicationId(Long creditApplicationId);
    
    /**
     * Belge türüne göre belgeleri bulur
     * @param documentType Belge türü
     * @return Belge listesi
     */
    List<CreditDocument> findByDocumentType(String documentType);
    
    /**
     * Kredi başvurusu ID'si ve belge türüne göre belgeleri bulur
     * @param creditApplicationId Kredi başvurusu ID
     * @param documentType Belge türü
     * @return Belge listesi
     */
    List<CreditDocument> findByCreditApplicationIdAndDocumentType(Long creditApplicationId, String documentType);
    
    /**
     * Belirli bir tarih aralığında yüklenen belgeleri bulur
     * @param startDate Başlangıç tarihi
     * @param endDate Bitiş tarihi
     * @return Belge listesi
     */
    List<CreditDocument> findByUploadDateBetween(LocalDateTime startDate, LocalDateTime endDate);
} 