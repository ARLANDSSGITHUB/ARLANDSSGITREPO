package com.dss.model;

import com.dss.entity.MovieEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Review {private int reviewId;
    private Date datePosted;
    private String description;
    private int rating;
    private MovieEntity movie;
}
