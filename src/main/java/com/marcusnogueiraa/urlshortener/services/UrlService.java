package com.marcusnogueiraa.urlshortener.services;

import java.lang.StringBuilder;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marcusnogueiraa.urlshortener.dtos.OriginalUrlDTO;
import com.marcusnogueiraa.urlshortener.dtos.ShortUrlDTO;
import com.marcusnogueiraa.urlshortener.entity.Url;
import com.marcusnogueiraa.urlshortener.repository.UrlRepository;


@Service
public class UrlService {

    @Autowired
    private UrlRepository urlRepository;

    @Autowired
    private UrlShortenerService urlShortenerService;

    public ShortUrlDTO shortenUrl(OriginalUrlDTO newUrlDto){
        String shortCode;
        Boolean exists;
        do {
            shortCode = urlShortenerService.generateShortUrl();
            exists = urlRepository.existsByShortenedUrl(shortCode);
        } while(exists);

        Url url = new Url();
        url.setOriginalUrl(newUrlDto.getUrl());
        url.setShortenedUrl(shortCode);

        urlRepository.save(url);

        return new ShortUrlDTO(shortCode);
    }
}
