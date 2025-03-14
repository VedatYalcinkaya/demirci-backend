-- Müşteri tablosuna veri ekleme
INSERT INTO customers (id, email, phone_number, address, created_date, updated_date, is_active) 
VALUES 
(1, 'ahmet.yilmaz@example.com', '5551112233', 'Atatürk Cad. No:123 İstanbul', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true),
(2, 'ayse.demir@example.com', '5552223344', 'Cumhuriyet Mah. 1453 Sok. No:7 Ankara', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true),
(3, 'mehmet.kaya@example.com', '5553334455', 'Barbaros Bulvarı No:45 İzmir', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true),
(4, 'zeynep.celik@example.com', '5554445566', 'Bağdat Cad. No:78 İstanbul', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true),
(5, 'mustafa.sahin@example.com', '5555556677', 'Kızılay Meydanı No:12 Ankara', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true),
(6, 'info@abcholding.com', '5301112233', 'Levent Plaza Kat:15 İstanbul', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true),
(7, 'contact@xyztech.com', '5302223344', 'ODTÜ Teknokent No:45 Ankara', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true),
(8, 'info@sunlogistics.com', '5303334455', 'Ege Serbest Bölgesi No:23 İzmir', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true),
(9, 'contact@megamarket.com', '5304445566', 'Maslak Plaza Kat:10 İstanbul', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true),
(10, 'info@greenagro.com', '5305556677', 'Organize Sanayi Bölgesi No:78 Bursa', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true);

-- Bireysel müşteri tablosuna veri ekleme
INSERT INTO individual_customers (id, first_name, last_name, identity_number, birth_date, nationality, gender, marital_status, education_level, profession) 
VALUES 
(1, 'Ahmet', 'Yılmaz', '10000000146', '1985-05-15', 'TC', 'Erkek', 'Evli', 'Lisans', 'Mühendis'),
(2, 'Ayşe', 'Demir', '10000000228', '1990-10-20', 'TC', 'Kadın', 'Bekar', 'Yüksek Lisans', 'Doktor'),
(3, 'Mehmet', 'Kaya', '10000000300', '1978-03-25', 'TC', 'Erkek', 'Evli', 'Lise', 'Esnaf'),
(4, 'Zeynep', 'Çelik', '10000000499', '1995-12-10', 'TC', 'Kadın', 'Bekar', 'Lisans', 'Öğretmen'),
(5, 'Mustafa', 'Şahin', '10000000561', '1982-07-30', 'TC', 'Erkek', 'Evli', 'Doktora', 'Akademisyen');

-- Kurumsal müşteri tablosuna veri ekleme
INSERT INTO corporate_customers (id, company_name, tax_number, registration_number, foundation_year, sector, employee_count, annual_revenue, website, contact_person_name, contact_person_title) 
VALUES 
(6, 'ABC Holding A.Ş.', '1234567890', 'ABC123456', 1995, 'Finans', 500, 25000000.00, 'www.abcholding.com', 'Ali Veli', 'Genel Müdür'),
(7, 'XYZ Teknoloji Ltd. Şti.', '2345678901', 'XYZ234567', 2005, 'Bilişim', 150, 8000000.00, 'www.xyztech.com', 'Ayşe Fatma', 'İK Direktörü'),
(8, 'Sun Lojistik A.Ş.', '3456789012', 'SUN345678', 2010, 'Lojistik', 300, 15000000.00, 'www.sunlogistics.com', 'Mehmet Ali', 'Operasyon Müdürü'),
(9, 'Mega Market Zinciri A.Ş.', '4567890123', 'MEGA456789', 1998, 'Perakende', 1200, 50000000.00, 'www.megamarket.com', 'Zeynep Nur', 'Satış Direktörü'),
(10, 'Green Agro Tarım A.Ş.', '5678901234', 'GREEN567890', 2015, 'Tarım', 80, 5000000.00, 'www.greenagro.com', 'Ahmet Can', 'Genel Müdür');

-- Sequence değerlerini güncelleme
SELECT pg_catalog.setval('customers_id_seq', (SELECT MAX(id) FROM customers), true);
SELECT pg_catalog.setval('customers_id_seq', (SELECT MAX(id) FROM individual_customers), true) WHERE EXISTS (SELECT 1 FROM individual_customers);
SELECT pg_catalog.setval('customers_id_seq', (SELECT MAX(id) FROM corporate_customers), true) WHERE EXISTS (SELECT 1 FROM corporate_customers); 