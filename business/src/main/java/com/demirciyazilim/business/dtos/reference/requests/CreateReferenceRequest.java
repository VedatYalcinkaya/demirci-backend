package com.demirciyazilim.business.dtos.reference.requests;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateReferenceRequest {
    
    @NotBlank(message = "Başlık boş olamaz")
    @Size(min = 3, max = 200, message = "Başlık 3-200 karakter arasında olmalıdır")
    private String title;
    
    @NotBlank(message = "Özet boş olamaz")
    @Size(min = 10, max = 500, message = "Özet 10-500 karakter arasında olmalıdır")
    private String summary;
    
    @NotBlank(message = "Açıklama boş olamaz")
    @Size(min = 10, message = "Açıklama en az 10 karakter olmalıdır")
    private String description;
    
    @NotBlank(message = "Küçük resim URL'i boş olamaz")
    @Pattern(regexp = "^(http|https)://.*$", message = "Küçük resim URL'i geçerli bir URL olmalıdır (http:// veya https:// ile başlamalıdır)")
    private String thumbnailUrl;
    
    private String clientName;
    
    private String clientLogo;
    
    private String projectUrl;
    
    @NotNull(message = "Tamamlanma tarihi boş olamaz")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    private Date completionDate;
    
    private String technologies;
    
    private boolean active = true;
}