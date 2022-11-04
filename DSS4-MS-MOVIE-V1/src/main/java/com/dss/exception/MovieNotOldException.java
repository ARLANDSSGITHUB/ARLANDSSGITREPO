package com.dss.exception;

public class MovieNotOldException extends RuntimeException{
    public MovieNotOldException(String message){
        super(message);
    }
}
