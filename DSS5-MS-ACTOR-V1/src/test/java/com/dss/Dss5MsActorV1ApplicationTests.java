package com.dss;

import com.dss.entity.ActorEntity;
import com.dss.entity.MovieActor;
import com.dss.exception.*;
import com.dss.repository.ActorRepository;
import com.dss.repository.MovieActorRepository;
import com.dss.service.ActorService;
import com.dss.specification.ActorSpecs;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.domain.Specification;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class Dss5MsActorV1ApplicationTests {
    @MockBean
    private ActorRepository actorRepository;
    @MockBean
    private MovieActorRepository movieActorRepository;

    @Autowired
    private ActorService actorService;

    @Test
    void saveActorSuccessful() {

        ActorEntity actor = new ActorEntity(1,"James", "Reid", 'M', 24);
        when(actorRepository.save(actor)).thenReturn(actor);
        Assertions.assertEquals(actor, actorService.save(actor));
    }

    @Test
    void saveActorFailedNullValues() {
        ActorEntity actor = new ActorEntity(1,null, "Reid", 'M', 24);
        when(actorRepository.save(actor)).thenReturn(actor);
        Assertions.assertThrows(InvalidInputException.class, () -> actorService.save(actor));
    }


    @Test
    void saveActorFailedActorAlreadyExist() {

        List<ActorEntity> actors = new ArrayList<ActorEntity>();
        ActorEntity actor = new ActorEntity(1,"James", "Reid", 'M', 24);
        actors.add(actor);
        when(actorRepository.save(actor)).thenReturn(actor);
        when(actorRepository.findAll((Specification<ActorEntity>) any())).thenReturn(actors);
        Assertions.assertThrows(ActorAlreadyExistException.class, () -> actorService.save(actor));

    }

    @Test
    void findAllActors() {
        List<ActorEntity> actors = new ArrayList<ActorEntity>();
        ActorEntity actor = new ActorEntity(1,"James", "Reid", 'M', 24);
        actors.add(actor);
        when(actorRepository.findAll()).thenReturn(actors);
        Assertions.assertEquals(actors, actorService.findAllActors());
    }

    @Test
    void findAllActorsFailed() {

        List<ActorEntity> actors = new ArrayList<ActorEntity>();
        ActorEntity actor = new ActorEntity(1,"James", "Reid", 'M', 24);
        when(actorRepository.findAll(ActorSpecs.findByModel(actor))).thenReturn(actors);
        Assertions.assertThrows(ActorNotFoundException.class, () -> actorService.findAllActors());

    }

    @Test
    void findByModel() {
        List<ActorEntity> actors = new ArrayList<ActorEntity>();
        ActorEntity actor = new ActorEntity(1,"James", "Reid", 'M', 24);
        actors.add(actor);
        when(actorRepository.findAll(ActorSpecs.findByModel(actor))).thenReturn(actors);
        Assertions.assertThrows(ActorNotFoundException.class, () -> actorService.findByModel(actor));
    }

    @Test
    void findByModelFailed() {

        List<ActorEntity> actors = new ArrayList<ActorEntity>();
        ActorEntity actor = new ActorEntity(1,"James", "Reid", 'M', 24);
        when(actorRepository.findAll(ActorSpecs.findByModel(actor))).thenReturn(actors);
        Assertions.assertThrows(ActorNotFoundException.class, () -> actorService.findByModel(actor));

    }

    @Test
    void findById() {
        ActorEntity actor = new ActorEntity(1,"James", "Reid", 'M', 24);
        when(actorRepository.findById(actor.getActorId())).thenReturn(Optional.of(actor));
        Assertions.assertEquals(actor, actorService.findById(actor.getActorId()));
    }

    @Test
    void findByIdFailed() {
        ActorEntity actor = new ActorEntity(1,"James", "Reid", 'M', 24);
        Assertions.assertThrows(ActorNotFoundException.class, () -> actorService.findById(actor.getActorId()));
    }

    @Test
    void updateActorSuccessful() {
        List<ActorEntity> actors = new ArrayList<ActorEntity>();
        ActorEntity actor = new ActorEntity(1,"James", "Reid", 'M', 24);
        ActorEntity updateActor = new ActorEntity(1,"Arlan", "Antique", 'M', 23);
        actors.add(actor);
        when(actorRepository.findById(actor.getActorId())).thenReturn(Optional.of(actor));
        when(actorRepository.findAll(ActorSpecs.findByModel(updateActor))).thenReturn(actors);
        when(actorRepository.save(actor)).thenReturn(actor);
        Assertions.assertEquals(actor, actorService.update(actor.getActorId(), actor));

    }

    @Test
    void updateActorFailedNoActorsFound() {
        ActorEntity actor = new ActorEntity(1,"James", "Reid", 'M', 24);
        Assertions.assertThrows(ActorNotFoundException.class, () -> actorService.update(actor.getActorId(), actor));

    }

    @Test
    void updateActorFailedNoChangesFound() {

        List<ActorEntity> actors = new ArrayList<ActorEntity>();
        ActorEntity actor = new ActorEntity(1,"James", "Reid", 'M', 24);
        ActorEntity updateActor = new ActorEntity(1,"Arlan", "Antique", 'M', 23);
        actors.add(actor);
        when(actorRepository.findById(actor.getActorId())).thenReturn(Optional.of(actor));
        when(actorRepository.findAll((Specification<ActorEntity>) any())).thenReturn(actors);
        when(actorRepository.save(actor)).thenReturn(actor);
        Assertions.assertThrows(NoChangesFoundException.class, () -> actorService.update(actor.getActorId(), actor));
    }

    @Test
    void updateActorFailedActorAlreadyExist() {

        List<ActorEntity> actors = new ArrayList<ActorEntity>();
        ActorEntity actor = new ActorEntity(1,"James", "Reid", 'M', 24);
        ActorEntity updateActor = new ActorEntity(2,"Arlan", "Antique", 'M', 23);
        actors.add(actor);
        when(actorRepository.findById(updateActor.getActorId())).thenReturn(Optional.of(updateActor));
        when(actorRepository.findAll((Specification<ActorEntity>) any())).thenReturn(actors);
        when(actorRepository.save(actor)).thenReturn(actor);
        Assertions.assertThrows(ActorAlreadyExistException.class, () -> actorService.update(updateActor.getActorId(), updateActor));
    }

    @Test
    void deleteActorSuccessful() {

        ActorEntity actor = new ActorEntity(1,"James", "Reid", 'M', 24);
        when(actorRepository.findById(actor.getActorId())).thenReturn(Optional.of(actor));
        Assertions.assertEquals(Optional.of(actor), actorService.delete(actor.getActorId()));

    }

    @Test
    void deleteActorFailedNoActorsFound() {

        ActorEntity actor = new ActorEntity(1,"James", "Reid", 'M', 24);
        Assertions.assertThrows(ActorNotFoundException.class, () -> actorService.delete(actor.getActorId()));

    }

    @Test
    void deleteActorFailedActorHasMovie() {

        ActorEntity actor = new ActorEntity(1,"James", "Reid", 'M', 24);
        MovieActor movieActor = new MovieActor();
        movieActor.setActorId(1);
        movieActor.setMovieId(1);
        List<MovieActor> movieActors = new ArrayList<MovieActor>();
        movieActors.add(movieActor);
        when(movieActorRepository.findByActorId(actor.getActorId())).thenReturn(movieActors);
        when(actorRepository.findById(actor.getActorId())).thenReturn(Optional.of(actor));
        Assertions.assertThrows(ActorHasMovieException.class, () -> actorService.delete(actor.getActorId()));

    }

    @Test
    void movieActorEntityTest(){
        MovieActor movieActor = new MovieActor();
        movieActor.setActorId(1);
        movieActor.setMovieId(1);
        Assertions.assertEquals(movieActor.getActorId(), movieActor.getMovieId());
    }

}
