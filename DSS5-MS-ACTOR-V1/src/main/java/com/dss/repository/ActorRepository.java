package com.dss.repository;

import com.dss.entity.ActorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ActorRepository extends JpaRepository<ActorEntity, Integer>, JpaSpecificationExecutor<ActorEntity> {

    ActorEntity findByFirstNameAndLastName(String firstName, String lastName);
}
