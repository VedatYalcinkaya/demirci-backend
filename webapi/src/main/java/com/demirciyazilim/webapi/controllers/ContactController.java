package com.demirciyazilim.webapi.controllers;

import com.demirciyazilim.business.dtos.contact.requests.ContactFormRequest;
import com.demirciyazilim.business.dtos.contact.requests.QuoteFormRequest;
import com.demirciyazilim.business.services.EmailService;
import com.demirciyazilim.core.utilities.results.ErrorResult;
import com.demirciyazilim.core.utilities.results.Result;
import com.demirciyazilim.core.utilities.results.SuccessResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/contact")
@AllArgsConstructor
@Tag(name = "Contact", description = "İletişim ve Teklif Formları API")
@CrossOrigin
public class ContactController {

    private final EmailService emailService;
    private static final Logger logger = LoggerFactory.getLogger(ContactController.class);

    @PostMapping("/send-message")
    @Operation(summary = "İletişim formu gönder", description = "İletişim formu verilerini e-posta olarak iletir")
    public ResponseEntity<Result> sendContactForm(@Valid @RequestBody ContactFormRequest request) {
        try {
            logger.info("İletişim formu alındı: {}", request.getFullName());
            
            String htmlContent = emailService.createContactFormEmailContent(
                request.getFullName(),
                request.getEmail(),
                request.getPhone(),
                request.getSubject(),
                request.getMessage()
            );
            
            emailService.sendContactFormEmail(request.getEmail(), "İletişim Formu: " + request.getSubject(), htmlContent);
            logger.info("İletişim formu e-postası başarıyla gönderildi");
            return ResponseEntity.ok(new SuccessResult("Mesajınız başarıyla gönderildi"));
        } catch (Exception e) {
            logger.error("İletişim formu gönderilirken hata oluştu", e);
            return ResponseEntity.badRequest().body(new ErrorResult("Mesaj gönderilirken bir hata oluştu: " + e.getMessage()));
        }
    }
    
    @PostMapping("/send-quote")
    @Operation(summary = "Teklif formu gönder", description = "Teklif formu verilerini e-posta olarak iletir")
    public ResponseEntity<Result> sendQuoteForm(@Valid @RequestBody QuoteFormRequest request) {
        try {
            logger.info("Teklif formu alındı: {}", request.getFullName());
            
            String htmlContent = emailService.createQuoteFormEmailContent(
                request.getFullName(),
                request.getEmail(),
                request.getPhone(),
                request.getService(),
                request.getMessage()
            );
            
            emailService.sendContactFormEmail(request.getEmail(), "Teklif Talebi: " + request.getService(), htmlContent);
            logger.info("Teklif formu e-postası başarıyla gönderildi");
            return ResponseEntity.ok(new SuccessResult("Teklif talebiniz başarıyla gönderildi"));
        } catch (Exception e) {
            logger.error("Teklif formu gönderilirken hata oluştu", e);
            return ResponseEntity.badRequest().body(new ErrorResult("Teklif gönderilirken bir hata oluştu: " + e.getMessage()));
        }
    }
} 