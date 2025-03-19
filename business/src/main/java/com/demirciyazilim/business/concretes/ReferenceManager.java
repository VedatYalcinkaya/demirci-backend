package com.demirciyazilim.business.concretes;

import com.demirciyazilim.business.abstracts.ReferenceService;
import com.demirciyazilim.business.constants.Messages;
import com.demirciyazilim.business.dtos.reference.requests.CreateReferenceRequest;
import com.demirciyazilim.business.dtos.reference.requests.CreateReferenceImageRequest;
import com.demirciyazilim.business.dtos.reference.requests.UpdateReferenceRequest;
import com.demirciyazilim.business.dtos.reference.responses.ReferenceResponse;
import com.demirciyazilim.business.dtos.reference.responses.ReferenceImageResponse;
import com.demirciyazilim.business.mappers.ReferenceMapper;
import com.demirciyazilim.business.mappers.ReferenceImageMapper;
import com.demirciyazilim.business.rules.ReferenceBusinessRules;
import com.demirciyazilim.core.utilities.results.*;
import com.demirciyazilim.entities.Reference;
import com.demirciyazilim.entities.ReferenceImage;
import com.demirciyazilim.repositories.ReferenceImageRepository;
import com.demirciyazilim.repositories.ReferenceRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ReferenceManager implements ReferenceService {

    private final ReferenceRepository referenceRepository;
    private final ReferenceImageRepository referenceImageRepository;
    private final ReferenceBusinessRules referenceBusinessRules;
    private final ReferenceMapper referenceMapper;
    private final ReferenceImageMapper referenceImageMapper;

    @Override
    public DataResult<List<ReferenceResponse>> getAll() {
        List<Reference> references = referenceRepository.findAll(Sort.by(Sort.Direction.DESC, "completionDate"));
        List<ReferenceResponse> responseList = referenceMapper.toResponseList(references);
        return new SuccessDataResult<>(responseList, Messages.REFERENCES_LISTED_SUCCESSFULLY);
    }

    @Override
    public DataResult<List<ReferenceResponse>> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "completionDate"));
        Page<Reference> result = referenceRepository.findAll(pageable);
        List<ReferenceResponse> responseList = referenceMapper.toResponseList(result.getContent());
        return new SuccessDataResult<>(responseList, Messages.REFERENCES_LISTED_SUCCESSFULLY);
    }

    @Override
    public DataResult<List<ReferenceResponse>> getAllActive(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "completionDate"));
        Page<Reference> result = referenceRepository.findByIsActiveTrue(pageable);
        List<ReferenceResponse> responseList = referenceMapper.toResponseList(result.getContent());
        return new SuccessDataResult<>(responseList, Messages.REFERENCES_LISTED_SUCCESSFULLY);
    }

    @Override
    public DataResult<ReferenceResponse> getById(Long id) {
        Optional<Reference> reference = referenceRepository.findById(id);
        if (reference.isEmpty()) {
            return new ErrorDataResult<>(Messages.REFERENCE_NOT_FOUND);
        }
        ReferenceResponse response = referenceMapper.toResponse(reference.get());
        return new SuccessDataResult<>(response, Messages.REFERENCE_FETCHED_SUCCESSFULLY);
    }

    @Override
    public DataResult<ReferenceResponse> add(CreateReferenceRequest request) {
        try {
            Reference reference = referenceMapper.toEntity(request);
            Reference savedReference = referenceRepository.save(reference);
            ReferenceResponse response = referenceMapper.toResponse(savedReference);
            return new SuccessDataResult<>(response, Messages.REFERENCE_ADDED_SUCCESSFULLY);
        } catch (Exception e) {
            return new ErrorDataResult<>("Referans eklenirken bir hata oluştu: " + e.getMessage());
        }
    }

    // @Override
    // public DataResult<ReferenceResponse> update(UpdateReferenceRequest request) {
    // referenceBusinessRules.checkIfReferenceExists(request.getId());

    // Reference reference = referenceMapper.toEntity(request);
    // Reference existingReference =
    // referenceRepository.findById(request.getId()).get();
    // reference.setCreatedAt(existingReference.getCreatedAt());

    // Reference updatedReference = referenceRepository.save(reference);
    // ReferenceResponse response = referenceMapper.toResponse(updatedReference);
    // return new SuccessDataResult<>(response,
    // Messages.REFERENCE_UPDATED_SUCCESSFULLY);
    // }

    @Override
    public DataResult<ReferenceResponse> update(UpdateReferenceRequest request) {
        referenceBusinessRules.checkIfReferenceExists(request.getId());

        Reference reference = referenceMapper.toEntity(request);
        Reference existingReference = referenceRepository.findById(request.getId()).get();

        // Mevcut oluşturulma tarihini koru
        reference.setCreatedAt(existingReference.getCreatedAt());

        // Mevcut resimleri koru - bu satırı ekleyin
        reference.setImages(existingReference.getImages());

        Reference updatedReference = referenceRepository.save(reference);
        ReferenceResponse response = referenceMapper.toResponse(updatedReference);
        return new SuccessDataResult<>(response, Messages.REFERENCE_UPDATED_SUCCESSFULLY);
    }

    @Override
    @Transactional
    public Result delete(Long id) {
        referenceBusinessRules.checkIfReferenceExists(id);
        referenceImageRepository.deleteByReferenceId(id);
        referenceRepository.deleteById(id);
        return new SuccessResult(Messages.REFERENCE_DELETED_SUCCESSFULLY);
    }

    @Override
    public Result activate(Long id) {
        referenceBusinessRules.checkIfReferenceExists(id);
        Reference reference = referenceRepository.findById(id).get();
        reference.setActive(true);
        reference.setUpdatedAt(LocalDateTime.now());
        referenceRepository.save(reference);
        return new SuccessResult(Messages.REFERENCE_ACTIVATED_SUCCESSFULLY);
    }

    @Override
    public Result deactivate(Long id) {
        referenceBusinessRules.checkIfReferenceExists(id);
        Reference reference = referenceRepository.findById(id).get();
        reference.setActive(false);
        reference.setUpdatedAt(LocalDateTime.now());
        referenceRepository.save(reference);
        return new SuccessResult(Messages.REFERENCE_DEACTIVATED_SUCCESSFULLY);
    }

    @Override
    public DataResult<List<ReferenceResponse>> getLatestReferences(int count) {
        List<Reference> references = referenceRepository.findTop5ByIsActiveTrueOrderByCompletionDateDesc();
        List<ReferenceResponse> responseList = referenceMapper.toResponseList(references);
        return new SuccessDataResult<>(responseList, Messages.REFERENCES_LISTED_SUCCESSFULLY);
    }

    @Override
    public DataResult<List<ReferenceResponse>> searchByTitle(String title, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "completionDate"));
        Page<Reference> result = referenceRepository.findByTitleContainingAndIsActiveTrue(title, pageable);
        List<ReferenceResponse> responseList = referenceMapper.toResponseList(result.getContent());
        return new SuccessDataResult<>(responseList, Messages.REFERENCES_LISTED_SUCCESSFULLY);
    }

    @Override
    public DataResult<List<ReferenceResponse>> searchByTechnology(String technology, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "completionDate"));
        Page<Reference> result = referenceRepository.findByTechnologiesContainingAndIsActiveTrue(technology, pageable);
        List<ReferenceResponse> responseList = referenceMapper.toResponseList(result.getContent());
        return new SuccessDataResult<>(responseList, Messages.REFERENCES_LISTED_SUCCESSFULLY);
    }

    @Override
    public DataResult<ReferenceImageResponse> addImage(Long referenceId, CreateReferenceImageRequest request) {
        referenceBusinessRules.checkIfReferenceExists(referenceId);

        Reference reference = referenceRepository.findById(referenceId).get();
        ReferenceImage image = referenceImageMapper.toEntity(request, reference);
        ReferenceImage savedImage = referenceImageRepository.save(image);
        ReferenceImageResponse response = referenceImageMapper.toResponse(savedImage);
        return new SuccessDataResult<>(response, Messages.REFERENCE_IMAGE_ADDED_SUCCESSFULLY);
    }

    @Override
    public Result deleteImage(Long imageId) {
        referenceBusinessRules.checkIfReferenceImageExists(imageId);
        referenceImageRepository.deleteById(imageId);
        return new SuccessResult(Messages.REFERENCE_IMAGE_DELETED_SUCCESSFULLY);
    }

    @Override
    public DataResult<List<ReferenceImageResponse>> getImagesByReferenceId(Long referenceId) {
        referenceBusinessRules.checkIfReferenceExists(referenceId);
        List<ReferenceImage> images = referenceImageRepository.findByReferenceIdOrderByDisplayOrder(referenceId);
        List<ReferenceImageResponse> responseList = referenceImageMapper.toResponseList(images);
        return new SuccessDataResult<>(responseList, Messages.DATA_LISTED_SUCCESSFULLY);
    }
}