package com.marcusnogueiraa.urlshortener.dtos;

public class OriginalUrlDTO {
    private String url;

    public OriginalUrlDTO() {}

    public OriginalUrlDTO(String url) {
        this.url = url;
    }

    public String getUrl(){
        return url;
    }
    public void setUrl(String url){
        this.url = url;
    }
}
