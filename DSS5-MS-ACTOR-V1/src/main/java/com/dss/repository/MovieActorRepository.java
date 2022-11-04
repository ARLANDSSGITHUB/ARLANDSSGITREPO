package com.dss.repository;

import com.dss.entity.MovieActor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieActorRepository extends JpaRepository<MovieActor, Integer> {
    List<MovieActor> findByActorId(int actorId);
}
