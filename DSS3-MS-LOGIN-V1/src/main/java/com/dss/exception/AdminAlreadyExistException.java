package com.dss.exception;

public class AdminAlreadyExistException extends RuntimeException{
    public AdminAlreadyExistException(String message){
        super(message);
    }
}
