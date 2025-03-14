package com.tobeto.banking.business.dtos.requests;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Kredi onayı oluşturma isteği DTO'su
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCreditApprovalRequest {
    
    @NotNull(message = "Kredi başvurusu ID boş olamaz")
    private Long creditApplicationId;
    
    @NotBlank(message = "Onaylayan kişi boş olamaz")
    @Size(max = 100, message = "Onaylayan kişi en fazla 100 karakter olabilir")
    private String approvedBy;
    
    @Size(max = 1000, message = "Notlar en fazla 1000 karakter olabilir")
    private String notes;
} 