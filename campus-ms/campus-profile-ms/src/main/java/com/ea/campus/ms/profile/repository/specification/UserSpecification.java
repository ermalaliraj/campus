package com.ea.campus.ms.profile.repository.specification;

import com.ea.campus.ms.profile.model.User;
import com.ea.campus.ms.profile.queryparam.UserQueryParam;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

import static com.ea.campus.ms.profile.util.CampusUtil.addWildcard;

@Slf4j
public class UserSpecification extends BaseEntitySpecification<User, UserQueryParam> {

    public UserSpecification(UserQueryParam params) {
        super(params);
    }

    @Override
    public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        Predicate superPredicate = super.toPredicate(root, query, builder);

        List<Predicate> predicates = new ArrayList<>();
        if (superPredicate != null) {
            predicates.add(superPredicate);
        }

        if (queryParam.getSearchText() != null) {
            var referentPredicates = new ArrayList<Predicate>();
            referentPredicates.add(builder.like(builder.lower(root.get("userName")), addWildcard(queryParam.getSearchText().toLowerCase())));
            referentPredicates.add(builder.like(builder.lower(root.get("firstName")), addWildcard(queryParam.getSearchText().toLowerCase())));
            referentPredicates.add(builder.like(builder.lower(root.get("lastName")), addWildcard(queryParam.getSearchText().toLowerCase())));
            referentPredicates.add(builder.like(builder.lower(root.get("email")), addWildcard(queryParam.getSearchText().toLowerCase())));

            predicates.add(builder.or(referentPredicates.toArray(new Predicate[]{})));
        }

        if (queryParam.getEnabled() != null) {
            predicates.add(builder.equal(root.get("enabled"), queryParam.getEnabled()));
        }

        return builder.and(predicates.toArray(new Predicate[]{}));
    }

}