package com.dss.specification;

import com.dss.entity.ActorEntity;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class ActorSpecs {
    public static Specification<ActorEntity> findByModel(ActorEntity criteria) {
        return new Specification<ActorEntity>() {

            @Override
            public Predicate toPredicate(Root<ActorEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (criteria.getActorId() != 0) {
                    predicates.add(criteriaBuilder.equal(root.get("actorId"), criteria.getActorId()));
                }
                if (criteria.getFirstName() != null) {
                    predicates.add(criteriaBuilder.equal(root.get("firstName"), criteria.getFirstName()));
                }
                if (criteria.getLastName() != null) {
                    predicates.add(criteriaBuilder.equal(root.get("lastName"), criteria.getLastName()));
                }
                if (criteria.getGender() != '\0') {
                    predicates.add(criteriaBuilder.equal(root.get("gender"), criteria.getGender()));
                }
                if (criteria.getAge() != 0) {
                    predicates.add(criteriaBuilder.equal(root.get("age"), criteria.getAge()));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }
}
