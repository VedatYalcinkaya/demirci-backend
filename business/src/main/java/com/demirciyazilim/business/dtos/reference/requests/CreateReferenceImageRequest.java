package com.demirciyazilim.business.dtos.reference.requests;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateReferenceImageRequest {
    
    @NotBlank(message = "Resim URL'i boş olamaz")
    @Pattern(regexp = "^(http|https)://.*$", message = "Resim URL'i geçerli bir URL olmalıdır (http:// veya https:// ile başlamalıdır)")
    private String imageUrl;
    
    private String caption;
    
    @Min(value = 0, message = "Görüntüleme sırası negatif olamaz")
    private int displayOrder;
    
    private boolean active = true;
} 