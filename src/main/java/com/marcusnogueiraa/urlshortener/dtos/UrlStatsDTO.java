package com.marcusnogueiraa.urlshortener.dtos;

import com.marcusnogueiraa.urlshortener.entities.Url;

public record UrlStatsDTO(
    String originalUrl,
    String shortenedUrl,
    Long accessCount
) {
    public UrlStatsDTO(Url url) {
        this(url.getOriginalUrl(), url.getShortenedUrl(), url.getAccessCount());
    }
}
