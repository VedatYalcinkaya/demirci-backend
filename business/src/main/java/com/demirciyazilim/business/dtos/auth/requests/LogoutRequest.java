package com.demirciyazilim.business.dtos.auth.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LogoutRequest {
    
    @NotBlank(message = "Refresh token boş olamaz")
    private String refreshToken;
} 