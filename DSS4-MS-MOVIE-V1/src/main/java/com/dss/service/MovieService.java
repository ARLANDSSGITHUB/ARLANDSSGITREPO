package com.dss.service;

import com.dss.entity.ActorEntity;
import com.dss.entity.MovieEntity;

import java.util.List;
import java.util.Optional;

public interface MovieService {

    MovieEntity save(MovieEntity movieEntity);
    List<MovieEntity> findAllMovies();
    List<MovieEntity> findByModel(MovieEntity movieEntity);

    MovieEntity findById(Integer movieId);

    MovieEntity update(int movieId, MovieEntity movieEntity);

    Optional<MovieEntity> delete(int movieId);
}
