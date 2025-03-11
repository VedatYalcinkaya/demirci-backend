package com.tobeto.banking.core.data;

import com.tobeto.banking.core.entities.IEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * Tüm repository'ler için temel arayüz
 * @param <T> Entity tipi
 * @param <ID> ID tipi
 */
@NoRepositoryBean
public interface IRepository<T extends IEntity, ID> extends JpaRepository<T, ID> {
} 