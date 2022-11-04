package com.dss.entity;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Table(name = "MOVIE")
@Entity
@Getter
@Setter
public class MovieEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int movieId;
    private String movieTitle;
    private int movieCost;
    private int movieYear;
    private String image;

    @ManyToMany
    @Fetch(FetchMode.JOIN)
    @JoinTable(
            name = "movieActors",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "actor_id")
    )
    private Set<ActorEntity> actors = new HashSet<ActorEntity>();

    public MovieEntity() {
    }

    public MovieEntity(int movieId, String movieTitle, int movieCost, int movieYear, String image, Set<ActorEntity> actors) {
        this.movieId = movieId;
        this.movieTitle = movieTitle;
        this.movieCost = movieCost;
        this.movieYear = movieYear;
        this.image = image;
        this.actors = actors;
    }
}
