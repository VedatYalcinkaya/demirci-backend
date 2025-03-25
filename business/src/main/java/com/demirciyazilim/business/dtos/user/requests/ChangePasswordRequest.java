package com.demirciyazilim.business.dtos.user.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordRequest {
    
    private Long id;
    private String oldPassword;
    private String newPassword;
} 