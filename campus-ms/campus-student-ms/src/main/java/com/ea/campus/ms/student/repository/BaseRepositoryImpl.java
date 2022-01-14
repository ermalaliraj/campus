package com.ea.campus.ms.student.repository;

import com.ea.campus.ms.student.model.BaseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.query.MongoEntityInformation;
import org.springframework.data.mongodb.repository.support.SimpleMongoRepository;

public class BaseRepositoryImpl<E extends BaseEntity> extends SimpleMongoRepository<E, String> implements BaseRepository<E> {

    private final MongoEntityInformation<E, String> mongoEntityInformation;
    private final MongoOperations mongoOperations;

    public BaseRepositoryImpl(MongoEntityInformation<E, String> mongoEntityInformation, MongoOperations mongoOperations) {
        super(mongoEntityInformation, mongoOperations);
        this.mongoEntityInformation = mongoEntityInformation;
        this.mongoOperations = mongoOperations;
    }

    @Override
    public Page<E> findByQuery(Query query, Pageable pageable) {
        var count = mongoOperations.count(query, mongoEntityInformation.getJavaType(), mongoEntityInformation.getCollectionName());
        var result = mongoOperations.find(query.with(pageable), mongoEntityInformation.getJavaType(), mongoEntityInformation.getCollectionName());
        return new PageImpl<>(result, pageable, count);
    }

    @Override
    public long countByQuery(Query query) {
        return mongoOperations.count(query, mongoEntityInformation.getJavaType(), mongoEntityInformation.getCollectionName());
    }
}
