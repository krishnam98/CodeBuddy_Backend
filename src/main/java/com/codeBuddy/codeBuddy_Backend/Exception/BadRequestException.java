package com.codeBuddy.codeBuddy_Backend.Exception;

public class BadRequestException extends RuntimeException{
    public BadRequestException(String message){
        super(message);
    }
}
