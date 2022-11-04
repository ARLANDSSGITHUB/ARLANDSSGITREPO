package com.dss.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "ACTOR")
@Entity
@Getter
@Setter
public class ActorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int actorId;
    private String firstName;
    private String lastName;
    private char gender;
    private int age;

    public ActorEntity() {

    }

    public ActorEntity(int actorId, String firstName, String lastName, char gender, int age) {
        this.actorId = actorId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.age = age;
    }

    public ActorEntity(ActorEntity actorEntity) {
        this.firstName = actorEntity.firstName;
        this.lastName = actorEntity.lastName;
        this.gender = actorEntity.gender;
        this.age = actorEntity.age;
    }
}
