package com.dss.exception;

public class PhoneNumberAlreadyInUseException extends RuntimeException{
    public PhoneNumberAlreadyInUseException(String message){
        super(message);
    }
}
