package com.tobeto.banking.business.concretes;

import com.tobeto.banking.business.abstracts.CreditTypeService;
import com.tobeto.banking.business.dtos.requests.CreateCreditTypeRequest;
import com.tobeto.banking.business.dtos.requests.GetCreditTypeRequest;
import com.tobeto.banking.business.dtos.requests.UpdateCreditTypeRequest;
import com.tobeto.banking.business.dtos.responses.CreditConditionResponse;
import com.tobeto.banking.business.dtos.responses.CreditTypeResponse;
import com.tobeto.banking.core.crosscuttingconcerns.exceptions.types.BusinessException;
import com.tobeto.banking.core.utilities.mapping.ModelMapperService;
import com.tobeto.banking.entities.concretes.CreditType;
import com.tobeto.banking.repositories.abstracts.CreditTypeRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Kredi türleri için servis implementasyonu
 */
@Service
@AllArgsConstructor
public class CreditTypeManager implements CreditTypeService {
    
    private final CreditTypeRepository creditTypeRepository;
    private final ModelMapperService modelMapperService;
    
    @Override
    public CreditTypeResponse create(CreateCreditTypeRequest request) {
        // Aynı isimde kredi türü var mı kontrolü
        if (creditTypeRepository.findByName(request.getName()) != null) {
            throw new BusinessException("Bu isimde bir kredi türü zaten mevcut");
        }
        
        // Parent ID kontrolü
        if (request.getParentId() != null) {
            CreditType parentType = creditTypeRepository.findById(request.getParentId())
                    .orElseThrow(() -> new BusinessException("Belirtilen ana kredi türü bulunamadı"));
            
            // Ana kredi türünün müşteri tipi ile yeni kredi türünün müşteri tipi aynı olmalı
            if (!parentType.getCustomerType().equals(request.getCustomerType())) {
                throw new BusinessException("Alt kredi türü, ana kredi türü ile aynı müşteri tipine sahip olmalıdır");
            }
        }
        
        // Min ve max değer kontrolleri
        if (request.getMinAmount().compareTo(request.getMaxAmount()) > 0) {
            throw new BusinessException("Minimum tutar, maksimum tutardan büyük olamaz");
        }
        
        if (request.getMinTerm() > request.getMaxTerm()) {
            throw new BusinessException("Minimum vade, maksimum vadeden büyük olamaz");
        }
        
        // DTO'yu entity'ye dönüştür
        CreditType creditType = modelMapperService.forRequest().map(request, CreditType.class);
        creditType.setActive(true);
        
        // Veritabanına kaydet
        CreditType savedCreditType = creditTypeRepository.save(creditType);
        
        // Entity'yi DTO'ya dönüştür ve döndür
        return modelMapperService.forResponse().map(savedCreditType, CreditTypeResponse.class);
    }
    
    @Override
    public CreditTypeResponse update(UpdateCreditTypeRequest request) {
        // Kredi türü var mı kontrolü
        CreditType existingCreditType = creditTypeRepository.findById(request.getId())
                .orElseThrow(() -> new BusinessException("Güncellenecek kredi türü bulunamadı"));
        
        // İsim değiştiriliyorsa, aynı isimde başka bir kredi türü var mı kontrolü
        if (!existingCreditType.getName().equals(request.getName()) && 
                creditTypeRepository.findByName(request.getName()) != null) {
            throw new BusinessException("Bu isimde bir kredi türü zaten mevcut");
        }
        
        // Parent ID kontrolü
        if (request.getParentId() != null) {
            // Kendisini parent olarak seçemez
            if (request.getParentId().equals(request.getId())) {
                throw new BusinessException("Bir kredi türü kendisini ana kredi türü olarak seçemez");
            }
            
            CreditType parentType = creditTypeRepository.findById(request.getParentId())
                    .orElseThrow(() -> new BusinessException("Belirtilen ana kredi türü bulunamadı"));
            
            // Ana kredi türünün müşteri tipi ile güncellenen kredi türünün müşteri tipi aynı olmalı
            if (!parentType.getCustomerType().equals(request.getCustomerType())) {
                throw new BusinessException("Alt kredi türü, ana kredi türü ile aynı müşteri tipine sahip olmalıdır");
            }
        }
        
        // Min ve max değer kontrolleri
        if (request.getMinAmount().compareTo(request.getMaxAmount()) > 0) {
            throw new BusinessException("Minimum tutar, maksimum tutardan büyük olamaz");
        }
        
        if (request.getMinTerm() > request.getMaxTerm()) {
            throw new BusinessException("Minimum vade, maksimum vadeden büyük olamaz");
        }
        
        // DTO'yu entity'ye dönüştür
        modelMapperService.forRequest().map(request, existingCreditType);
        
        // Veritabanına kaydet
        CreditType updatedCreditType = creditTypeRepository.save(existingCreditType);
        
        // Entity'yi DTO'ya dönüştür ve döndür
        return modelMapperService.forResponse().map(updatedCreditType, CreditTypeResponse.class);
    }
    
