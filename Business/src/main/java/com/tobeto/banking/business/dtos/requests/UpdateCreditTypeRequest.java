package com.tobeto.banking.business.dtos.requests;

import com.tobeto.banking.entities.concretes.CreditType;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Kredi türü güncelleme isteği DTO'su
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCreditTypeRequest {
    
    @NotNull(message = "ID boş olamaz")
    private Long id;
    
    @NotBlank(message = "Kredi türü adı boş olamaz")
    @Size(max = 100, message = "Kredi türü adı en fazla 100 karakter olabilir")
    private String name;
    
    @Size(max = 1000, message = "Açıklama en fazla 1000 karakter olabilir")
    private String description;
    
    @NotNull(message = "Müşteri tipi boş olamaz")
    private CreditType.CustomerType customerType;
    
    private Long parentId;
    
    @NotNull(message = "Faiz oranı boş olamaz")
    @DecimalMin(value = "0.0", inclusive = true, message = "Faiz oranı 0 veya daha büyük olmalıdır")
    @DecimalMax(value = "100.0", inclusive = true, message = "Faiz oranı 100 veya daha küçük olmalıdır")
    private BigDecimal interestRate;
    
    @NotNull(message = "Minimum tutar boş olamaz")
    @DecimalMin(value = "0.0", inclusive = true, message = "Minimum tutar 0 veya daha büyük olmalıdır")
    private BigDecimal minAmount;
    
    @NotNull(message = "Maksimum tutar boş olamaz")
    @DecimalMin(value = "0.0", inclusive = false, message = "Maksimum tutar 0'dan büyük olmalıdır")
    private BigDecimal maxAmount;
    
    @NotNull(message = "Minimum vade boş olamaz")
    @Min(value = 1, message = "Minimum vade 1 veya daha büyük olmalıdır")
    private Integer minTerm;
    
    @NotNull(message = "Maksimum vade boş olamaz")
    @Min(value = 1, message = "Maksimum vade 1 veya daha büyük olmalıdır")
    private Integer maxTerm;
    
    @NotNull(message = "Aktiflik durumu boş olamaz")
    private Boolean isActive;
} 