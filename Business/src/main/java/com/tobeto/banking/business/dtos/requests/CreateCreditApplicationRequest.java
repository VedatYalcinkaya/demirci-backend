package com.tobeto.banking.business.dtos.requests;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Kredi başvurusu oluşturma isteği DTO'su
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCreditApplicationRequest {
    
    @NotNull(message = "Müşteri ID boş olamaz")
    private Long customerId;
    
    @NotNull(message = "Kredi türü ID boş olamaz")
    private Long creditTypeId;
    
    @NotNull(message = "Tutar boş olamaz")
    @DecimalMin(value = "0.0", inclusive = false, message = "Tutar 0'dan büyük olmalıdır")
    private BigDecimal amount;
    
    @NotNull(message = "Vade boş olamaz")
    @Min(value = 1, message = "Vade 1 veya daha büyük olmalıdır")
    private Integer term;
    
    @Size(max = 1000, message = "Açıklama en fazla 1000 karakter olabilir")
    private String description;
} 