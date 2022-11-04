package com.dss.specification;

import com.dss.entity.ActorEntity;
import com.dss.entity.MovieEntity;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class MovieSpecs {
    public static Specification<MovieEntity> findByModel(MovieEntity criteria) {
        return new Specification<MovieEntity>() {

            @Override
            public Predicate toPredicate(Root<MovieEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (criteria.getMovieId() != 0) {
                    predicates.add(criteriaBuilder.equal(root.get("movieId"), criteria.getMovieId()));
                }
                if (criteria.getMovieTitle() != null) {
                    predicates.add(criteriaBuilder.equal(root.get("movieTitle"), criteria.getMovieTitle()));
                }
                if (criteria.getMovieCost() != 0) {
                    predicates.add(criteriaBuilder.equal(root.get("movieCost"), criteria.getMovieCost()));
                }
                if (criteria.getMovieYear() != 0) {
                    predicates.add(criteriaBuilder.equal(root.get("movieYear"), criteria.getMovieYear()));
                }
                if (criteria.getImage() != null) {
                    predicates.add(criteriaBuilder.equal(root.get("image"), criteria.getImage()));
                }
                //idk if set of objects can be a criteria
//                if (!criteria.getActors().isEmpty()) {
//                    predicates.add(criteriaBuilder.equal(root.get("actors").as(ActorEntity.class), criteria.getActors()));
//                }

                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }
}
