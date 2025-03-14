package com.tobeto.banking.business.concretes;

import com.tobeto.banking.business.abstracts.CreditConditionService;
import com.tobeto.banking.business.dtos.requests.CreateCreditConditionRequest;
import com.tobeto.banking.business.dtos.requests.GetCreditConditionRequest;
import com.tobeto.banking.business.dtos.requests.UpdateCreditConditionRequest;
import com.tobeto.banking.business.dtos.responses.CreditConditionResponse;
import com.tobeto.banking.core.crosscuttingconcerns.exceptions.types.BusinessException;
import com.tobeto.banking.core.utilities.mapping.ModelMapperService;
import com.tobeto.banking.entities.concretes.CreditCondition;
import com.tobeto.banking.entities.concretes.CreditType;
import com.tobeto.banking.repositories.abstracts.CreditConditionRepository;
import com.tobeto.banking.repositories.abstracts.CreditTypeRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Kredi koşulları için servis implementasyonu
 */
@Service
@AllArgsConstructor
public class CreditConditionManager implements CreditConditionService {
    
    private final CreditConditionRepository creditConditionRepository;
    private final CreditTypeRepository creditTypeRepository;
    private final ModelMapperService modelMapperService;
    
    @Override
    public CreditConditionResponse create(CreateCreditConditionRequest request) {
        // Kredi türü var mı kontrolü
        CreditType creditType = creditTypeRepository.findById(request.getCreditTypeId())
                .orElseThrow(() -> new BusinessException("Belirtilen kredi türü bulunamadı"));
        
        // Aynı isimde koşul var mı kontrolü
        List<CreditCondition> existingConditions = creditConditionRepository
                .findByCreditTypeIdAndConditionName(request.getCreditTypeId(), request.getConditionName());
        
        if (!existingConditions.isEmpty()) {
            throw new BusinessException("Bu kredi türü için aynı isimde bir koşul zaten mevcut");
        }
        
        // DTO'yu entity'ye dönüştür
        CreditCondition creditCondition = modelMapperService.forRequest().map(request, CreditCondition.class);
        creditCondition.setCreditType(creditType);
        
        // Veritabanına kaydet
        CreditCondition savedCreditCondition = creditConditionRepository.save(creditCondition);
        
        // Entity'yi DTO'ya dönüştür ve döndür
        CreditConditionResponse response = modelMapperService.forResponse().map(savedCreditCondition, CreditConditionResponse.class);
        response.setCreditTypeName(creditType.getName());
        
        return response;
    }
    
    @Override
    public CreditConditionResponse update(UpdateCreditConditionRequest request) {
        // Koşul var mı kontrolü
        CreditCondition existingCondition = creditConditionRepository.findById(request.getId())
                .orElseThrow(() -> new BusinessException("Güncellenecek kredi koşulu bulunamadı"));
        
        // Kredi türü değiştiriliyorsa, yeni kredi türü var mı kontrolü
        CreditType creditType;
        if (!existingCondition.getCreditType().getId().equals(request.getCreditTypeId())) {
            creditType = creditTypeRepository.findById(request.getCreditTypeId())
                    .orElseThrow(() -> new BusinessException("Belirtilen kredi türü bulunamadı"));
            
            // Aynı isimde koşul var mı kontrolü
            List<CreditCondition> existingConditions = creditConditionRepository
                    .findByCreditTypeIdAndConditionName(request.getCreditTypeId(), request.getConditionName());
            
            if (!existingConditions.isEmpty()) {
                throw new BusinessException("Hedef kredi türü için aynı isimde bir koşul zaten mevcut");
            }
        } else {
            creditType = existingCondition.getCreditType();
            
            // İsim değiştiriliyorsa, aynı isimde başka bir koşul var mı kontrolü
            if (!existingCondition.getConditionName().equals(request.getConditionName())) {
                List<CreditCondition> existingConditions = creditConditionRepository
                        .findByCreditTypeIdAndConditionName(request.getCreditTypeId(), request.getConditionName());
                
                if (!existingConditions.isEmpty()) {
                    throw new BusinessException("Bu kredi türü için aynı isimde bir koşul zaten mevcut");
                }
            }
        }
        
        // DTO'yu entity'ye dönüştür
        modelMapperService.forRequest().map(request, existingCondition);
        existingCondition.setCreditType(creditType);
        
        // Veritabanına kaydet
        CreditCondition updatedCreditCondition = creditConditionRepository.save(existingCondition);
        
        // Entity'yi DTO'ya dönüştür ve döndür
        CreditConditionResponse response = modelMapperService.forResponse().map(updatedCreditCondition, CreditConditionResponse.class);
        response.setCreditTypeName(creditType.getName());
        
        return response;
    }
    
