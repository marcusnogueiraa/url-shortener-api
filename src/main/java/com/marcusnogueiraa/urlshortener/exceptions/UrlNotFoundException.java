package com.marcusnogueiraa.urlshortener.exceptions;

public class UrlNotFoundException extends RuntimeException {
    public UrlNotFoundException(){
        super();
    }
    public UrlNotFoundException(String message){
        super(message);
    }
}
