package com.tobeto.banking.business.concretes;

import com.tobeto.banking.business.abstracts.CustomerService;
import com.tobeto.banking.business.constants.CustomerMessages;
import com.tobeto.banking.business.dtos.responses.CustomerResponse;
import com.tobeto.banking.business.dtos.responses.PagedResponse;
import com.tobeto.banking.business.mappings.CustomerMapper;
import com.tobeto.banking.business.rules.CustomerBusinessRules;
import com.tobeto.banking.core.business.BaseService;
import com.tobeto.banking.core.crosscuttingconcerns.exceptions.types.BusinessException;
import com.tobeto.banking.entities.concretes.Customer;
import com.tobeto.banking.repositories.abstracts.CustomerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Müşteri servisi implementasyonu
 */
@Service
public class CustomerManager extends BaseService<Customer, CustomerRepository, Long> implements CustomerService {
    
    private final CustomerBusinessRules rules;
    private final CustomerMapper mapper;
    
    public CustomerManager(CustomerRepository repository, CustomerBusinessRules rules, CustomerMapper mapper) {
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
    public PagedResponse<CustomerResponse> getAllPaged(int pageNo, int pageSize, String sortBy, String sortDir) {
        // Sıralama yönünü belirle
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? 
                Sort.by(sortBy).ascending() : 
                Sort.by(sortBy).descending();
        
        // Sayfalama nesnesi oluştur
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        
        // Veritabanından sayfalanmış veriyi al
        Page<Customer> customerPage = repository.findAll(pageable);
        
        // Entity'leri DTO'lara dönüştür
        List<CustomerResponse> content = mapper.toResponseList(customerPage.getContent());
        
        // Sayfalama yanıtını oluştur
        PagedResponse<CustomerResponse> pagedResponse = new PagedResponse<>();
        pagedResponse.setContent(content);
        pagedResponse.setPageNo(customerPage.getNumber());
        pagedResponse.setPageSize(customerPage.getSize());
        pagedResponse.setTotalElements(customerPage.getTotalElements());
        pagedResponse.setTotalPages(customerPage.getTotalPages());
        pagedResponse.setLast(customerPage.isLast());
        pagedResponse.setFirst(customerPage.isFirst());
        pagedResponse.setEmpty(customerPage.isEmpty());
        pagedResponse.setNumberOfElements(customerPage.getNumberOfElements());
        pagedResponse.setHasNext(customerPage.hasNext());
        pagedResponse.setHasPrevious(customerPage.hasPrevious());
        
        return pagedResponse;
    }
    
    @Override
    public CustomerResponse getDtoById(Long id) {
        rules.checkIfCustomerExists(id);
        Customer customer = repository.findById(id)
                .orElseThrow(() -> new BusinessException(CustomerMessages.CUSTOMER_NOT_FOUND));
        return mapper.toResponse(customer);
    }
} 