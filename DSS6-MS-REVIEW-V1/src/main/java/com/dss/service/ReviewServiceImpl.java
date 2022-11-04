package com.dss.service;

import com.dss.entity.MovieEntity;
import com.dss.entity.ReviewEntity;
import com.dss.exception.InvalidInputException;
import com.dss.exception.MovieNotFoundException;
import com.dss.exception.ReviewNotFoundException;
import com.dss.feign.MovieFeign;
import com.dss.repository.ReviewRepository;
import com.dss.specification.ReviewSpecs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private MovieFeign movieFeign;

    @Override
    public ReviewEntity save(ReviewEntity reviewEntity) {
        validate(reviewEntity);
        ReviewEntity savedReview = reviewRepository.save(reviewEntity);
        System.out.println("Review saved successfully");
        return savedReview;
    }

    @Override
    public List<ReviewEntity> findAllReviews() {
        List<ReviewEntity> reviewEntityList = reviewRepository.findAll();
        if (reviewEntityList.isEmpty()) {
            throw new ReviewNotFoundException("No review/s found, Please add reviews");
        }
        return reviewEntityList;
    }

    @Override
    public List<ReviewEntity> findByModel(ReviewEntity review) {
        //The details of movies should be fetched from the backend and displayed
        List<ReviewEntity> reviewEntityList = reviewRepository.findAll(ReviewSpecs.findByModel(review));
        if (reviewEntityList.isEmpty()) {
            //• Display proper messages in case of errors or exceptions
            throw new ReviewNotFoundException("No review/s found, Please add reviews");
        }
        return reviewEntityList;
    }


    public void validate(ReviewEntity reviewEntity) {
        if (reviewEntity.getDatePosted() == null || reviewEntity.getDescription() == null || reviewEntity.getRating() == 0 || reviewEntity.getMovie() == null) {
            //• Display proper messages in case of errors or exceptions
            throw new InvalidInputException("Please fill all details");
        }
        try {
            MovieEntity movieEntity = movieFeign.findMovieById(reviewEntity.getMovie().getMovieId());
        } catch (Exception e) {
            throw new MovieNotFoundException("Movie does not exist");
        }
    }
}
