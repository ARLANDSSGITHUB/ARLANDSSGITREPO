package com.dss.service;

import com.dss.entity.ActorEntity;

import java.util.List;
import java.util.Optional;

public interface ActorService {

    ActorEntity save(ActorEntity actorEntity);
    List<ActorEntity> findAllActors();
    List<ActorEntity> findByModel(ActorEntity actorEntity);
    ActorEntity findById(Integer actorId);

    ActorEntity update(int actorId, ActorEntity actorEntity);
    Optional<ActorEntity> delete(int actorId);

}
