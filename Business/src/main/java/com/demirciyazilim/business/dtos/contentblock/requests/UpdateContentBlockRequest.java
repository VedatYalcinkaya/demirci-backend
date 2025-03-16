package com.demirciyazilim.business.dtos.contentblock.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateContentBlockRequest {
    
    @NotNull(message = "ID boş olamaz")
    private Long id;
    
    @NotBlank(message = "Tanımlayıcı boş olamaz")
    @Size(min = 3, max = 100, message = "Tanımlayıcı 3-100 karakter arasında olmalıdır")
    @Pattern(regexp = "^[a-zA-Z0-9_.-]+$", message = "Tanımlayıcı sadece harf, rakam, nokta, tire ve alt çizgi içerebilir")
    private String identifier;
    
    @NotBlank(message = "Başlık boş olamaz")
    @Size(min = 3, max = 100, message = "Başlık 3-100 karakter arasında olmalıdır")
    private String title;
    
    private String content;
    
    private String type;
    
    private boolean active;
} 