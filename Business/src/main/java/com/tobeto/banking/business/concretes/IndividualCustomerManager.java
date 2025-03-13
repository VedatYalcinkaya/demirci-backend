package com.tobeto.banking.business.concretes;

import com.tobeto.banking.business.abstracts.IndividualCustomerService;
import com.tobeto.banking.business.constants.CustomerMessages;
import com.tobeto.banking.business.dtos.requests.CreateIndividualCustomerRequest;
import com.tobeto.banking.business.dtos.requests.UpdateIndividualCustomerRequest;
import com.tobeto.banking.business.dtos.responses.IndividualCustomerResponse;
import com.tobeto.banking.business.mappings.IndividualCustomerMapper;
import com.tobeto.banking.business.rules.IndividualCustomerBusinessRules;
import com.tobeto.banking.core.business.BaseService;
import com.tobeto.banking.core.exceptions.BusinessException;
import com.tobeto.banking.entities.concretes.IndividualCustomer;
import com.tobeto.banking.repositories.abstracts.IIndividualCustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Bireysel müşteri servisi implementasyonu
 */
@Service
public class IndividualCustomerManager extends BaseService<IndividualCustomer, IIndividualCustomerRepository, Long> implements IndividualCustomerService {
    
    private final IndividualCustomerBusinessRules rules;
    private final IndividualCustomerMapper mapper;
    
    public IndividualCustomerManager(IIndividualCustomerRepository repository, IndividualCustomerBusinessRules rules, IndividualCustomerMapper mapper) {
        super(repository);
        this.rules = rules;
        this.mapper = mapper;
    }
    
    @Override
    public Optional<IndividualCustomer> findByIdentityNumber(String identityNumber) {
        return repository.findByIdentityNumber(identityNumber);
    }
    
    @Override
    public Optional<IndividualCustomer> findByEmail(String email) {
        return repository.findByEmail(email);
    }
    
    @Override
    public List<IndividualCustomer> findByFirstNameAndLastName(String firstName, String lastName) {
        return repository.findByFirstNameAndLastName(firstName, lastName);
    }
    
    @Override
    public List<IndividualCustomer> findByAgeBetween(int minAge, int maxAge) {
        return repository.findByAgeBetween(minAge, maxAge);
    }
    
    @Override
    public List<IndividualCustomer> searchByName(String keyword) {
        return repository.searchByName(keyword);
    }
    
    @Override
    public List<IndividualCustomerResponse> getAllDto() {
        List<IndividualCustomer> customers = repository.findAll();
        return mapper.toResponseList(customers);
    }
    
    @Override
    public IndividualCustomerResponse getDtoById(Long id) {
        rules.checkIfIndividualCustomerExists(id);
        IndividualCustomer customer = repository.findById(id)
                .orElseThrow(() -> new BusinessException(CustomerMessages.INDIVIDUAL_CUSTOMER_NOT_FOUND));
        return mapper.toResponse(customer);
    }
    
    @Override
    @Transactional
    public IndividualCustomerResponse addDto(CreateIndividualCustomerRequest request) {
        // Validasyon kuralları
        rules.validateIndividualCustomer(request.getEmail(), request.getPhoneNumber(), request.getIdentityNumber(), request.getBirthDate());
        rules.checkIfIdentityNumberExists(request.getIdentityNumber());
        rules.checkIfEmailExists(request.getEmail());
        
        // Entity'ye dönüştür ve kaydet
        IndividualCustomer customer = mapper.toEntity(request);
        customer = repository.save(customer);
        
        return mapper.toResponse(customer);
    }
    
    @Override
    @Transactional
    public IndividualCustomerResponse updateDto(UpdateIndividualCustomerRequest request) {
        // Validasyon kuralları
        rules.checkIfIndividualCustomerExists(request.getId());
        rules.validateIndividualCustomer(request.getEmail(), request.getPhoneNumber(), request.getIdentityNumber(), request.getBirthDate());
        
        // Mevcut müşteriyi getir
        IndividualCustomer existingCustomer = repository.findById(request.getId())
                .orElseThrow(() -> new BusinessException(CustomerMessages.INDIVIDUAL_CUSTOMER_NOT_FOUND));
        
        // TC kimlik numarası değiştiyse kontrol et
        if (!existingCustomer.getIdentityNumber().equals(request.getIdentityNumber())) {
            rules.checkIfIdentityNumberExists(request.getIdentityNumber());
        }
        
        // E-posta değiştiyse kontrol et
        if (!existingCustomer.getEmail().equals(request.getEmail())) {
            rules.checkIfEmailExists(request.getEmail());
        }
        
        // Entity'yi güncelle ve kaydet
        IndividualCustomer customer = mapper.toEntity(request, existingCustomer);
        customer = repository.save(customer);
        
        return mapper.toResponse(customer);
    }
} 