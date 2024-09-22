package com.marcusnogueiraa.urlshortener.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "urls")
public class Url {
    @Id
    private String id;
    private String originalUrl;
    private String shortUrl;
    private long acessCount;

    public Url(){}
    
    public Url(String id, String originalUrl, String shortUrl, long acessCount) {
        this.id = id;
        this.originalUrl = originalUrl;
        this.shortUrl = shortUrl;
        this.acessCount = acessCount;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getOriginalUrl() {
        return originalUrl;
    }
    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }
    public String getShortUrl() {
        return shortUrl;
    }
    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }
    public long getAcessCount() {
        return acessCount;
    }
    public void setAcessCount(long acessCount) {
        this.acessCount = acessCount;
    }

}
