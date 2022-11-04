package com.dss.entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;


@Table(name = "MOVIE")
@Entity
@Getter
@Setter
public class MovieEntity {

    @Id
    private int movieId;
    private String movieTitle;
    private int movieCost;
    private int movieYear;
    private String image;

    @JsonIgnore
    @OneToMany(mappedBy = "movie")
    private Set<ReviewEntity> reviews = new HashSet<ReviewEntity>();


    public MovieEntity() {
    }

    public MovieEntity(int movieId, String movieTitle, int movieCost, int movieYear, String image, Set<ReviewEntity> reviews) {
        this.movieId = movieId;
        this.movieTitle = movieTitle;
        this.movieCost = movieCost;
        this.movieYear = movieYear;
        this.image = image;
        this.reviews = reviews;
    }

}
