package com.dss.service;

import com.dss.entity.ReviewEntity;

import java.util.List;

public interface ReviewService {
    ReviewEntity save(ReviewEntity reviewEntity);

    List<ReviewEntity> findAllReviews();

    List<ReviewEntity> findByModel(ReviewEntity review);
}