    @Override
    public CreditConditionResponse getById(Long id) {
        CreditCondition creditCondition = creditConditionRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Kredi koşulu bulunamadı"));
        
        CreditConditionResponse response = modelMapperService.forResponse().map(creditCondition, CreditConditionResponse.class);
        response.setCreditTypeName(creditCondition.getCreditType().getName());
        
        return response;
    }
    
    @Override
    public List<CreditConditionResponse> getAll() {
        List<CreditCondition> creditConditions = creditConditionRepository.findAll();
        
        return creditConditions.stream()
                .map(condition -> {
                    CreditConditionResponse response = modelMapperService.forResponse().map(condition, CreditConditionResponse.class);
                    response.setCreditTypeName(condition.getCreditType().getName());
                    return response;
                })
                .collect(Collectors.toList());
    }
    
    @Override
    public List<CreditConditionResponse> getAllByFilter(GetCreditConditionRequest request) {
        List<CreditCondition> filteredConditions = new ArrayList<>();
        
        // Filtreleme mantığı
        if (request.getId() != null) {
            creditConditionRepository.findById(request.getId()).ifPresent(filteredConditions::add);
        } else {
            filteredConditions = creditConditionRepository.findAll();
            
            // Kredi türü ID'sine göre filtrele
            if (request.getCreditTypeId() != null) {
                filteredConditions = filteredConditions.stream()
                        .filter(condition -> condition.getCreditType().getId().equals(request.getCreditTypeId()))
                        .collect(Collectors.toList());
            }
            
            // Koşul adına göre filtrele
            if (request.getConditionName() != null && !request.getConditionName().isEmpty()) {
                filteredConditions = filteredConditions.stream()
                        .filter(condition -> condition.getConditionName().toLowerCase().contains(request.getConditionName().toLowerCase()))
                        .collect(Collectors.toList());
            }
        }
        
        return filteredConditions.stream()
                .map(condition -> {
                    CreditConditionResponse response = modelMapperService.forResponse().map(condition, CreditConditionResponse.class);
                    response.setCreditTypeName(condition.getCreditType().getName());
                    return response;
                })
                .collect(Collectors.toList());
    }
    
    @Override
    public Page<CreditConditionResponse> getAllPaged(Pageable pageable) {
        Page<CreditCondition> creditConditionPage = creditConditionRepository.findAll(pageable);
        
        return creditConditionPage.map(condition -> {
            CreditConditionResponse response = modelMapperService.forResponse().map(condition, CreditConditionResponse.class);
            response.setCreditTypeName(condition.getCreditType().getName());
            return response;
        });
    }
    
    @Override
    public List<CreditConditionResponse> getAllByCreditTypeId(Long creditTypeId) {
        // Kredi türü var mı kontrolü
        if (!creditTypeRepository.existsById(creditTypeId)) {
            throw new BusinessException("Belirtilen kredi türü bulunamadı");
        }
        
        List<CreditCondition> conditions = creditConditionRepository.findByCreditTypeId(creditTypeId);
        
        return conditions.stream()
                .map(condition -> {
                    CreditConditionResponse response = modelMapperService.forResponse().map(condition, CreditConditionResponse.class);
                    response.setCreditTypeName(condition.getCreditType().getName());
                    return response;
                })
                .collect(Collectors.toList());
    }
    
    @Override
    public List<CreditConditionResponse> getAllByConditionName(String conditionName) {
        List<CreditCondition> conditions = creditConditionRepository.findByConditionName(conditionName);
        
        return conditions.stream()
                .map(condition -> {
                    CreditConditionResponse response = modelMapperService.forResponse().map(condition, CreditConditionResponse.class);
                    response.setCreditTypeName(condition.getCreditType().getName());
                    return response;
                })
                .collect(Collectors.toList());
    }
    
    @Override
    public List<CreditConditionResponse> getAllByCreditTypeIdAndConditionName(Long creditTypeId, String conditionName) {
        // Kredi türü var mı kontrolü
        if (!creditTypeRepository.existsById(creditTypeId)) {
            throw new BusinessException("Belirtilen kredi türü bulunamadı");
        }
        
        List<CreditCondition> conditions = creditConditionRepository.findByCreditTypeIdAndConditionName(creditTypeId, conditionName);
        
        return conditions.stream()
                .map(condition -> {
                    CreditConditionResponse response = modelMapperService.forResponse().map(condition, CreditConditionResponse.class);
                    response.setCreditTypeName(condition.getCreditType().getName());
                    return response;
                })
                .collect(Collectors.toList());
    }
    
    @Override
    public void delete(Long id) {
        CreditCondition creditCondition = creditConditionRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Silinecek kredi koşulu bulunamadı"));
        
        creditConditionRepository.delete(creditCondition);
    }
} 