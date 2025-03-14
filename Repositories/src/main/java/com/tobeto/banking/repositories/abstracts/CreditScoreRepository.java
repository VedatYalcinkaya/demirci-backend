package com.tobeto.banking.repositories.abstracts;

import com.tobeto.banking.entities.concretes.CreditScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Kredi skorları için repository arayüzü
 */
public interface CreditScoreRepository extends JpaRepository<CreditScore, Long> {
    
    /**
     * Müşteri ID'sine göre kredi skorlarını bulur
     * @param customerId Müşteri ID
     * @return Kredi skorları listesi
     */
    List<CreditScore> findByCustomerId(Long customerId);
    
    /**
     * Müşteri ID'sine göre en son kredi skorunu bulur
     * @param customerId Müşteri ID
     * @return En son kredi skoru
     */
    @Query("SELECT cs FROM CreditScore cs WHERE cs.customer.id = :customerId ORDER BY cs.calculationDate DESC")
    List<CreditScore> findLatestByCustomerId(@Param("customerId") Long customerId);
    
    /**
     * Müşteri ID'sine göre geçerli kredi skorunu bulur
     * @param customerId Müşteri ID
     * @param currentDate Şu anki tarih
     * @return Geçerli kredi skoru
     */
    @Query("SELECT cs FROM CreditScore cs WHERE cs.customer.id = :customerId AND cs.expiryDate > :currentDate ORDER BY cs.calculationDate DESC")
    Optional<CreditScore> findValidByCustomerId(@Param("customerId") Long customerId, @Param("currentDate") LocalDateTime currentDate);
    
    /**
     * Belirli bir skor aralığındaki kredi skorlarını bulur
     * @param minScore Minimum skor
     * @param maxScore Maksimum skor
     * @return Kredi skorları listesi
     */
    List<CreditScore> findByScoreBetween(Integer minScore, Integer maxScore);
    
    /**
     * Belirli bir hesaplama tarihi aralığındaki kredi skorlarını bulur
     * @param startDate Başlangıç tarihi
     * @param endDate Bitiş tarihi
     * @return Kredi skorları listesi
     */
    List<CreditScore> findByCalculationDateBetween(LocalDateTime startDate, LocalDateTime endDate);
    
    /**
     * Süresi dolmuş kredi skorlarını bulur
     * @param currentDate Şu anki tarih
     * @return Süresi dolmuş kredi skorları listesi
     */
    List<CreditScore> findByExpiryDateLessThan(LocalDateTime currentDate);
} 