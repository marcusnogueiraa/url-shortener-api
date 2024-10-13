package com.marcusnogueiraa.urlshortener.dtos;

import com.marcusnogueiraa.urlshortener.entity.Url;

public class StatsUrlDTO {
    private String originalUrl;
    private String shortenedUrl;
    private Long acessCount;

    public StatsUrlDTO(){}

    public StatsUrlDTO(Url url){
        this.originalUrl = url.getOriginalUrl();
        this.shortenedUrl = url.getShortenedUrl();
        this.acessCount = url.getAccessCount();
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public String getShortenedUrl() {
        return shortenedUrl;
    }

    public void setShortenedUrl(String shortenedUrl) {
        this.shortenedUrl = shortenedUrl;
    }

    public Long getAcessCount() {
        return acessCount;
    }

    public void setAcessCount(Long acessCount) {
        this.acessCount = acessCount;
    }    
}
