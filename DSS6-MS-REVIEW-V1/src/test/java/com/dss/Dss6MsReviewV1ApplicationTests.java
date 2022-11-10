package com.dss;

import com.dss.controller.ReviewController;
import com.dss.entity.MovieEntity;
import com.dss.entity.ReviewEntity;
import com.dss.exception.InvalidInputException;
import com.dss.exception.MovieNotFoundException;
import com.dss.exception.ReviewNotFoundException;
import com.dss.feign.MovieFeign;
import com.dss.model.Review;
import com.dss.repository.ReviewRepository;
import com.dss.service.ReviewService;
import com.dss.specification.ReviewSpecs;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class Dss6MsReviewV1ApplicationTests {
    @MockBean
    private ReviewRepository reviewRepository;

    @MockBean
    private MovieFeign movieFeign;

    @Autowired
    private ReviewService reviewService;

    @InjectMocks
    private ReviewController reviewController;

    @Mock
    private ReviewService reviewService2;

    @Test
    void saveReviewSuccessful() throws ParseException {
        MovieEntity movie = new MovieEntity(1
                , "Shrek"
                , 180
                , 2021
                , "Shrek.jpg"
                , null);

        String filterDate = "2022-11-28";
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = dateFormatter.parse(filterDate);

        ReviewEntity reviewEntity = new ReviewEntity("VERY NICE MOVIE", date, 4, movie);

        when(reviewRepository.save(reviewEntity)).thenReturn(reviewEntity);
        when(movieFeign.findMovieById(reviewEntity.getMovie().getMovieId())).thenReturn(movie);

        Assertions.assertEquals(reviewEntity, reviewService.save(reviewEntity));
    }

    @Test
    void saveReviewFailedNullValues() {
        MovieEntity movie = new MovieEntity(1
                , "Shrek"
                , 180
                , 2021
                , "Shrek.jpg"
                , null);

        ReviewEntity reviewEntity = new ReviewEntity(null, new Date(), 4, movie);

        Assertions.assertThrows(InvalidInputException.class, () -> reviewService.save(reviewEntity));
    }

    @Test
    void saveReviewFailedMovieNotFound() {
        MovieEntity movie = new MovieEntity(1
                , "Shrek"
                , 180
                , 2021
                , "Shrek.jpg"
                , null);

        ReviewEntity reviewEntity = new ReviewEntity("NICE", new Date(), 4, movie);
        when(reviewRepository.save(reviewEntity)).thenReturn(reviewEntity);
        when(movieFeign.findMovieById(reviewEntity.getMovie().getMovieId())).thenThrow(MovieNotFoundException.class);
        Assertions.assertThrows(MovieNotFoundException.class, () -> reviewService.save(reviewEntity));
    }

    @Test
    void findAllReviews() {
        MovieEntity movie = new MovieEntity(1
                , "Shrek"
                , 180
                , 2021
                , "Shrek.jpg"
                , null);

        ReviewEntity reviewEntity = new ReviewEntity("NICE", new Date(), 4, movie);
        List<ReviewEntity> reviewEntityList = new ArrayList<ReviewEntity>();
        reviewEntityList.add(reviewEntity);

        when(reviewRepository.findAll()).thenReturn(reviewEntityList);
        Assertions.assertEquals(reviewEntityList, reviewService.findAllReviews());
    }

    @Test
    void findAllReviewsFailed() {
        List<ReviewEntity> reviewEntityList = new ArrayList<ReviewEntity>();
        when(reviewRepository.findAll()).thenReturn(reviewEntityList);
        Assertions.assertThrows(ReviewNotFoundException.class, () -> reviewService.findAllReviews());
    }


    @Test
    void findByModel() {
        MovieEntity movie = new MovieEntity(1
                , "Shrek"
                , 180
                , 2021
                , "Shrek.jpg"
                , null);

        ReviewEntity reviewEntity = new ReviewEntity("NICE", new Date(), 4, movie);
        List<ReviewEntity> reviewEntityList = new ArrayList<ReviewEntity>();
        reviewEntityList.add(reviewEntity);

        when(reviewRepository.findAll((Specification<ReviewEntity>) any())).thenReturn(reviewEntityList);
        Assertions.assertEquals(reviewEntityList, reviewService.findByModel(reviewEntity));
    }

    @Test
    void findByModelFailed() {
        MovieEntity movie = new MovieEntity(1
                , "Shrek"
                , 180
                , 2021
                , "Shrek.jpg"
                , null);

        ReviewEntity reviewEntity = new ReviewEntity("NICE", new Date(), 4, movie);
        List<ReviewEntity> reviewEntityList = new ArrayList<ReviewEntity>();
        reviewEntityList.add(reviewEntity);
        Assertions.assertThrows(ReviewNotFoundException.class, () -> reviewService.findByModel(reviewEntity));
    }

    @Test
    void coverSetterAndReviewId() {
        ReviewEntity reviewEntity = new ReviewEntity();
        reviewEntity.setReviewId(1);
        reviewEntity.getReviewId();
    }

    @Test
    void coverSetterAndMovie() {
        MovieEntity movieEntity = new MovieEntity();
        movieEntity.setMovieTitle("Shrek");
        movieEntity.setMovieCost(180);
        movieEntity.setMovieYear(2000);
        movieEntity.setImage("Shrek.jpg");
        movieEntity.setReviews(new HashSet<>());
        movieEntity.getMovieTitle();
        movieEntity.getMovieId();
        movieEntity.getReviews();
        movieEntity.getMovieCost();
        movieEntity.getMovieYear();
        movieEntity.getImage();
    }

    @Test
    void coverSetterAndReview() throws ParseException {
        Review review = new Review();
        String filterDate = "2022-11-28";
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = dateFormatter.parse(filterDate);
        review.setDatePosted(date);
        review.setReviewId(1);
        review.setDescription("");
        review.setRating(1);
        review.setMovie(new MovieEntity());
        ReviewEntity reviewEntity = new ReviewEntity(review);
    }

    @Test
    void testSpecs() throws ParseException {
        ReviewEntity review = new ReviewEntity();
        String filterDate = "2022-11-28";
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = dateFormatter.parse(filterDate);
        review.setDatePosted(date);
        review.setReviewId(1);
        review.setDescription("");
        review.setRating(1);
        review.setMovie(new MovieEntity());
        CriteriaBuilder criteriaBuilderMock = mock(CriteriaBuilder.class);
        CriteriaQuery criteriaQueryMock = mock(CriteriaQuery.class);
        Root<ReviewEntity> personRootMock = mock(Root.class);
        ReviewSpecs.findByModel(review).toPredicate(personRootMock, criteriaQueryMock, criteriaBuilderMock);
    }

    @Test
    void testController() throws ParseException {
        Review review = new Review();
        String filterDate = "2022-11-28";
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = dateFormatter.parse(filterDate);
        review.setDatePosted(date);
        review.setReviewId(1);
        review.setDescription("");
        review.setRating(1);
        review.setMovie(new MovieEntity());
        ReviewEntity reviewEntity = new ReviewEntity(review);
        when(reviewService.save(reviewEntity)).thenReturn(reviewEntity);
        reviewController.addReview(review);
        reviewController.findAllReviews();
        reviewController.findByModel(review);
    }
}
