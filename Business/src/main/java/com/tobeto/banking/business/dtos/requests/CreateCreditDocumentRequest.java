package com.tobeto.banking.business.dtos.requests;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Kredi belgesi oluşturma isteği DTO'su
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCreditDocumentRequest {
    
    @NotNull(message = "Kredi başvurusu ID boş olamaz")
    private Long creditApplicationId;
    
    @NotBlank(message = "Belge türü boş olamaz")
    @Size(max = 100, message = "Belge türü en fazla 100 karakter olabilir")
    private String documentType;
    
    @NotBlank(message = "Belge adı boş olamaz")
    @Size(max = 255, message = "Belge adı en fazla 255 karakter olabilir")
    private String documentName;
    
    @NotBlank(message = "Belge yolu boş olamaz")
    @Size(max = 1000, message = "Belge yolu en fazla 1000 karakter olabilir")
    private String documentPath;
} 