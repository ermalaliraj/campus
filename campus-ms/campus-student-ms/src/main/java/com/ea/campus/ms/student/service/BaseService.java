package com.ea.campus.ms.student.service;

import com.ea.campus.ms.student.model.BaseEntity;
import com.ea.campus.ms.student.queryparam.BaseQueryParam;
import com.ea.campus.ms.student.queryparam.mapper.BaseQueryMapper;
import com.ea.campus.ms.student.repository.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Optional;

public abstract class BaseService<E extends BaseEntity, R extends BaseRepository<E>, Q extends BaseQueryParam, P extends BaseQueryMapper<Q>> {
    protected final R repository;
    protected final P queryMapper;

    public BaseService(R repository, P queryMapper) {
        this.repository = repository;
        this.queryMapper = queryMapper;
    }

    public Page<E> getAll(Q queryParam, Pageable page) {
        Query query = queryMapper.toQuery(queryParam);
        return repository.findByQuery(query, page);
    }

    public long count(Q queryParam) {
        Query query = queryMapper.toQuery(queryParam);
        return repository.countByQuery(query);
    }

    public Optional<E> getById(String id) {
        return repository.findById(id);
    }

    public E save(E entity) {
        repository.save(entity);
        return entity;
    }
}
