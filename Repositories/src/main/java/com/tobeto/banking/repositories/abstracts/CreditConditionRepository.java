package com.tobeto.banking.repositories.abstracts;

import com.tobeto.banking.entities.concretes.CreditCondition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Kredi koşulları için repository arayüzü
 */
@Repository
public interface CreditConditionRepository extends JpaRepository<CreditCondition, Long> {
    
    /**
     * Kredi türü ID'sine göre koşulları getirir
     * @param creditTypeId Kredi türü ID
     * @return Koşul listesi
     */
    List<CreditCondition> findByCreditTypeId(Long creditTypeId);
    
    /**
     * Koşul adına göre koşulları getirir
     * @param conditionName Koşul adı
     * @return Koşul listesi
     */
    List<CreditCondition> findByConditionName(String conditionName);
    
    /**
     * Koşul adı ve değerine göre koşulları getirir
     * @param conditionName Koşul adı
     * @param conditionValue Koşul değeri
     * @return Koşul listesi
     */
    List<CreditCondition> findByConditionNameAndConditionValue(String conditionName, String conditionValue);
    
    /**
     * Kredi türü ID'si ve koşul adına göre koşulları getirir
     * @param creditTypeId Kredi türü ID
     * @param conditionName Koşul adı
     * @return Koşul listesi
     */
    List<CreditCondition> findByCreditTypeIdAndConditionName(Long creditTypeId, String conditionName);
    
    /**
     * Kredi türü ID'si, koşul adı ve değerine göre koşulları getirir
     * @param creditTypeId Kredi türü ID
     * @param conditionName Koşul adı
     * @param conditionValue Koşul değeri
     * @return Koşul listesi
     */
    List<CreditCondition> findByCreditTypeIdAndConditionNameAndConditionValue(Long creditTypeId, String conditionName, String conditionValue);
} 