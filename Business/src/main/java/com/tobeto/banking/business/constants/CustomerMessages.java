package com.tobeto.banking.business.constants;

/**
 * Müşteri işlemleri için sabit mesajlar
 */
public class CustomerMessages {
    
    // Genel müşteri mesajları
    public static final String CUSTOMER_NOT_FOUND = "Müşteri bulunamadı";
    public static final String CUSTOMER_ALREADY_EXISTS = "Bu müşteri zaten mevcut";
    public static final String EMAIL_ALREADY_EXISTS = "Bu e-posta adresi zaten kullanılıyor";
    public static final String INVALID_EMAIL = "Geçersiz e-posta adresi";
    public static final String INVALID_PHONE_NUMBER = "Geçersiz telefon numarası";
    
    // Bireysel müşteri mesajları
    public static final String INDIVIDUAL_CUSTOMER_NOT_FOUND = "Bireysel müşteri bulunamadı";
    public static final String INDIVIDUAL_CUSTOMER_ALREADY_EXISTS = "Bu bireysel müşteri zaten mevcut";
    public static final String IDENTITY_NUMBER_ALREADY_EXISTS = "Bu kimlik numarası zaten kullanılıyor";
    public static final String INVALID_IDENTITY_NUMBER = "Geçersiz kimlik numarası";
    public static final String INVALID_FIRST_NAME = "Geçersiz ad";
    public static final String INVALID_LAST_NAME = "Geçersiz soyad";
    public static final String INVALID_BIRTH_DATE = "Geçersiz doğum tarihi";
    
    // Kurumsal müşteri mesajları
    public static final String CORPORATE_CUSTOMER_NOT_FOUND = "Kurumsal müşteri bulunamadı";
    public static final String CORPORATE_CUSTOMER_ALREADY_EXISTS = "Bu kurumsal müşteri zaten mevcut";
    public static final String TAX_NUMBER_ALREADY_EXISTS = "Bu vergi numarası zaten kullanılıyor";
    public static final String INVALID_TAX_NUMBER = "Geçersiz vergi numarası";
    public static final String INVALID_COMPANY_NAME = "Geçersiz şirket adı";
    public static final String INVALID_FOUNDATION_YEAR = "Geçersiz kuruluş yılı";
    
    // Adres mesajları
    public static final String ADDRESS_NOT_FOUND = "Adres bulunamadı";
    public static final String INVALID_CITY = "Geçersiz şehir";
    public static final String INVALID_DISTRICT = "Geçersiz ilçe";
    public static final String INVALID_STREET = "Geçersiz sokak";
    public static final String INVALID_POSTAL_CODE = "Geçersiz posta kodu";
    
    // Hesap mesajları
    public static final String ACCOUNT_NOT_FOUND = "Hesap bulunamadı";
    public static final String INSUFFICIENT_BALANCE = "Yetersiz bakiye";
    public static final String INVALID_AMOUNT = "Geçersiz miktar";
    
    // Kart mesajları
    public static final String CARD_NOT_FOUND = "Kart bulunamadı";
    public static final String CARD_ALREADY_EXISTS = "Bu kart zaten mevcut";
    public static final String INVALID_CARD_NUMBER = "Geçersiz kart numarası";
    public static final String INVALID_EXPIRY_DATE = "Geçersiz son kullanma tarihi";
    public static final String INVALID_CVV = "Geçersiz CVV";
    
    // İşlem mesajları
    public static final String TRANSACTION_NOT_FOUND = "İşlem bulunamadı";
    public static final String TRANSACTION_FAILED = "İşlem başarısız";
    
    private CustomerMessages() {
        // Utility sınıfı olduğu için constructor private
    }
} 