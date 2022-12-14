package com.dss;

import com.dss.entity.ActorEntity;
import com.dss.entity.MovieEntity;
import com.dss.exception.*;
import com.dss.feign.ActorFeign;
import com.dss.repository.MovieRepository;
import com.dss.service.MovieService;
import com.dss.specification.MovieSpecs;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.domain.Specification;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.when;

@SpringBootTest
class Dss4MsMovieV1ApplicationTests {
    @MockBean
    private MovieRepository movieRepository;
    @MockBean
    private ActorFeign actorFeign;

    private final Class<Specification<MovieEntity>> movieSpecs = null;

    @Autowired
    private MovieService movieService;

    @Test
    void saveMovieSuccessful() {
        Set<ActorEntity> actors = new HashSet<ActorEntity>();
        ActorEntity actor = new ActorEntity(1,"James", "Reid", 'M', 24);
        actors.add(actor);
        MovieEntity movie = new MovieEntity(1
                , "Shrek"
                , 180
                ,2021
                , "Shrek.jpg"
                , actors);

        when(movieRepository.save(movie)).thenReturn(movie);
        when(actorFeign.findActorById(actor.getActorId())).thenReturn(actor);

        Assertions.assertEquals(movie, movieService.save(movie));
    }
    @Test
    void saveMovieFailedNullValues() {
        Set<ActorEntity> actors = new HashSet<ActorEntity>();
        MovieEntity movie = new MovieEntity(1
                , null
                , 180
                ,2021
                , "Shrek.jpg"
                , actors);
        Assertions.assertThrows(InvalidInputException.class, () -> movieService.save(movie));
    }
    @Test
    void saveMovieFailedActorNotFound() {
        Set<ActorEntity> actors = new HashSet<ActorEntity>();
        ActorEntity actor = new ActorEntity(1,"James", "Reid", 'M', 24);
        ActorEntity savedActor = new ActorEntity(2,"Arlan", "Antique", 'M', 23);
        actors.add(actor);
        MovieEntity movie = new MovieEntity(1
                , "Shrek"
                , 180
                ,2021
                , "Shrek.jpg"
                , actors);

        when(movieRepository.save(movie)).thenReturn(movie);
        when(actorFeign.findActorById(actor.getActorId())).thenThrow(ActorNotFoundException.class);

        Assertions.assertThrows(ActorNotFoundException.class, () -> movieService.save(movie));
    }

    @Test
    void saveMovieFailedMovieAlreadyExist() {
        Set<ActorEntity> actors = new HashSet<ActorEntity>();
        ActorEntity actor = new ActorEntity(1,"James", "Reid", 'M', 24);
        actors.add(actor);
        List<MovieEntity> movies = new ArrayList<MovieEntity>();

        MovieEntity movie = new MovieEntity(1
                , "Shrek"
                , 180
                ,2021
                , "Shrek.jpg"
                , actors);
        movies.add(movie);
        when(movieRepository.findAll((Specification<MovieEntity>) any())).thenReturn(movies);

        Assertions.assertThrows(MovieAlreadyExistException.class, () -> movieService.save(movie));
    }
    @Test
    void findAllMovies() {
        Set<ActorEntity> actors = new HashSet<ActorEntity>();
        ActorEntity actor = new ActorEntity(1,"James", "Reid", 'M', 24);
        actors.add(actor);
        List<MovieEntity> movies = new ArrayList<MovieEntity>();

        MovieEntity movie = new MovieEntity(1
                , "Shrek"
                , 180
                ,2021
                , "Shrek.jpg"
                , actors);
        movies.add(movie);
        when(movieRepository.findAll()).thenReturn(movies);
        Assertions.assertEquals(movies, movieService.findAllMovies());
    }
    @Test
    void findAllMoviesFailed() {
        List<MovieEntity> movies = new ArrayList<MovieEntity>();
        when(movieRepository.findAll()).thenReturn(movies);
        Assertions.assertThrows(MovieNotFoundException.class, () -> movieService.findAllMovies());
    }
    @Test
    void findByModel() {
        Set<ActorEntity> actors = new HashSet<ActorEntity>();
        ActorEntity actor = new ActorEntity(1,"James", "Reid", 'M', 24);
        actors.add(actor);
        List<MovieEntity> movies = new ArrayList<MovieEntity>();

        MovieEntity movie = new MovieEntity(1
                , "Shrek"
                , 180
                ,2021
                , "Shrek.jpg"
                , actors);
        movies.add(movie);
        Mockito.when(movieRepository.findAll((Specification<MovieEntity>) any())).thenReturn(movies);
        Assertions.assertEquals(movies, movieService.findByModel(movie));
    }
    @Test
    void findByModelFailed() {
        Set<ActorEntity> actors = new HashSet<ActorEntity>();
        ActorEntity actor = new ActorEntity(1,"James", "Reid", 'M', 24);
        actors.add(actor);
        List<MovieEntity> movies = new ArrayList<MovieEntity>();

        MovieEntity movie = new MovieEntity(1
                , "Shrek"
                , 180
                ,2021
                , "Shrek.jpg"
                , actors);
        movies.add(movie);
        Assertions.assertThrows(MovieNotFoundException.class, () -> movieService.findByModel(movie));
    }

