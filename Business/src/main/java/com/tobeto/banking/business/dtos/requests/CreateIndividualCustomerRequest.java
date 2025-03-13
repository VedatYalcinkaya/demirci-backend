package com.tobeto.banking.business.dtos.requests;

import com.tobeto.banking.core.entities.IDto;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Bireysel müşteri oluşturma isteği
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateIndividualCustomerRequest implements IDto {
    
    @NotBlank(message = "Ad boş olamaz")
    @Size(min = 2, max = 50, message = "Ad 2-50 karakter arasında olmalıdır")
    private String firstName;
    
    @NotBlank(message = "Soyad boş olamaz")
    @Size(min = 2, max = 50, message = "Soyad 2-50 karakter arasında olmalıdır")
    private String lastName;
    
    @NotBlank(message = "TC kimlik numarası boş olamaz")
    @Pattern(regexp = "^[0-9]{11}$", message = "TC kimlik numarası 11 haneli olmalıdır")
    private String identityNumber;
    
    @NotNull(message = "Doğum tarihi boş olamaz")
    @Past(message = "Doğum tarihi geçmiş bir tarih olmalıdır")
    private LocalDate birthDate;
    
    @NotBlank(message = "E-posta adresi boş olamaz")
    @Email(message = "Geçerli bir e-posta adresi giriniz")
    @Size(max = 100, message = "E-posta adresi en fazla 100 karakter olmalıdır")
    private String email;
    
    @NotBlank(message = "Telefon numarası boş olamaz")
    @Pattern(regexp = "^[0-9]{10,15}$", message = "Geçerli bir telefon numarası giriniz")
    private String phoneNumber;
    
    @Size(max = 255, message = "Adres en fazla 255 karakter olmalıdır")
    private String address;
    
    @Size(max = 50, message = "Uyruk en fazla 50 karakter olmalıdır")
    private String nationality;
    
    @Size(max = 10, message = "Cinsiyet en fazla 10 karakter olmalıdır")
    private String gender;
    
    @Size(max = 20, message = "Medeni durum en fazla 20 karakter olmalıdır")
    private String maritalStatus;
    
    @Size(max = 50, message = "Eğitim seviyesi en fazla 50 karakter olmalıdır")
    private String educationLevel;
    
    @Size(max = 50, message = "Meslek en fazla 50 karakter olmalıdır")
    private String profession;
} 