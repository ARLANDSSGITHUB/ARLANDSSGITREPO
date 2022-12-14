package com.dss.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Table(name = "ACTOR")
@Entity
@Getter
@Setter
public class ActorEntity {
    //• Actor details include first name, last name, gender and age.
    //• The actor id should be autogenerated.
    @Id
    private int actorId;
    private String firstName;
    private String lastName;
    private char gender;
    private int age;

    @JsonIgnore
    @ManyToMany(mappedBy = "actors")
    private Set<MovieEntity> movies = new HashSet<MovieEntity>();

    public ActorEntity() {

    }

    public ActorEntity(int actorId, String firstName, String lastName, char gender, int age) {
        this.actorId = actorId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.age = age;
    }

    public ActorEntity(int actorId, String firstName, String lastName, char gender, int age, Set<MovieEntity> movies) {
        this.actorId = actorId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.age = age;
        this.movies = movies;
    }

    public ActorEntity(ActorEntity actorEntity) {
        this.firstName = actorEntity.firstName;
        this.lastName = actorEntity.lastName;
        this.gender = actorEntity.gender;
        this.age = actorEntity.age;
    }
}
