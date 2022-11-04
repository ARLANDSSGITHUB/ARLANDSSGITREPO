package com.dss.service;

import com.dss.entity.ActorEntity;
import com.dss.entity.MovieActor;
import com.dss.exception.*;
import com.dss.repository.ActorRepository;
import com.dss.repository.MovieActorRepository;
import com.dss.specification.ActorSpecs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ActorServiceImpl implements ActorService {

    @Autowired
    private ActorRepository actorRepository;
    @Autowired
    private MovieActorRepository movieActorRepository;

    @Override
    public ActorEntity save(ActorEntity actorEntity) {
        validate(actorEntity);
        ActorEntity savedActor = actorRepository.save(actorEntity);
        System.out.println("Actor saved successfully");
        return savedActor;
    }

    @Override
    public List<ActorEntity> findAllActors() {
        List<ActorEntity> actorEntityList = actorRepository.findAll();
        if (actorEntityList.isEmpty()) {
            throw new ActorNotFoundException("No actor/s found, Please add actors");
        }
        return actorEntityList;
    }

    @Override
    public List<ActorEntity> findByModel(ActorEntity actorEntity) {
        //• The details of actors should be fetched from the backend and displayed.
        List<ActorEntity> actorEntityList = actorRepository.findAll(ActorSpecs.findByModel(actorEntity));
        if (actorEntityList.isEmpty()) {
            //• Display proper messages in case of errors or exceptions
            throw new ActorNotFoundException("No actor/s found, Please add actors");
        }
        return actorEntityList;
    }

    @Override
    public ActorEntity findById(Integer actorId) {
        Optional<ActorEntity> actorEntity = actorRepository.findById(actorId);
        if (!actorEntity.isPresent()) {
            throw new ActorNotFoundException("No actor/s found, Please add actors");
        }
        return actorEntity.get();
    }

    @Override
    public ActorEntity update(int actorId, ActorEntity actorRequestModel) {

        ActorEntity savedActorEntity = null;
        Optional<ActorEntity> actorsEntityOptional = actorRepository.findById(actorId);
        if (!actorsEntityOptional.isPresent()) {
            throw new ActorNotFoundException("No actor/s found, Please add actors");
        }
        ActorEntity updateActor = actorsEntityOptional.get();
        ActorEntity searchActor = new ActorEntity(updateActor);
//        update it's values
        if (actorRequestModel.getFirstName() != null) {
            updateActor.setFirstName(actorRequestModel.getFirstName());
            searchActor.setFirstName(actorRequestModel.getFirstName());
        }
        if (actorRequestModel.getLastName() != null) {
            updateActor.setLastName(actorRequestModel.getLastName());
            searchActor.setLastName(actorRequestModel.getLastName());
        }
        if (actorRequestModel.getGender() != '\0') {
            updateActor.setGender(actorRequestModel.getGender());
            searchActor.setGender(actorRequestModel.getGender());
        }
        if (actorRequestModel.getAge() != 0) {
            updateActor.setAge(actorRequestModel.getAge());
            searchActor.setAge(actorRequestModel.getAge());
        }
        List<ActorEntity> savedActorEntity1 = actorRepository.findAll(ActorSpecs.findByModel(searchActor));
        if (!savedActorEntity1.isEmpty()) {
            if(savedActorEntity1.get(0).getActorId()==actorId){
                throw new NoChangesFoundException("No change were found");
            }
            throw new ActorAlreadyExistException("Actor already exist");
        }
        savedActorEntity = actorRepository.save(updateActor);
        return savedActorEntity;
    }

    @Override
    public Optional<ActorEntity> delete(int actorId) {
        Optional<ActorEntity> actorEntity = actorRepository.findById(actorId);
        if (!actorEntity.isPresent()) {
            throw new ActorNotFoundException("No actor/s found, Please add actors");
        }
        ActorEntity existingActor = actorEntity.get();
        List<MovieActor> movieActor = movieActorRepository.findByActorId(actorId);
        if(!movieActor.isEmpty()){
            //Actors should be deleted only if there are no movie details for the actor
            throw new ActorHasMovieException("Actors should be deleted only if there are no movie details for the actor");
        }
        actorRepository.delete(existingActor);
        System.out.println("Delete Actor Successful");
        return actorEntity;
    }

    public void validate(ActorEntity actorEntity) {
        if (actorEntity.getLastName() == null || actorEntity.getFirstName() == null || actorEntity.getGender() == '\0' || actorEntity.getAge() == 0) {
            //• Display proper messages in case of errors or exceptions
            throw new InvalidInputException("Please fill all details");
        }
        List<ActorEntity> savedActorEntity = actorRepository.findAll(ActorSpecs.findByModel(actorEntity));
        if (!savedActorEntity.isEmpty()) {
            throw new ActorAlreadyExistException("Actor already exist");
        }
    }
}
