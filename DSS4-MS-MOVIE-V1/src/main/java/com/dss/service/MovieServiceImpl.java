package com.dss.service;

import com.dss.entity.ActorEntity;
import com.dss.entity.MovieEntity;
import com.dss.exception.*;
import com.dss.feign.ActorFeign;
import com.dss.repository.MovieRepository;
import com.dss.specification.MovieSpecs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ActorFeign actorFeign;

    @Override
    public MovieEntity save(MovieEntity movieEntity) {
        validate(movieEntity);
        MovieEntity savedMovie = movieRepository.save(movieEntity);
        System.out.println("Movie saved successfully");
        return savedMovie;
    }

    @Override
    public List<MovieEntity> findAllMovies() {
        List<MovieEntity> movieEntityList = movieRepository.findAll();
        if (movieEntityList.isEmpty()) {
            throw new MovieNotFoundException("No movie/s found, Please add movies");
        }
        return movieEntityList;
    }

    @Override
    public List<MovieEntity> findByModel(MovieEntity movieEntity) {
        //The details of movies should be fetched from the backend and displayed
        List<MovieEntity> movieEntityList = movieRepository.findAll(MovieSpecs.findByModel(movieEntity));
        if (movieEntityList.isEmpty()) {
            //• Display proper messages in case of errors or exceptions
            throw new MovieNotFoundException("No movie/s found, Please add movies");
        }
        return movieEntityList;
    }

    @Override
    public MovieEntity findById(Integer movieId) {
        Optional<MovieEntity> movieEntity = movieRepository.findById(movieId);
        if (!movieEntity.isPresent()) {
            throw new MovieNotFoundException("No movie/s found, Please add movies");
        }
        return movieEntity.get();
    }

    @Override
    public MovieEntity update(int movieId, MovieEntity movieEntity) {
        MovieEntity updatedMovieEntity = null;
        Optional<MovieEntity> movieEntityOptional = movieRepository.findById(movieId);
        if (!movieEntityOptional.isPresent()) {
            throw new MovieNotFoundException("No movie/s found, Please add movies");
        }
        MovieEntity savedMovieEntity = movieEntityOptional.get();

        //• Only the image and cost can be modified.
        if (movieEntity.getMovieId() != 0 || movieEntity.getMovieTitle() != null || movieEntity.getMovieYear() != 0 || !movieEntity.getActors().isEmpty()) {
            throw new InvalidInputException("Only the image and cost can be modified");
        }
        if (movieEntity.getMovieCost() != 0) {
            savedMovieEntity.setMovieCost(movieEntity.getMovieCost());
        }
        if (movieEntity.getImage() != null) {
            savedMovieEntity.setImage(movieEntity.getImage());
        }
        List<MovieEntity> savedMovieEntity1 = movieRepository.findAll(MovieSpecs.findByModel(savedMovieEntity));
        if (!savedMovieEntity1.isEmpty()) {
            if (savedMovieEntity1.get(0).getMovieId() == movieId) {
                throw new NoChangesFoundException("No change were found");
            }
        }

        updatedMovieEntity = movieRepository.save(savedMovieEntity);
        return updatedMovieEntity;
    }

    @Override
    public Optional<MovieEntity> delete(int movieId) {
        Optional<MovieEntity> movieEntity = movieRepository.findById(movieId);
        if (!movieEntity.isPresent()) {
            throw new MovieNotFoundException("No movie/s found, Please add movies");
        }
        MovieEntity existingMovie = movieEntity.get();
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int yearsOld = currentYear - existingMovie.getMovieYear();
        if (yearsOld <= 1) {
            throw new MovieNotOldException("Only movies older than a year can be deleted");
        }
        movieRepository.delete(existingMovie);
        System.out.println("Delete Movie Successful");
        return movieEntity;
    }


    public void validate(MovieEntity movieEntity) {
        if (movieEntity.getMovieTitle() == null || movieEntity.getMovieCost() == 0 || movieEntity.getMovieYear() == 0 || movieEntity.getImage() == null || movieEntity.getActors().isEmpty()) {
            //• Display proper messages in case of errors or exceptions
            throw new InvalidInputException("Please fill all details");
        }
        for (ActorEntity actor : movieEntity.getActors()) {
            //find actor if existing using feign actor
            try {
                ActorEntity actorEntity = actorFeign.findActorById(actor.getActorId());
            } catch (Exception e) {
                throw new ActorNotFoundException("Actor " + actor.getFirstName() + " " + actor.getLastName() + " does not exist");
            }
        }
        List<MovieEntity> savedMovieEntity1 = movieRepository.findAll(MovieSpecs.findByModel(movieEntity));
        if (!savedMovieEntity1.isEmpty()) {
            //• Verify that an error message is displayed if the email id is already in use by some other user
            throw new MovieAlreadyExistException("Movie already exist");
        }
    }
}
