package com.marcusnogueiraa.urlshortener.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "urls")
public class Url {
    @Id
    private String id;
    private String originalUrl;
    @Indexed(unique = true)
    private String shortenedUrl;
    private long accessCount;

    public Url(){
        this.accessCount = 0;
    }
   
    public Url(String id, String originalUrl, String shortenedUrl) {
        this.id = id;
        this.originalUrl = originalUrl;
        this.shortenedUrl = shortenedUrl;
        this.accessCount = 0;
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
    public String getShortenedUrl() {
        return shortenedUrl;
    }
    public void setShortenedUrl(String shortenedUrl) {
        this.shortenedUrl = shortenedUrl;
    }
    public long getAccessCount() {
        return accessCount;
    }
    public void setAccessCount(long accessCount) {
        this.accessCount = accessCount;
    }
    public void incrementAcessCount(){
        this.accessCount++;
    }
}
