package com.tobeto.banking.business.concretes;

import com.tobeto.banking.business.abstracts.CorporateCustomerService;
import com.tobeto.banking.business.constants.CustomerMessages;
import com.tobeto.banking.business.dtos.requests.CreateCorporateCustomerRequest;
import com.tobeto.banking.business.dtos.requests.UpdateCorporateCustomerRequest;
import com.tobeto.banking.business.dtos.responses.CorporateCustomerResponse;
import com.tobeto.banking.business.mappings.CorporateCustomerMapper;
import com.tobeto.banking.business.rules.CorporateCustomerBusinessRules;
import com.tobeto.banking.core.business.BaseService;
import com.tobeto.banking.core.exceptions.BusinessException;
import com.tobeto.banking.entities.concretes.CorporateCustomer;
import com.tobeto.banking.repositories.abstracts.ICorporateCustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Kurumsal müşteri servisi implementasyonu
 */
@Service
public class CorporateCustomerManager extends BaseService<CorporateCustomer, ICorporateCustomerRepository, Long> implements CorporateCustomerService {
    
    private final CorporateCustomerBusinessRules rules;
    private final CorporateCustomerMapper mapper;
    
    public CorporateCustomerManager(ICorporateCustomerRepository repository, CorporateCustomerBusinessRules rules, CorporateCustomerMapper mapper) {
        super(repository);
        this.rules = rules;
        this.mapper = mapper;
    }
    
    @Override
    public Optional<CorporateCustomer> findByTaxNumber(String taxNumber) {
        return repository.findByTaxNumber(taxNumber);
    }
    
    @Override
    public Optional<CorporateCustomer> findByCompanyName(String companyName) {
        return repository.findByCompanyName(companyName);
    }
    
    @Override
    public Optional<CorporateCustomer> findByEmail(String email) {
        return repository.findByEmail(email);
    }
    
    @Override
    public List<CorporateCustomer> findBySector(String sector) {
        return repository.findBySector(sector);
    }
    
    @Override
    public List<CorporateCustomer> findByEmployeeCountBetween(int minCount, int maxCount) {
        return repository.findByEmployeeCountBetween(minCount, maxCount);
    }
    
    @Override
    public List<CorporateCustomer> searchByCompanyName(String keyword) {
        return repository.searchByCompanyName(keyword);
    }
    
    @Override
    public List<CorporateCustomer> findByFoundationYearGreaterThanEqual(int year) {
        return repository.findByFoundationYearGreaterThanEqual(year);
    }
    
    @Override
    public List<CorporateCustomer> findBySectorAndMinEmployeeCount(String sector, int minEmployeeCount) {
        return repository.findBySectorAndMinEmployeeCount(sector, minEmployeeCount);
    }
    
    @Override
    public List<CorporateCustomerResponse> getAllDto() {
        List<CorporateCustomer> customers = repository.findAll();
        return mapper.toResponseList(customers);
    }
    
    @Override
    public CorporateCustomerResponse getDtoById(Long id) {
        rules.checkIfCorporateCustomerExists(id);
        CorporateCustomer customer = repository.findById(id)
                .orElseThrow(() -> new BusinessException(CustomerMessages.CORPORATE_CUSTOMER_NOT_FOUND));
        return mapper.toResponse(customer);
    }
    
    @Override
    @Transactional
    public CorporateCustomerResponse addDto(CreateCorporateCustomerRequest request) {
        // Validasyon kuralları
        rules.validateCorporateCustomer(request.getEmail(), request.getPhoneNumber(), request.getTaxNumber(), request.getCompanyName(), request.getFoundationYear());
        rules.checkIfTaxNumberExists(request.getTaxNumber());
        rules.checkIfEmailExists(request.getEmail());
        
        // Entity'ye dönüştür ve kaydet
        CorporateCustomer customer = mapper.toEntity(request);
        customer = repository.save(customer);
        
        return mapper.toResponse(customer);
    }
    
    @Override
    @Transactional
    public CorporateCustomerResponse updateDto(UpdateCorporateCustomerRequest request) {
        // Validasyon kuralları
        rules.checkIfCorporateCustomerExists(request.getId());
        rules.validateCorporateCustomer(request.getEmail(), request.getPhoneNumber(), request.getTaxNumber(), request.getCompanyName(), request.getFoundationYear());
        
        // Mevcut müşteriyi getir
        CorporateCustomer existingCustomer = repository.findById(request.getId())
                .orElseThrow(() -> new BusinessException(CustomerMessages.CORPORATE_CUSTOMER_NOT_FOUND));
        
        // Vergi numarası değiştiyse kontrol et
        if (!existingCustomer.getTaxNumber().equals(request.getTaxNumber())) {
            rules.checkIfTaxNumberExists(request.getTaxNumber());
        }
        
        // E-posta değiştiyse kontrol et
        if (!existingCustomer.getEmail().equals(request.getEmail())) {
            rules.checkIfEmailExists(request.getEmail());
        }
        
        // Entity'yi güncelle ve kaydet
        CorporateCustomer customer = mapper.toEntity(request, existingCustomer);
        customer = repository.save(customer);
        
        return mapper.toResponse(customer);
    }
} 