package com.marcusnogueiraa.urlshortener.dtos;

public class ShortenedUrlDTO {
    private String shortUrl;

    public ShortenedUrlDTO(){}
    
    public ShortenedUrlDTO(String shortUrl){
        this.shortUrl = shortUrl;
    }

    public String getShortenedUrl(){
        return shortUrl;
    }

    public void setShortenedUrl(String shortUrl){
        this.shortUrl = shortUrl;
    }
}
