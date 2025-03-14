package com.tobeto.banking.business.dtos.requests;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Kredi skoru güncelleme isteği DTO'su
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCreditScoreRequest {
    
    @NotNull(message = "ID boş olamaz")
    private Long id;
    
    @NotNull(message = "Müşteri ID boş olamaz")
    private Long customerId;
    
    @NotNull(message = "Skor boş olamaz")
    @Min(value = 0, message = "Skor 0 veya daha büyük olmalıdır")
    @Max(value = 1900, message = "Skor 1900 veya daha küçük olmalıdır")
    private Integer score;
    
    @NotNull(message = "Geçerlilik süresi boş olamaz")
    private LocalDateTime expiryDate;
}