package com.demirciyazilim.business.rules;

import com.demirciyazilim.business.constants.Messages;
import com.demirciyazilim.core.utilities.exceptions.BusinessException;
import com.demirciyazilim.entities.Blog;
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
    
    // Diğer iş kuralları buraya eklenebilir
    // Örneğin: Aynı başlıkta blog var mı kontrolü, vb.
} 