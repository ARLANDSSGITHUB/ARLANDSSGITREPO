package com.dss.controller;

import com.dss.entity.ReviewEntity;
import com.dss.service.ReviewService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/review")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping("/addReview")
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "Server error"),
            @ApiResponse(code = 404, message = "Service not found"),
            @ApiResponse(code = 200, message = "Success")})
    @ResponseStatus(HttpStatus.OK)
    public ReviewEntity addReview(@RequestBody ReviewEntity review) {
        ReviewEntity savedReview = reviewService.save(review);
        return savedReview;
    }

    @GetMapping("/findAllReviews")
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "Server error"),
            @ApiResponse(code = 404, message = "Service not found"),
            @ApiResponse(code = 200, message = "Success")})
    @ResponseStatus(HttpStatus.OK)
    public List<ReviewEntity> findAllReviews(){
        List<ReviewEntity> reviewEntityList = null;
        reviewEntityList = reviewService.findAllReviews();
        return reviewEntityList;
    }
    @GetMapping("/findByModel")
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "Server error"),
            @ApiResponse(code = 404, message = "Service not found"),
            @ApiResponse(code = 200, message = "Success")})
    @ResponseStatus(HttpStatus.OK)
    public List<ReviewEntity> findByModel(@RequestBody ReviewEntity review){
        List<ReviewEntity> reviewEntityList = null;
        reviewEntityList = reviewService.findByModel(review);
        return reviewEntityList;
    }
}
