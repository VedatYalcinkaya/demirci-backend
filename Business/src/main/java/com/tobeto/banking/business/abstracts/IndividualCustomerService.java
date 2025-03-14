package com.tobeto.banking.business.abstracts;

import com.tobeto.banking.business.dtos.requests.CreateIndividualCustomerRequest;
import com.tobeto.banking.business.dtos.requests.UpdateIndividualCustomerRequest;
import com.tobeto.banking.business.dtos.responses.IndividualCustomerResponse;
import com.tobeto.banking.business.dtos.responses.PagedResponse;
import com.tobeto.banking.core.business.IService;
import com.tobeto.banking.entities.concretes.IndividualCustomer;

import java.util.List;
import java.util.Optional;

/**
 * Bireysel müşteri servisi arayüzü
 */
public interface IndividualCustomerService extends IService<IndividualCustomer, Long> {
    
    /**
     * TC kimlik numarasına göre bireysel müşteri arar
     * @param identityNumber TC kimlik numarası
     * @return Bulunan müşteri veya boş Optional
     */
    Optional<IndividualCustomer> findByIdentityNumber(String identityNumber);
    
    /**
     * E-posta adresine göre bireysel müşteri arar
     * @param email E-posta adresi
     * @return Bulunan müşteri veya boş Optional
     */
    Optional<IndividualCustomer> findByEmail(String email);
    
    /**
     * Ad ve soyada göre bireysel müşteri listesi döner
     * @param firstName Ad
     * @param lastName Soyad
     * @return Bulunan müşteri listesi
     */
    List<IndividualCustomer> findByFirstNameAndLastName(String firstName, String lastName);
    
    /**
     * Tüm bireysel müşterileri DTO olarak döner
     * @return Bireysel müşteri DTO listesi
     */
    List<IndividualCustomerResponse> getAllDto();
    
    /**
     * Sayfalanmış bireysel müşteri listesi döner
     * @param pageNo Sayfa numarası (0 tabanlı)
     * @param pageSize Sayfa başına öğe sayısı
     * @param sortBy Sıralama alanı
     * @param sortDir Sıralama yönü (asc/desc)
     * @return Sayfalanmış bireysel müşteri listesi
     */
    PagedResponse<IndividualCustomerResponse> getAllPaged(int pageNo, int pageSize, String sortBy, String sortDir);
    
    /**
     * ID'ye göre bireysel müşteriyi DTO olarak döner
     * @param id Müşteri ID
     * @return Bireysel müşteri DTO
     */
    IndividualCustomerResponse getDtoById(Long id);
    
    /**
     * Bireysel müşteri ekler
     * @param request Eklenecek müşteri isteği
     * @return Eklenen müşteri yanıtı
     */
    IndividualCustomerResponse addDto(CreateIndividualCustomerRequest request);
    
    /**
     * Bireysel müşteri günceller
     * @param request Güncellenecek müşteri isteği
     * @return Güncellenen müşteri yanıtı
     */
    IndividualCustomerResponse updateDto(UpdateIndividualCustomerRequest request);
    
    /**
     * Bireysel müşteriyi siler
     * @param id Müşteri ID
     */
    void delete(Long id);
} 