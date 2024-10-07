package com.marcusnogueiraa.urlshortener.service;

import java.lang.StringBuilder;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marcusnogueiraa.urlshortener.dtos.OriginalUrlDTO;
import com.marcusnogueiraa.urlshortener.dtos.ShortUrlDTO;
import com.marcusnogueiraa.urlshortener.entity.Url;
import com.marcusnogueiraa.urlshortener.repository.UrlRepository;

@Service
public class UrlService {
    
    private static final String BASE62 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    

    @Autowired
    private UrlRepository urlRepository;

    public ShortUrlDTO createUrl(OriginalUrlDTO newUrlDto){
        String shortCode = newUrlDto.getUrl();

        Url url = new Url();
        url.setOriginalUrl(newUrlDto.getUrl());
        url.setShortenedUrl(shortCode);

        urlRepository.save(url);

        return new ShortUrlDTO(shortCode);

    }

    private static String generateShortCode(String originalUrl){
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(originalUrl.getBytes());

            BigInteger number = new BigInteger(1, digest);

            return toBase62(number);
        } catch (NoSuchAlgorithmException err) {
            throw new RuntimeException("MD5 Hash Algorithm not available.");
        }
    }

    private static String toBase62(BigInteger number){
        StringBuilder shortCode = new StringBuilder();
        BigInteger base = BigInteger.valueOf(62);

        while(number.compareTo(BigInteger.ZERO) == 1 && shortCode.length() < 6){
            int remainder = number.mod(base).intValue();
            shortCode.append(BASE62.charAt(remainder));
            number = number.divide(base);
        }

        while(shortCode.length() < 6){
            shortCode.append('0');
        }

        return shortCode.reverse().toString();
    }
}
