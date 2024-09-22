package com.marcusnogueiraa.urlshortener.dtos;

public class CreateUrlDTO {
    private String url;

    public CreateUrlDTO() {}

    public String getUrl(){
        return url;
    }
    public void setUrl(String url){
        this.url = url;
    }
}