    @Test
    void updateMovieSuccessful() {
        Set<ActorEntity> actors = new HashSet<ActorEntity>();
        ActorEntity actor = new ActorEntity(1,"James", "Reid", 'M', 24);
        actors.add(actor);

        MovieEntity movie = new MovieEntity(1
                , "Shrek"
                , 180
                ,2021
                , "Shrek.jpg"
                , actors);
        MovieEntity movieForm = new MovieEntity();
        movieForm.setMovieCost(100);
        movieForm.setImage("Shrek.jpg");

        when(movieRepository.findById(movie.getMovieId())).thenReturn(Optional.of(movie));
        when(movieRepository.save(movie)).thenReturn(movie);

        Assertions.assertEquals(movie, movieService.update(movie.getMovieId(),movieForm));
    }
    @Test
    void updateMovieFailedMovieNotFound() {
        Set<ActorEntity> actors = new HashSet<ActorEntity>();
        ActorEntity actor = new ActorEntity(1,"James", "Reid", 'M', 24);
        actors.add(actor);
        MovieEntity movie = new MovieEntity(1
                , "Shrek"
                , 180
                ,2021
                , "Shrek.jpg"
                , actors);
        Assertions.assertThrows(MovieNotFoundException.class, () -> movieService.update(movie.getMovieId(),movie));
    }
    @Test
    void updateMovieFailedFieldCantUpdate() {
        Set<ActorEntity> actors = new HashSet<ActorEntity>();
        ActorEntity actor = new ActorEntity(1,"James", "Reid", 'M', 24);
        actors.add(actor);
        MovieEntity movie = new MovieEntity(1
                , "Shrek"
                , 180
                ,2021
                , "Shrek.jpg"
                , actors);

        when(movieRepository.findById(movie.getMovieId())).thenReturn(Optional.of(movie));
        Assertions.assertThrows(InvalidInputException.class, () -> movieService.update(movie.getMovieId(),movie));
    }
    @Test
    void updateMovieFailedNoChanges() {
        Set<ActorEntity> actors = new HashSet<ActorEntity>();
        ActorEntity actor = new ActorEntity(1,"James", "Reid", 'M', 24);
        actors.add(actor);
        List<MovieEntity> movies = new ArrayList<MovieEntity>();

        Set<ActorEntity> actorsForm = new HashSet<ActorEntity>();

        MovieEntity movie = new MovieEntity(1
                , "Shrek"
                , 180
                ,2021
                , "Shrek.jpg"
                , actors);
        movies.add(movie);

        MovieEntity movieForm = new MovieEntity(0
                , null
                , 180
                ,0
                , "Shrek.jpg"
                , actorsForm);
        when(movieRepository.findAll((Specification<MovieEntity>) any())).thenReturn(movies);
        when(movieRepository.findById(movie.getMovieId())).thenReturn(Optional.of(movie));
        Assertions.assertThrows(NoChangesFoundException.class, () -> movieService.update(movie.getMovieId(),movieForm));
    }

    @Test
    void deleteMovieSuccessful() {

        Set<ActorEntity> actors = new HashSet<ActorEntity>();
        ActorEntity actor = new ActorEntity(1,"James", "Reid", 'M', 24);
        actors.add(actor);
        MovieEntity movie = new MovieEntity(1
                , "Shrek"
                , 180
                ,2020
                , "Shrek.jpg"
                , actors);

        when(movieRepository.findById(movie.getMovieId())).thenReturn(Optional.of(movie));

        Assertions.assertEquals(movie, movieService.delete(movie.getMovieId()).get());
    }
    @Test
    void deleteMovieFailedMovieNotFound() {
        Assertions.assertThrows(MovieNotFoundException.class, () -> movieService.delete(1));
    }
    @Test
    void deleteMovieFailedNotOlderThanYear() {

        Set<ActorEntity> actors = new HashSet<ActorEntity>();
        ActorEntity actor = new ActorEntity(1,"James", "Reid", 'M', 24);
        actors.add(actor);
        MovieEntity movie = new MovieEntity(1
                , "Shrek"
                , 180
                ,2021
                , "Shrek.jpg"
                , actors);

        when(movieRepository.findById(movie.getMovieId())).thenReturn(Optional.of(movie));

        Assertions.assertThrows(MovieNotOldException.class, () -> movieService.delete(movie.getMovieId()));
    }

    @Test
    void coverSetterAndActor() {
        ActorEntity actorEntity = new ActorEntity();
        actorEntity.setGender('M');
        actorEntity.setAge(22);
        actorEntity.getAge();
        actorEntity.getGender();
        actorEntity.setMovies(new HashSet<>());
        actorEntity.getMovies();

        ActorEntity actorEntity1 = new ActorEntity(1,"James", "Reid", 'M', 24,new HashSet<>());
        ActorEntity actorEntity2 = new ActorEntity(actorEntity);

    }

}
