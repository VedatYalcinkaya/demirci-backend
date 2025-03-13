package com.tobeto.banking.business.dtos.requests;

import com.tobeto.banking.core.entities.IDto;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Kurumsal müşteri güncelleme isteği
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCorporateCustomerRequest implements IDto {
    
    @NotNull(message = "ID boş olamaz")
    private Long id;
    
    @NotBlank(message = "Şirket adı boş olamaz")
    @Size(min = 2, max = 100, message = "Şirket adı 2-100 karakter arasında olmalıdır")
    private String companyName;
    
    @NotBlank(message = "Vergi numarası boş olamaz")
    @Pattern(regexp = "^[0-9]{10}$", message = "Vergi numarası 10 haneli olmalıdır")
    private String taxNumber;
    
    @Size(max = 20, message = "Sicil numarası en fazla 20 karakter olmalıdır")
    private String registrationNumber;
    
    @Min(value = 1800, message = "Kuruluş yılı 1800'den küçük olamaz")
    @Max(value = 2100, message = "Kuruluş yılı 2100'den büyük olamaz")
    private Integer foundationYear;
    
    @Size(max = 50, message = "Sektör en fazla 50 karakter olmalıdır")
    private String sector;
    
    @Min(value = 0, message = "Çalışan sayısı negatif olamaz")
    private Integer employeeCount;
    
    @Min(value = 0, message = "Yıllık gelir negatif olamaz")
    private Double annualRevenue;
    
    @Size(max = 100, message = "Web sitesi en fazla 100 karakter olmalıdır")
    private String website;
    
    @NotBlank(message = "E-posta adresi boş olamaz")
    @Email(message = "Geçerli bir e-posta adresi giriniz")
    @Size(max = 100, message = "E-posta adresi en fazla 100 karakter olmalıdır")
    private String email;
    
    @NotBlank(message = "Telefon numarası boş olamaz")
    @Pattern(regexp = "^[0-9]{10,15}$", message = "Geçerli bir telefon numarası giriniz")
    private String phoneNumber;
    
    @Size(max = 255, message = "Adres en fazla 255 karakter olmalıdır")
    private String address;
    
    @Size(max = 100, message = "İletişim kişisi adı en fazla 100 karakter olmalıdır")
    private String contactPersonName;
    
    @Size(max = 50, message = "İletişim kişisi ünvanı en fazla 50 karakter olmalıdır")
    private String contactPersonTitle;
    
    /**
     * Müşterinin aktif olup olmadığı
     */
    private boolean active = true;
} 