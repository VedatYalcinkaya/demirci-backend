package com.tobeto.banking.business.mappings;

import com.tobeto.banking.business.dtos.requests.CreateIndividualCustomerRequest;
import com.tobeto.banking.business.dtos.requests.UpdateIndividualCustomerRequest;
import com.tobeto.banking.business.dtos.responses.IndividualCustomerResponse;
import com.tobeto.banking.entities.concretes.IndividualCustomer;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Bireysel müşteri entity ve DTO'ları arasında dönüşüm yapan sınıf
 */
@Component
public class IndividualCustomerMapper {
    
    /**
     * IndividualCustomer entity'sini IndividualCustomerResponse DTO'suna dönüştürür
     * @param customer IndividualCustomer entity
     * @return IndividualCustomerResponse DTO
     */
    public IndividualCustomerResponse toResponse(IndividualCustomer customer) {
        if (customer == null) {
            return null;
        }
        
        IndividualCustomerResponse response = new IndividualCustomerResponse();
        response.setId(customer.getId());
        response.setFirstName(customer.getFirstName());
        response.setLastName(customer.getLastName());
        response.setIdentityNumber(customer.getIdentityNumber());
        response.setBirthDate(customer.getBirthDate());
        response.setEmail(customer.getEmail());
        response.setPhoneNumber(customer.getPhoneNumber());
        response.setAddress(customer.getAddress());
        response.setNationality(customer.getNationality());
        response.setGender(customer.getGender());
        response.setMaritalStatus(customer.getMaritalStatus());
        response.setEducationLevel(customer.getEducationLevel());
        response.setProfession(customer.getProfession());
        response.setCreatedDate(customer.getCreatedDate());
        response.setUpdatedDate(customer.getUpdatedDate());
        response.setActive(customer.isActive());
        
        return response;
    }
    
    /**
     * IndividualCustomer entity listesini IndividualCustomerResponse DTO listesine dönüştürür
     * @param customers IndividualCustomer entity listesi
     * @return IndividualCustomerResponse DTO listesi
     */
    public List<IndividualCustomerResponse> toResponseList(List<IndividualCustomer> customers) {
        if (customers == null) {
            return null;
        }
        
        return customers.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
    
    /**
     * CreateIndividualCustomerRequest DTO'sunu IndividualCustomer entity'sine dönüştürür
     * @param request CreateIndividualCustomerRequest DTO
     * @return IndividualCustomer entity
     */
    public IndividualCustomer toEntity(CreateIndividualCustomerRequest request) {
        if (request == null) {
            return null;
        }
        
        IndividualCustomer customer = new IndividualCustomer();
        customer.setFirstName(request.getFirstName());
        customer.setLastName(request.getLastName());
        customer.setIdentityNumber(request.getIdentityNumber());
        customer.setBirthDate(request.getBirthDate());
        customer.setEmail(request.getEmail());
        customer.setPhoneNumber(request.getPhoneNumber());
        customer.setAddress(request.getAddress());
        customer.setNationality(request.getNationality());
        customer.setGender(request.getGender());
        customer.setMaritalStatus(request.getMaritalStatus());
        customer.setEducationLevel(request.getEducationLevel());
        customer.setProfession(request.getProfession());
        customer.setActive(true);
        
        return customer;
    }
    
    /**
     * UpdateIndividualCustomerRequest DTO'sunu IndividualCustomer entity'sine dönüştürür
     * @param request UpdateIndividualCustomerRequest DTO
     * @param customer Güncellenecek IndividualCustomer entity
     * @return Güncellenmiş IndividualCustomer entity
     */
    public IndividualCustomer toEntity(UpdateIndividualCustomerRequest request, IndividualCustomer customer) {
        if (request == null || customer == null) {
            return customer;
        }
        
        customer.setFirstName(request.getFirstName());
        customer.setLastName(request.getLastName());
        customer.setIdentityNumber(request.getIdentityNumber());
        customer.setBirthDate(request.getBirthDate());
        customer.setEmail(request.getEmail());
        customer.setPhoneNumber(request.getPhoneNumber());
        customer.setAddress(request.getAddress());
        customer.setNationality(request.getNationality());
        customer.setGender(request.getGender());
        customer.setMaritalStatus(request.getMaritalStatus());
        customer.setEducationLevel(request.getEducationLevel());
        customer.setProfession(request.getProfession());
        
        return customer;
    }
} 