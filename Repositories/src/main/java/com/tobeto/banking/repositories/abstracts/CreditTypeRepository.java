package com.tobeto.banking.repositories.abstracts;

import com.tobeto.banking.entities.concretes.CreditType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Kredi türleri için repository arayüzü
 */
public interface CreditTypeRepository extends JpaRepository<CreditType, Long> {
    
    /**
     * Müşteri tipine göre kredi türlerini bulur
     * @param customerType Müşteri tipi (INDIVIDUAL/CORPORATE)
     * @return Kredi türleri listesi
     */
    List<CreditType> findByCustomerType(CreditType.CustomerType customerType);
    
    /**
     * Ana kredi türlerini bulur (parent_id = null)
     * @return Ana kredi türleri listesi
     */
    List<CreditType> findByParentIsNull();
    
    /**
     * Belirli bir ana kredi türüne ait alt kredi türlerini bulur
     * @param parentId Ana kredi türü ID
     * @return Alt kredi türleri listesi
     */
    List<CreditType> findByParentId(Long parentId);
    
    /**
     * Müşteri tipine göre ana kredi türlerini bulur
     * @param customerType Müşteri tipi (INDIVIDUAL/CORPORATE)
     * @return Ana kredi türleri listesi
     */
    List<CreditType> findByCustomerTypeAndParentIsNull(CreditType.CustomerType customerType);
    
    /**
     * Aktif kredi türlerini bulur
     * @return Aktif kredi türleri listesi
     */
    List<CreditType> findByIsActiveTrue();
    
    /**
     * Müşteri tipine göre aktif kredi türlerini bulur
     * @param customerType Müşteri tipi (INDIVIDUAL/CORPORATE)
     * @return Aktif kredi türleri listesi
     */
    List<CreditType> findByCustomerTypeAndIsActiveTrue(CreditType.CustomerType customerType);
    
    /**
     * İsme göre kredi türü arar
     * @param name Kredi türü adı
     * @return Kredi türü
     */
    CreditType findByName(String name);
} 