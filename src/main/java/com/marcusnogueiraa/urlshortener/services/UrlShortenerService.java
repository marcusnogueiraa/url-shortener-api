package com.marcusnogueiraa.urlshortener.services;

import java.security.SecureRandom;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UrlShortenerService {

    @Value("${app.url.length}")
    private int urlLength;
    private static final String BASE62 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final SecureRandom random = new SecureRandom();

    public String generateShortUrl() {
        StringBuilder shortUrl = new StringBuilder(urlLength);
        for (int i = 0; i < urlLength; i++) {
            shortUrl.append(BASE62.charAt(random.nextInt(BASE62.length())));
        }
        return shortUrl.toString();
    }
}