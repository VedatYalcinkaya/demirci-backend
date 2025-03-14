package com.tobeto.banking.business.concretes;

import com.tobeto.banking.business.abstracts.CreditApplicationService;
import com.tobeto.banking.business.dtos.requests.CreateCreditApplicationRequest;
import com.tobeto.banking.business.dtos.requests.GetCreditApplicationRequest;
import com.tobeto.banking.business.dtos.requests.UpdateCreditApplicationRequest;
import com.tobeto.banking.business.dtos.responses.CreditApplicationResponse;
import com.tobeto.banking.business.dtos.responses.CreditApprovalResponse;
import com.tobeto.banking.business.dtos.responses.CreditDocumentResponse;
import com.tobeto.banking.core.crosscuttingconcerns.exceptions.types.BusinessException;
import com.tobeto.banking.core.utilities.mapping.ModelMapperService;
import com.tobeto.banking.entities.concretes.CreditApplication;
import com.tobeto.banking.entities.concretes.CreditType;
import com.tobeto.banking.entities.concretes.Customer;
import com.tobeto.banking.repositories.abstracts.CreditApplicationRepository;
import com.tobeto.banking.repositories.abstracts.CreditTypeRepository;
import com.tobeto.banking.repositories.abstracts.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Kredi başvuruları için servis implementasyonu
 */
@Service
@AllArgsConstructor
public class CreditApplicationManager implements CreditApplicationService {
    
    private final CreditApplicationRepository creditApplicationRepository;
    private final CreditTypeRepository creditTypeRepository;
    private final CustomerRepository customerRepository;
    private final ModelMapperService modelMapperService;
    
    @Override
    public CreditApplicationResponse create(CreateCreditApplicationRequest request) {
        // Müşteri var mı kontrolü
        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new BusinessException("Belirtilen müşteri bulunamadı"));
        
        // Kredi türü var mı kontrolü
        CreditType creditType = creditTypeRepository.findById(request.getCreditTypeId())
                .orElseThrow(() -> new BusinessException("Belirtilen kredi türü bulunamadı"));
        
        // Kredi türü aktif mi kontrolü
        if (!creditType.isActive()) {
            throw new BusinessException("Belirtilen kredi türü aktif değil");
        }
        
        // Müşteri tipi uygun mu kontrolü
        if (!isCustomerTypeCompatible(customer, creditType)) {
            throw new BusinessException("Müşteri tipi bu kredi türü için uygun değil");
        }
        
        // Kredi tutarı ve vade uygun mu kontrolü
        validateAmountAndTerm(request.getAmount(), request.getTerm(), creditType);
        
        // DTO'yu entity'ye dönüştür
        CreditApplication creditApplication = modelMapperService.forRequest().map(request, CreditApplication.class);
        creditApplication.setCustomer(customer);
        creditApplication.setCreditType(creditType);
        creditApplication.setApplicationDate(LocalDateTime.now());
        creditApplication.setStatus(CreditApplication.ApplicationStatus.PENDING);
        creditApplication.setInterestRate(creditType.getInterestRate());
        
        // Aylık ödeme ve toplam ödeme hesapla
        calculatePayments(creditApplication);
        
        // Veritabanına kaydet
        CreditApplication savedCreditApplication = creditApplicationRepository.save(creditApplication);
        
