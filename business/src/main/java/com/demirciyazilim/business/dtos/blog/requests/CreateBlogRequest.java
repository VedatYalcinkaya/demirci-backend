package com.demirciyazilim.business.dtos.blog.requests;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateBlogRequest {
    
    @NotBlank(message = "Blog başlığı boş olamaz")
    @Size(min = 3, max = 200, message = "Blog başlığı 3-200 karakter arasında olmalıdır")
    private String title;
    
    @NotBlank(message = "Blog içeriği boş olamaz")
    @Size(min = 10, message = "Blog içeriği en az 10 karakter olmalıdır")
    private String content;
    
    @NotBlank(message = "Blog özeti boş olamaz")
    @Size(min = 10, max = 500, message = "Blog özeti 10-500 karakter arasında olmalıdır")
    private String summary;
    
    private String thumbnailUrl;
    
    @NotBlank(message = "Blog yazarı boş olamaz")
    @Size(min = 2, max = 100, message = "Blog yazarı 2-100 karakter arasında olmalıdır")
    private String author;
    
    private String tags;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    private LocalDateTime publishDate;
    
    private boolean active = true;
    
    // SEO alanları
    @Size(min = 3, max = 200, message = "Blog slug değeri 3-200 karakter arasında olmalıdır")
    private String slug;
    
    @Size(max = 100, message = "Meta başlık en fazla 100 karakter olmalıdır")
    private String metaTitle;
    
    @Size(max = 160, message = "Meta açıklama en fazla 160 karakter olmalıdır")
    private String metaDescription;
    
    @Size(max = 200, message = "Meta anahtar kelimeleri en fazla 200 karakter olmalıdır")
    private String metaKeywords;
    
    @Size(max = 255, message = "Canonical URL en fazla 255 karakter olmalıdır")
    private String canonicalUrl;
} 