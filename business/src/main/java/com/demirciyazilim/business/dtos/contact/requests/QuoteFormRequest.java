package com.demirciyazilim.business.dtos.contact.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuoteFormRequest {
    
    @NotBlank(message = "İsim Soyisim boş olamaz")
    @Size(min = 2, max = 100, message = "İsim Soyisim 2-100 karakter arasında olmalıdır")
    private String fullName;
    
    @NotBlank(message = "E-posta adresi boş olamaz")
    @Email(message = "Geçerli bir e-posta adresi giriniz")
    private String email;
    
    @NotBlank(message = "Telefon numarası boş olamaz")
    private String phone;
    
    @NotBlank(message = "İlgilendiğiniz hizmet boş olamaz")
    private String service;
    
    @NotBlank(message = "Mesaj boş olamaz")
    @Size(min = 10, max = 1000, message = "Mesaj 10-1000 karakter arasında olmalıdır")
    private String message;
} 