        // Entity'yi DTO'ya dönüştür ve döndür
        return mapToResponse(savedCreditApplication);
    }
    
    @Override
    public CreditApplicationResponse update(UpdateCreditApplicationRequest request) {
        // Başvuru var mı kontrolü
        CreditApplication existingApplication = creditApplicationRepository.findById(request.getId())
                .orElseThrow(() -> new BusinessException("Güncellenecek kredi başvurusu bulunamadı"));
        
        // Başvuru durumu PENDING değilse güncelleme yapılamaz
        if (existingApplication.getStatus() != CreditApplication.ApplicationStatus.PENDING && 
                request.getStatus() != existingApplication.getStatus()) {
            throw new BusinessException("İşleme alınmış veya sonuçlanmış başvurular güncellenemez");
        }
        
        // Müşteri var mı kontrolü
        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new BusinessException("Belirtilen müşteri bulunamadı"));
        
        // Kredi türü var mı kontrolü
        CreditType creditType = creditTypeRepository.findById(request.getCreditTypeId())
                .orElseThrow(() -> new BusinessException("Belirtilen kredi türü bulunamadı"));
        
        // Kredi türü aktif mi kontrolü
        if (!creditType.isActive()) {
            throw new BusinessException("Belirtilen kredi türü aktif değil");
        }
        
        // Müşteri tipi uygun mu kontrolü
        if (!isCustomerTypeCompatible(customer, creditType)) {
            throw new BusinessException("Müşteri tipi bu kredi türü için uygun değil");
        }
        
        // Kredi tutarı ve vade uygun mu kontrolü
        validateAmountAndTerm(request.getAmount(), request.getTerm(), creditType);
        
        // Durum değişikliği kontrolü
        if (request.getStatus() == CreditApplication.ApplicationStatus.REJECTED && 
                (request.getRejectionReason() == null || request.getRejectionReason().trim().isEmpty())) {
            throw new BusinessException("Reddedilen başvurular için red nedeni belirtilmelidir");
        }
        
        // DTO'yu entity'ye dönüştür
        modelMapperService.forRequest().map(request, existingApplication);
        existingApplication.setCustomer(customer);
        existingApplication.setCreditType(creditType);
        existingApplication.setInterestRate(creditType.getInterestRate());
        
        // Durum değişikliği işlemleri
        if (existingApplication.getStatus() == CreditApplication.ApplicationStatus.APPROVED && 
                existingApplication.getApprovalDate() == null) {
            existingApplication.setApprovalDate(LocalDateTime.now());
        }
        
        // Aylık ödeme ve toplam ödeme hesapla
        calculatePayments(existingApplication);
        
        // Veritabanına kaydet
        CreditApplication updatedApplication = creditApplicationRepository.save(existingApplication);
        
        // Entity'yi DTO'ya dönüştür ve döndür
        return mapToResponse(updatedApplication);
    }
    
    @Override
    public CreditApplicationResponse getById(Long id) {
        CreditApplication creditApplication = creditApplicationRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Kredi başvurusu bulunamadı"));
        
        return mapToResponse(creditApplication);
    }
    
    @Override
    public List<CreditApplicationResponse> getAll() {
        List<CreditApplication> applications = creditApplicationRepository.findAll();
        return applications.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<CreditApplicationResponse> getAllByFilter(GetCreditApplicationRequest request) {
        List<CreditApplication> filteredApplications = new ArrayList<>();
        
        // Filtreleme mantığı
        if (request.getId() != null) {
            creditApplicationRepository.findById(request.getId()).ifPresent(filteredApplications::add);
        } else {
            filteredApplications = creditApplicationRepository.findAll();
            
            // Müşteri ID'sine göre filtrele
            if (request.getCustomerId() != null) {
                filteredApplications = filteredApplications.stream()
                        .filter(app -> app.getCustomer().getId().equals(request.getCustomerId()))
                        .collect(Collectors.toList());
            }
            
            // Kredi türü ID'sine göre filtrele
            if (request.getCreditTypeId() != null) {
                filteredApplications = filteredApplications.stream()
                        .filter(app -> app.getCreditType().getId().equals(request.getCreditTypeId()))
                        .collect(Collectors.toList());
            }
            
            // Duruma göre filtrele
            if (request.getStatus() != null) {
                filteredApplications = filteredApplications.stream()
                        .filter(app -> app.getStatus() == request.getStatus())
                        .collect(Collectors.toList());
            }
        }
        
        return filteredApplications.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
    
    @Override
    public Page<CreditApplicationResponse> getAllPaged(Pageable pageable) {
        Page<CreditApplication> applicationPage = creditApplicationRepository.findAll(pageable);
        return applicationPage.map(this::mapToResponse);
    }
    
    @Override
    public List<CreditApplicationResponse> getAllByCustomerId(Long customerId) {
        // Müşteri var mı kontrolü
        if (!customerRepository.existsById(customerId)) {
            throw new BusinessException("Belirtilen müşteri bulunamadı");
        }
        
        List<CreditApplication> applications = creditApplicationRepository.findByCustomerId(customerId);
        return applications.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<CreditApplicationResponse> getAllByCreditTypeId(Long creditTypeId) {
        // Kredi türü var mı kontrolü
        if (!creditTypeRepository.existsById(creditTypeId)) {
            throw new BusinessException("Belirtilen kredi türü bulunamadı");
        }
        
        List<CreditApplication> applications = creditApplicationRepository.findByCreditTypeId(creditTypeId);
        return applications.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<CreditApplicationResponse> getAllByStatus(CreditApplication.ApplicationStatus status) {
        List<CreditApplication> applications = creditApplicationRepository.findByStatus(status);
        return applications.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<CreditApplicationResponse> getAllByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        if (startDate.isAfter(endDate)) {
            throw new BusinessException("Başlangıç tarihi bitiş tarihinden sonra olamaz");
        }
        
        List<CreditApplication> applications = creditApplicationRepository.findByApplicationDateBetween(startDate, endDate);
        return applications.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
    
    @Override
    public void delete(Long id) {
        CreditApplication creditApplication = creditApplicationRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Silinecek kredi başvurusu bulunamadı"));
        
        // Onaylanmış başvurular silinemez
        if (creditApplication.getStatus() == CreditApplication.ApplicationStatus.APPROVED) {
            throw new BusinessException("Onaylanmış başvurular silinemez");
        }
        
        creditApplicationRepository.delete(creditApplication);
    }
    
    // Yardımcı metodlar
    
    private boolean isCustomerTypeCompatible(Customer customer, CreditType creditType) {
        String customerType = customer.getClass().getSimpleName();
        
        if (customerType.equals("IndividualCustomer") && 
                creditType.getCustomerType() == CreditType.CustomerType.INDIVIDUAL) {
            return true;
        }
        
        if (customerType.equals("CorporateCustomer") && 
                creditType.getCustomerType() == CreditType.CustomerType.CORPORATE) {
            return true;
        }
        
        return false;
    }
    
    private void validateAmountAndTerm(BigDecimal amount, Integer term, CreditType creditType) {
        // Tutar kontrolü
        if (amount.compareTo(creditType.getMinAmount()) < 0) {
            throw new BusinessException("Kredi tutarı minimum tutardan küçük olamaz");
        }
        
        if (amount.compareTo(creditType.getMaxAmount()) > 0) {
            throw new BusinessException("Kredi tutarı maksimum tutardan büyük olamaz");
        }
        
        // Vade kontrolü
        if (term < creditType.getMinTerm()) {
            throw new BusinessException("Vade süresi minimum vadeden küçük olamaz");
        }
        
        if (term > creditType.getMaxTerm()) {
            throw new BusinessException("Vade süresi maksimum vadeden büyük olamaz");
        }
    }
    
    private void calculatePayments(CreditApplication application) {
        BigDecimal amount = application.getAmount();
        BigDecimal monthlyInterestRate = application.getInterestRate().divide(BigDecimal.valueOf(100), 10, RoundingMode.HALF_UP)
                .divide(BigDecimal.valueOf(12), 10, RoundingMode.HALF_UP);
        int term = application.getTerm();
        
        // Aylık ödeme hesaplama formülü: P = (PV * r * (1 + r)^n) / ((1 + r)^n - 1)
        // P: Aylık ödeme, PV: Kredi tutarı, r: Aylık faiz oranı, n: Vade (ay)
        
        BigDecimal onePlusRate = BigDecimal.ONE.add(monthlyInterestRate);
        BigDecimal onePlusRatePowN = onePlusRate.pow(term);
        
        BigDecimal numerator = amount.multiply(monthlyInterestRate).multiply(onePlusRatePowN);
        BigDecimal denominator = onePlusRatePowN.subtract(BigDecimal.ONE);
        
        BigDecimal monthlyPayment;
        if (monthlyInterestRate.compareTo(BigDecimal.ZERO) == 0) {
            // Faizsiz kredi
            monthlyPayment = amount.divide(BigDecimal.valueOf(term), 2, RoundingMode.HALF_UP);
        } else {
            monthlyPayment = numerator.divide(denominator, 2, RoundingMode.HALF_UP);
        }
        
        BigDecimal totalPayment = monthlyPayment.multiply(BigDecimal.valueOf(term)).setScale(2, RoundingMode.HALF_UP);
        
        application.setMonthlyPayment(monthlyPayment);
        application.setTotalPayment(totalPayment);
    }
    
    private CreditApplicationResponse mapToResponse(CreditApplication application) {
        CreditApplicationResponse response = modelMapperService.forResponse().map(application, CreditApplicationResponse.class);
        
        // Müşteri bilgilerini ekle
        response.setCustomerEmail(application.getCustomer().getEmail());
        response.setCustomerType(application.getCustomer().getClass().getSimpleName().replace("Customer", "").toUpperCase());
        
        // Kredi türü adını ekle
        response.setCreditTypeName(application.getCreditType().getName());
        
        // Belgeleri ekle
        if (application.getDocuments() != null && !application.getDocuments().isEmpty()) {
            response.setDocuments(application.getDocuments().stream()
                    .map(doc -> modelMapperService.forResponse().map(doc, CreditDocumentResponse.class))
                    .collect(Collectors.toList()));
        }
        
        // Onay bilgisini ekle
        if (application.getApproval() != null) {
            response.setApproval(modelMapperService.forResponse().map(application.getApproval(), CreditApprovalResponse.class));
        }
        
        return response;
    }
} 