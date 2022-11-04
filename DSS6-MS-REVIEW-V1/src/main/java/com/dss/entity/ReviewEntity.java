package com.dss.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Table(name = "REVIEW")
@Entity
@Getter
@Setter
public class ReviewEntity {

    //• The review id should be autogenerated.
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int reviewId;
    private Date datePosted;
    private String description;
    private int rating;

    @ManyToOne
    @JoinColumn(name="movie_id", referencedColumnName = "movieId")
    private MovieEntity movie;


    public ReviewEntity(String description, Date datePosted, int rating,MovieEntity movie){
        this.description = description;
        this.datePosted = datePosted;
        this.rating = rating;
        this.movie = movie;
    }

    public ReviewEntity(){

    }
}