    @Override
    public CreditTypeResponse getById(Long id) {
        CreditType creditType = creditTypeRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Kredi türü bulunamadı"));
        
        CreditTypeResponse response = modelMapperService.forResponse().map(creditType, CreditTypeResponse.class);
        
        // Alt kredi türlerini ekle
        if (creditType.getParent() == null) {
            List<CreditType> subTypes = creditTypeRepository.findByParentId(id);
            if (!subTypes.isEmpty()) {
                response.setSubTypes(subTypes.stream()
                        .map(subType -> modelMapperService.forResponse().map(subType, CreditTypeResponse.class))
                        .collect(Collectors.toList()));
            }
        }
        
        return response;
    }
    
    @Override
    public List<CreditTypeResponse> getAll() {
        List<CreditType> creditTypes = creditTypeRepository.findAll();
        return creditTypes.stream()
                .map(creditType -> modelMapperService.forResponse().map(creditType, CreditTypeResponse.class))
                .collect(Collectors.toList());
    }
    
    @Override
    public List<CreditTypeResponse> getAllByFilter(GetCreditTypeRequest request) {
        List<CreditType> filteredTypes = new ArrayList<>();
        
        // Filtreleme mantığı
        if (request.getId() != null) {
            creditTypeRepository.findById(request.getId()).ifPresent(filteredTypes::add);
        } else {
            filteredTypes = creditTypeRepository.findAll();
            
            // İsme göre filtrele
            if (request.getName() != null && !request.getName().isEmpty()) {
                filteredTypes = filteredTypes.stream()
                        .filter(type -> type.getName().toLowerCase().contains(request.getName().toLowerCase()))
                        .collect(Collectors.toList());
            }
            
            // Müşteri tipine göre filtrele
            if (request.getCustomerType() != null) {
                filteredTypes = filteredTypes.stream()
                        .filter(type -> type.getCustomerType().equals(request.getCustomerType()))
                        .collect(Collectors.toList());
            }
            
            // Ana kredi türüne göre filtrele
            if (request.getParentId() != null) {
                filteredTypes = filteredTypes.stream()
                        .filter(type -> type.getParent() != null && type.getParent().getId().equals(request.getParentId()))
                        .collect(Collectors.toList());
            }
            
            // Aktiflik durumuna göre filtrele
            if (request.getIsActive() != null) {
                filteredTypes = filteredTypes.stream()
                        .filter(type -> type.isActive() == request.getIsActive())
                        .collect(Collectors.toList());
            }
        }
        
        return filteredTypes.stream()
                .map(creditType -> modelMapperService.forResponse().map(creditType, CreditTypeResponse.class))
                .collect(Collectors.toList());
    }
    
    @Override
    public Page<CreditTypeResponse> getAllPaged(Pageable pageable) {
        Page<CreditType> creditTypePage = creditTypeRepository.findAll(pageable);
        return creditTypePage.map(creditType -> modelMapperService.forResponse().map(creditType, CreditTypeResponse.class));
    }
    
    @Override
    public List<CreditTypeResponse> getAllByCustomerType(CreditType.CustomerType customerType) {
        List<CreditType> creditTypes = creditTypeRepository.findByCustomerType(customerType);
        return creditTypes.stream()
                .map(creditType -> modelMapperService.forResponse().map(creditType, CreditTypeResponse.class))
                .collect(Collectors.toList());
    }
    
    @Override
    public List<CreditTypeResponse> getAllParentTypes() {
        List<CreditType> parentTypes = creditTypeRepository.findByParentIsNull();
        
        List<CreditTypeResponse> responses = new ArrayList<>();
        for (CreditType parentType : parentTypes) {
            CreditTypeResponse response = modelMapperService.forResponse().map(parentType, CreditTypeResponse.class);
            
            // Alt kredi türlerini ekle
            List<CreditType> subTypes = creditTypeRepository.findByParentId(parentType.getId());
            if (!subTypes.isEmpty()) {
                response.setSubTypes(subTypes.stream()
                        .map(subType -> modelMapperService.forResponse().map(subType, CreditTypeResponse.class))
                        .collect(Collectors.toList()));
            }
            
            responses.add(response);
        }
        
        return responses;
    }
    
    @Override
    public List<CreditTypeResponse> getAllByParentId(Long parentId) {
        List<CreditType> subTypes = creditTypeRepository.findByParentId(parentId);
        return subTypes.stream()
                .map(creditType -> modelMapperService.forResponse().map(creditType, CreditTypeResponse.class))
                .collect(Collectors.toList());
    }
    
    @Override
    public List<CreditTypeResponse> getAllActive() {
        List<CreditType> activeTypes = creditTypeRepository.findByIsActiveTrue();
        return activeTypes.stream()
                .map(creditType -> modelMapperService.forResponse().map(creditType, CreditTypeResponse.class))
                .collect(Collectors.toList());
    }
    
    @Override
    public void delete(Long id) {
        CreditType creditType = creditTypeRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Silinecek kredi türü bulunamadı"));
        
        // Alt kredi türleri varsa silme işlemi yapılamaz
        List<CreditType> subTypes = creditTypeRepository.findByParentId(id);
        if (!subTypes.isEmpty()) {
            throw new BusinessException("Bu kredi türüne bağlı alt kredi türleri bulunmaktadır. Önce alt kredi türlerini silmelisiniz.");
        }
        
        // Soft delete
        creditType.setActive(false);
        creditTypeRepository.save(creditType);
    }
} 