# Bankacılık Uygulaması - Kredilendirme Projesi

Bu proje, Java Spring Boot kullanılarak geliştirilmiş modüler bir bankacılık uygulamasının kredilendirme modülünü içermektedir.

## Proje Yapısı

Proje, aşağıdaki modüllerden oluşmaktadır:

- **common**: Ortak kullanılan utility sınıfları, exception'lar ve sabitler
- **model**: Veri modelleri ve DTO'lar
- **repository**: Veritabanı erişim katmanı
- **service**: İş mantığı katmanı
- **api**: REST API katmanı

## Teknolojiler

- Java 17
- Spring Boot 3.2.3
- Spring Data JPA
- Spring Web
- Lombok
- Maven

## Kurulum ve Çalıştırma

1. Projeyi klonlayın:
   ```
   git clone https://github.com/username/bank-app-backend.git
   ```

2. Projeyi derleyin:
   ```
   mvn clean install
   ```

3. Uygulamayı çalıştırın:
   ```
   mvn spring-boot:run
   ```

## Lisans

Bu proje [MIT Lisansı](LICENSE) altında lisanslanmıştır. 