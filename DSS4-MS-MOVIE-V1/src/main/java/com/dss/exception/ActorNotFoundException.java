package com.dss.exception;

public class ActorNotFoundException extends RuntimeException{
    public ActorNotFoundException(String message){
        super(message);
    }
}
