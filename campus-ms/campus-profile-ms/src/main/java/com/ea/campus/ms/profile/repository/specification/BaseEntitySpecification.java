package com.ea.campus.ms.profile.repository.specification;

import com.ea.campus.ms.profile.model.BaseEntity;
import com.ea.campus.ms.profile.queryparam.BaseQueryParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

import static com.ea.campus.ms.profile.util.CampusUtil.parseDateTime;

@Slf4j
@AllArgsConstructor
public abstract class BaseEntitySpecification<E extends BaseEntity, Q extends BaseQueryParam> implements Specification<E> {

    protected Q queryParam;

    @Override
    public Predicate toPredicate(Root<E> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        List<Predicate> predicates = new ArrayList<>();

        if (queryParam.getId() != null) {
            predicates.add(builder.equal(root.get("id"), queryParam.getId()));
        }

        if (queryParam.getCreationDateFrom() != null) {
            predicates.add(builder.greaterThanOrEqualTo(root.get("creationDate"), parseDateTime(queryParam.getCreationDateFrom())));
        }

        if (queryParam.getCreationDateTo() != null) {
            predicates.add(builder.lessThanOrEqualTo(root.get("creationDate"), parseDateTime(queryParam.getCreationDateTo())));
        }

        return predicates.isEmpty() ? null : builder.and(predicates.toArray(new Predicate[]{}));
    }

}