package com.dss.exception;

public class NoChangesFoundException extends RuntimeException{
    public NoChangesFoundException(String message){
        super(message);
    }
}
