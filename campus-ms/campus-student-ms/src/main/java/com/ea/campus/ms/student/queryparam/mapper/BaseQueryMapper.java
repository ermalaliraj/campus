package com.ea.campus.ms.student.queryparam.mapper;

import com.ea.campus.ms.student.queryparam.BaseQueryParam;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Date;
import java.util.Objects;

@Component
public class BaseQueryMapper<Q extends BaseQueryParam> {

    public Query toQuery(Q params) {
        Query query = new Query();
        equal(query, "id", params.getId());
        between(query, "creationDate", params.getCreationDateFrom(), params.getCreationDateTo());
        return query;
    }

    protected <T> void equal(Query query, String field, T value) {
        if (Objects.nonNull(value)) {
            query.addCriteria(Criteria.where(field).is(value));
        }
    }

    protected void isNull(Query query, String field, Boolean value) {
        if (Objects.nonNull(value)) {
            if (value) {
                query.addCriteria(Criteria.where(field).is(null));
            }
        }
    }

    protected void in(Query query, String field, Collection<String> value) {
        if (CollectionUtils.isNotEmpty(value)) {
            query.addCriteria(Criteria.where(field).in(value));
        }
    }

    protected void between(Query query, String field, Long from, Long to) {
        if (from == null && to == null) {
            return;
        }
        var criteria = Criteria.where(field);
        if (from != null) {
            criteria = criteria.gte(new Date(from));
        }
        if (to != null) {
            criteria = criteria.lte(new Date(to));
        }
        query.addCriteria(criteria);
    }

    protected void like(Query query, String field, String value) {
        if (StringUtils.isNotEmpty(value)) {
            if (value.endsWith(".*")) {
                query.addCriteria(Criteria.where(field).regex(value));
            } else {
                query.addCriteria(Criteria.where(field).regex(value+".*"));
            }

        }
    }
}
