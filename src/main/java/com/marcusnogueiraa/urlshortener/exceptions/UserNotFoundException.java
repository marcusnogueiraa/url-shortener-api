package com.marcusnogueiraa.urlshortener.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(){
        super();
    }
    public UserNotFoundException(String username){
        super("User " + username + " not found");
    }
}
