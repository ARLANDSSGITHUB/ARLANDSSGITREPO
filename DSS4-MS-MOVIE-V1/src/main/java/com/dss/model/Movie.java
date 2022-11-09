package com.dss.model;

import com.dss.entity.ActorEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class Movie {

    private int movieId;
    private String movieTitle;
    private int movieCost;
    private int movieYear;
    private String image;
    private Set<ActorEntity> actors = new HashSet<ActorEntity>();
}
