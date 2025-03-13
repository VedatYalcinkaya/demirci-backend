package com.tobeto.banking.business.mappings;

import com.tobeto.banking.business.dtos.responses.CustomerResponse;
import com.tobeto.banking.entities.concretes.CorporateCustomer;
import com.tobeto.banking.entities.concretes.Customer;
import com.tobeto.banking.entities.concretes.IndividualCustomer;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Müşteri entity ve DTO'ları arasında dönüşüm yapan sınıf
 */
@Component
public class CustomerMapper {
    
    /**
     * Customer entity'sini CustomerResponse DTO'suna dönüştürür
     * @param customer Customer entity
     * @return CustomerResponse DTO
     */
    public CustomerResponse toResponse(Customer customer) {
        if (customer == null) {
            return null;
        }
        
        CustomerResponse response = new CustomerResponse();
        response.setId(customer.getId());
        response.setEmail(customer.getEmail());
        response.setPhoneNumber(customer.getPhoneNumber());
        response.setAddress(customer.getAddress());
        response.setCreatedDate(customer.getCreatedDate());
        response.setUpdatedDate(customer.getUpdatedDate());
        response.setActive(customer.isActive());
        
        // Müşteri tipini belirle
        if (customer instanceof IndividualCustomer) {
            response.setCustomerType("INDIVIDUAL");
        } else if (customer instanceof CorporateCustomer) {
            response.setCustomerType("CORPORATE");
        } else {
            response.setCustomerType("UNKNOWN");
        }
        
        return response;
    }
    
    /**
     * Customer entity listesini CustomerResponse DTO listesine dönüştürür
     * @param customers Customer entity listesi
     * @return CustomerResponse DTO listesi
     */
    public List<CustomerResponse> toResponseList(List<Customer> customers) {
        if (customers == null) {
            return null;
        }
        
        return customers.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
} 