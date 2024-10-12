package com.marcusnogueiraa.urlshortener.dtos;

public class ShortUrlDTO {
    private String shortUrl;

    public ShortUrlDTO(){}
    
    public ShortUrlDTO(String shortUrl){
        this.shortUrl = shortUrl;
    }

    public String getShortUrl(){
        return shortUrl;
    }

    public void setShortUrl(String shortUrl){
        this.shortUrl = shortUrl;
    }
}
