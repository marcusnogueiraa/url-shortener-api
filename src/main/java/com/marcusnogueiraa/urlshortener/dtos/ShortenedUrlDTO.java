package com.marcusnogueiraa.urlshortener.dtos;

public class ShortenedUrlDTO {
    private String shortUrlCode;

    public ShortenedUrlDTO(){}
    
    public ShortenedUrlDTO(String shortUrlCode){
        this.shortUrlCode = shortUrlCode;
    }

    public String getShortUrlCode(){
        return shortUrlCode;
    }

    public void setShortUrlCode(String shortUrlCode){
        this.shortUrlCode = shortUrlCode;
    }
}
