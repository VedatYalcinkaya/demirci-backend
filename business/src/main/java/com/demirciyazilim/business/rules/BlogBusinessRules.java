package com.demirciyazilim.business.rules;

import com.demirciyazilim.business.constants.Messages;
import com.demirciyazilim.core.utilities.exceptions.BusinessException;
import com.demirciyazilim.repositories.BlogRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BlogBusinessRules {
    
    private final BlogRepository blogRepository;
    
    public void checkIfBlogExists(Long id) {
        if (!blogRepository.existsById(id)) {
            throw new BusinessException(Messages.BLOG_NOT_FOUND);
        }
    }
    
    // SEO kuralları
    public void checkIfSlugExists(String slug) {
        if (blogRepository.existsBySlug(slug)) {
            throw new BusinessException("Bu slug değeri zaten kullanılıyor: " + slug);
        }
    }
    
    public void checkIfSlugExistsForUpdate(String slug, Long id) {
        if (blogRepository.existsBySlugAndIdNot(slug, id)) {
            throw new BusinessException("Bu slug değeri zaten başka bir blog tarafından kullanılıyor: " + slug);
        }
    }
    
    // Slug oluşturma yardımcı metodu
    public String generateSlug(String title) {
        if (title == null || title.isEmpty()) {
            return "";
        }
        
        // Türkçe karakterleri değiştirme
        String slug = title.toLowerCase()
                .replace("ı", "i")
                .replace("ğ", "g")
                .replace("ü", "u")
                .replace("ş", "s")
                .replace("ö", "o")
                .replace("ç", "c")
                .replace("İ", "i");
        
        // Alfanumerik olmayan karakterleri tire ile değiştirme
        slug = slug.replaceAll("[^a-z0-9\\s-]", "")
                .replaceAll("\\s+", "-")
                .replaceAll("-+", "-")
                .trim();
        
        return slug;
    }
    
    // Diğer iş kuralları buraya eklenebilir
    // Örneğin: Aynı başlıkta blog var mı kontrolü, vb.
} 