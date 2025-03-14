package com.tobeto.banking.business.dtos.requests;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Kredi koşulu güncelleme isteği DTO'su
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCreditConditionRequest {
    
    @NotNull(message = "ID boş olamaz")
    private Long id;
    
    @NotNull(message = "Kredi türü ID boş olamaz")
    private Long creditTypeId;
    
    @NotBlank(message = "Koşul adı boş olamaz")
    @Size(max = 100, message = "Koşul adı en fazla 100 karakter olabilir")
    private String conditionName;
    
    @NotBlank(message = "Koşul değeri boş olamaz")
    @Size(max = 255, message = "Koşul değeri en fazla 255 karakter olabilir")
    private String conditionValue;
    
    @Size(max = 1000, message = "Açıklama en fazla 1000 karakter olabilir")
    private String description;
} 