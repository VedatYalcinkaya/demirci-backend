package com.tobeto.banking.core.business;

import com.tobeto.banking.core.data.IRepository;
import com.tobeto.banking.core.entities.IEntity;
import com.tobeto.banking.core.exceptions.BusinessException;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * Tüm servisler için temel sınıf
 * @param <T> Entity tipi
 * @param <R> Repository tipi
 * @param <ID> ID tipi
 */
@RequiredArgsConstructor
public abstract class BaseService<T extends IEntity, R extends IRepository<T, ID>, ID> implements IService<T, ID> {
    
    protected final R repository;
    
    @Override
    public List<T> getAll() {
        return repository.findAll();
    }
    
    @Override
    public T getById(ID id) {
        return repository.findById(id)
                .orElseThrow(() -> new BusinessException("Entity not found with id: " + id));
    }
    
    @Override
    public T add(T entity) {
        return repository.save(entity);
    }
    
    @Override
    public T update(T entity) {
        return repository.save(entity);
    }
    
    @Override
    public void delete(ID id) {
        repository.deleteById(id);
    }
} 