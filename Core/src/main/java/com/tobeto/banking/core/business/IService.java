package com.tobeto.banking.core.business;

import com.tobeto.banking.core.entities.IEntity;

import java.util.List;

/**
 * Tüm servisler için temel arayüz
 * @param <T> Entity tipi
 * @param <ID> ID tipi
 */
public interface IService<T extends IEntity, ID> {
    
    List<T> getAll();
    
    T getById(ID id);
    
    T add(T entity);
    
    T update(T entity);
    
    void delete(ID id);
} 