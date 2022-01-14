package com.ea.campus.ms.student.repository;

import com.ea.campus.ms.student.model.BaseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseRepository<E extends BaseEntity> extends MongoRepository<E, String> {
    Page<E> findByQuery(Query query, Pageable pageable);

    long countByQuery(Query query);
}
