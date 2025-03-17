package com.demirciyazilim.core.utilities.file;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.demirciyazilim.core.utilities.results.DataResult;
import com.demirciyazilim.core.utilities.results.ErrorDataResult;
import com.demirciyazilim.core.utilities.results.SuccessDataResult;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Service
public class CloudinaryService {

    private final Cloudinary cloudinary;

    public CloudinaryService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    public DataResult<String> uploadFile(MultipartFile file, String folder) {
        try {
            validateFile(file);
            
            // Benzersiz bir isim oluştur
            String publicId = folder + "/" + UUID.randomUUID().toString();
            
            Map<?, ?> uploadResult = cloudinary.uploader().upload(
                file.getBytes(),
                ObjectUtils.asMap(
                    "public_id", publicId,
                    "folder", folder,
                    "resource_type", "auto"
                )
            );
            
            // Cloudinary'den dönen URL'i al
            String fileUrl = uploadResult.get("secure_url").toString();
            return new SuccessDataResult<>(fileUrl, "Dosya başarıyla yüklendi");
        } catch (IllegalArgumentException e) {
            return new ErrorDataResult<>(e.getMessage());
        } catch (IOException e) {
            return new ErrorDataResult<>("Dosya yükleme sırasında bir hata oluştu: " + e.getMessage());
        } catch (Exception e) {
            return new ErrorDataResult<>("Beklenmeyen bir hata oluştu: " + e.getMessage());
        }
    }
    
    private void validateFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Dosya boş olamaz");
        }
        
        // Dosya boyutu kontrolü (örn. 5MB)
        if (file.getSize() > 5 * 1024 * 1024) {
            throw new IllegalArgumentException("Dosya boyutu 5MB'dan büyük olamaz");
        }
        
        // Dosya türü kontrolü
        String contentType = file.getContentType();
        if (contentType == null || !(contentType.equals("image/jpeg") || 
                                    contentType.equals("image/png") || 
                                    contentType.equals("image/gif") ||
                                    contentType.equals("image/webp"))) {
            throw new IllegalArgumentException("Sadece JPEG, PNG, GIF ve WEBP formatları desteklenmektedir");
        }
    }
} 