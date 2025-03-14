package com.tobeto.banking.business.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Sayfalanmış veri için genel yanıt sınıfı
 * @param <T> Sayfalanmış veri tipi
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PagedResponse<T> {
    private List<T> content;            // Sayfa içeriği
    private int pageNo;                 // Mevcut sayfa numarası (0 tabanlı)
    private int pageSize;               // Sayfa başına öğe sayısı
    private long totalElements;         // Toplam öğe sayısı
    private int totalPages;             // Toplam sayfa sayısı
    private boolean last;               // Son sayfa mı?
    private boolean first;              // İlk sayfa mı?
    private boolean empty;              // Sayfa boş mu?
    private int numberOfElements;       // Mevcut sayfadaki öğe sayısı
    private boolean hasNext;            // Sonraki sayfa var mı?
    private boolean hasPrevious;        // Önceki sayfa var mı?
} 