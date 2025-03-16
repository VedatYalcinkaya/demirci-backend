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
public class UpdateBlogRequest {
    
    @NotNull(message = "Blog ID boş olamaz")
    private Long id;
    
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
    
    private boolean active;
} 