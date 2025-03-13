package com.tobeto.banking.business.concretes;

import com.tobeto.banking.business.abstracts.CustomerService;
import com.tobeto.banking.business.constants.CustomerMessages;
import com.tobeto.banking.business.dtos.responses.CustomerResponse;
import com.tobeto.banking.business.mappings.CustomerMapper;
import com.tobeto.banking.business.rules.CustomerBusinessRules;
import com.tobeto.banking.core.business.BaseService;
import com.tobeto.banking.core.crosscuttingconcerns.exceptions.types.BusinessException;
import com.tobeto.banking.entities.concretes.Customer;
import com.tobeto.banking.repositories.abstracts.ICustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Müşteri servisi implementasyonu
 */
@Service
public class CustomerManager extends BaseService<Customer, ICustomerRepository, Long> implements CustomerService {
    
    private final CustomerBusinessRules rules;
    private final CustomerMapper mapper;
    
    public CustomerManager(ICustomerRepository repository, CustomerBusinessRules rules, CustomerMapper mapper) {
        super(repository);
        this.rules = rules;
        this.mapper = mapper;
    }
    
    @Override
    public Optional<Customer> findByEmail(String email) {
        return repository.findByEmail(email);
    }
    
    @Override
    public List<CustomerResponse> getAllDto() {
        List<Customer> customers = repository.findAll();
        return mapper.toResponseList(customers);
    }
    
    @Override
    public CustomerResponse getDtoById(Long id) {
        rules.checkIfCustomerExists(id);
        Customer customer = repository.findById(id)
                .orElseThrow(() -> new BusinessException(CustomerMessages.CUSTOMER_NOT_FOUND));
        return mapper.toResponse(customer);
    }
} 