package com.tobeto.banking.repositories.abstracts;

import com.tobeto.banking.entities.concretes.CreditApproval;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Kredi onayları için repository arayüzü
 */
public interface CreditApprovalRepository extends JpaRepository<CreditApproval, Long> {
    
    /**
     * Kredi başvurusu ID'sine göre onay bulur
     * @param creditApplicationId Kredi başvurusu ID
     * @return Onay
     */
    Optional<CreditApproval> findByCreditApplicationId(Long creditApplicationId);
    
    /**
     * Onaylayan kişiye göre onayları bulur
     * @param approvedBy Onaylayan kişi
     * @return Onay listesi
     */
    List<CreditApproval> findByApprovedBy(String approvedBy);
    
    /**
     * Belirli bir tarih aralığında yapılan onayları bulur
     * @param startDate Başlangıç tarihi
     * @param endDate Bitiş tarihi
     * @return Onay listesi
     */
    List<CreditApproval> findByApprovalDateBetween(LocalDateTime startDate, LocalDateTime endDate);
} 