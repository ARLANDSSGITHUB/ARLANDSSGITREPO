package com.dss.specification;

import com.dss.entity.ReviewEntity;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class ReviewSpecs {
    public static Specification<ReviewEntity> findByModel(ReviewEntity criteria) {
        return new Specification<ReviewEntity>() {

            @Override
            public Predicate toPredicate(Root<ReviewEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (criteria.getDatePosted() != null) {
                    predicates.add(criteriaBuilder.equal(root.get("actorId"), criteria.getDatePosted()));
                }
                if (criteria.getDescription() != null) {
                    predicates.add(criteriaBuilder.equal(root.get("firstName"), criteria.getDescription()));
                }
                if (criteria.getRating() != 0) {
                    predicates.add(criteriaBuilder.equal(root.get("age"), criteria.getRating()));
                }
                if (criteria.getMovie() != null) {
                    predicates.add(criteriaBuilder.equal(root.get("movie"), criteria.getMovie()));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }
}
