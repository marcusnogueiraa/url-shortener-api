package com.marcusnogueiraa.urlshortener.services;

import java.security.SecureRandom;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UrlShortenerService {

    @Value("${app.url.length}")
    private int urlLength;
    @Value("${app.url.base}")
    private String BASE;
    
    private static final SecureRandom random = new SecureRandom();

    public String generateShortUrl() {
        StringBuilder shortUrl = new StringBuilder(urlLength);
        for (int i = 0; i < urlLength; i++) {
            shortUrl.append(BASE.charAt(random.nextInt(BASE.length())));
        }
        return shortUrl.toString();
    }
}