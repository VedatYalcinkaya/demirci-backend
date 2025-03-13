package com.tobeto.banking.business.mappings;

import com.tobeto.banking.business.dtos.requests.CreateCorporateCustomerRequest;
import com.tobeto.banking.business.dtos.requests.UpdateCorporateCustomerRequest;
import com.tobeto.banking.business.dtos.responses.CorporateCustomerResponse;
import com.tobeto.banking.entities.concretes.CorporateCustomer;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Kurumsal müşteri entity ve DTO dönüşümleri için mapper sınıfı
 */
@Component
public class CorporateCustomerMapper {
    
    /**
     * Kurumsal müşteri entity'sini response DTO'ya dönüştürür
     * @param entity Kurumsal müşteri entity
     * @return Kurumsal müşteri response DTO
     */
    public CorporateCustomerResponse toResponse(CorporateCustomer entity) {
        if (entity == null) {
            return null;
        }
        
        CorporateCustomerResponse response = new CorporateCustomerResponse();
        response.setId(entity.getId());
        response.setEmail(entity.getEmail());
        response.setPhoneNumber(entity.getPhoneNumber());
        response.setTaxNumber(entity.getTaxNumber());
        response.setCompanyName(entity.getCompanyName());
        response.setSector(entity.getSector());
        response.setFoundationYear(entity.getFoundationYear());
        response.setEmployeeCount(entity.getEmployeeCount());
        response.setAnnualRevenue(entity.getAnnualRevenue());
        response.setWebsite(entity.getWebsite());
        response.setActive(entity.isActive());
        response.setCreatedDate(entity.getCreatedDate());
        response.setUpdatedDate(entity.getUpdatedDate());
        
        return response;
    }
    
    /**
     * Kurumsal müşteri entity listesini response DTO listesine dönüştürür
     * @param entities Kurumsal müşteri entity listesi
     * @return Kurumsal müşteri response DTO listesi
     */
    public List<CorporateCustomerResponse> toResponseList(List<CorporateCustomer> entities) {
        if (entities == null) {
            return null;
        }
        
        return entities.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
    
    /**
     * Oluşturma isteğini kurumsal müşteri entity'sine dönüştürür
     * @param request Kurumsal müşteri oluşturma isteği
     * @return Kurumsal müşteri entity
     */
    public CorporateCustomer toEntity(CreateCorporateCustomerRequest request) {
        if (request == null) {
            return null;
        }
        
        CorporateCustomer entity = new CorporateCustomer();
        entity.setEmail(request.getEmail());
        entity.setPhoneNumber(request.getPhoneNumber());
        entity.setTaxNumber(request.getTaxNumber());
        entity.setCompanyName(request.getCompanyName());
        entity.setSector(request.getSector());
        entity.setFoundationYear(request.getFoundationYear());
        entity.setEmployeeCount(request.getEmployeeCount());
        entity.setAnnualRevenue(request.getAnnualRevenue());
        entity.setWebsite(request.getWebsite());
        entity.setActive(true); // Yeni oluşturulan müşteri aktif olarak işaretlenir
        
        return entity;
    }
    
    /**
     * Güncelleme isteğini mevcut kurumsal müşteri entity'sine uygular
     * @param request Kurumsal müşteri güncelleme isteği
     * @param existingEntity Mevcut kurumsal müşteri entity
     * @return Güncellenmiş kurumsal müşteri entity
     */
    public CorporateCustomer toEntity(UpdateCorporateCustomerRequest request, CorporateCustomer existingEntity) {
        if (request == null || existingEntity == null) {
            return existingEntity;
        }
        
        existingEntity.setEmail(request.getEmail());
        existingEntity.setPhoneNumber(request.getPhoneNumber());
        existingEntity.setTaxNumber(request.getTaxNumber());
        existingEntity.setCompanyName(request.getCompanyName());
        existingEntity.setSector(request.getSector());
        existingEntity.setFoundationYear(request.getFoundationYear());
        existingEntity.setEmployeeCount(request.getEmployeeCount());
        existingEntity.setAnnualRevenue(request.getAnnualRevenue());
        existingEntity.setWebsite(request.getWebsite());
        existingEntity.setActive(request.isActive());
        
        return existingEntity;
    }
} 