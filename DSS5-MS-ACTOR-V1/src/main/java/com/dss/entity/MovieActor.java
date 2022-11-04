package com.dss.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "movieActors")
@Entity
@Getter
@Setter
public class MovieActor {

    @Id
    private int movieId;
    private int actorId;
}
