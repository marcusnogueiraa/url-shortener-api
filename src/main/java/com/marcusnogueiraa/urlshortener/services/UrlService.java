package com.marcusnogueiraa.urlshortener.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marcusnogueiraa.urlshortener.dtos.OriginalUrlDTO;
import com.marcusnogueiraa.urlshortener.dtos.ShortenedUrlDTO;
import com.marcusnogueiraa.urlshortener.entity.Url;
import com.marcusnogueiraa.urlshortener.exceptions.UrlNotFoundException;
import com.marcusnogueiraa.urlshortener.repository.UrlRepository;

@Service
public class UrlService {

    @Autowired
    private UrlRepository urlRepository;

    @Autowired
    private UrlShortenerService urlShortenerService;

    public OriginalUrlDTO findUrl(ShortenedUrlDTO shortUrl){
        Optional<Url> url = urlRepository.findByShortenedUrl(shortUrl.getShortenedUrl());
        
        if (url.isEmpty()){
            throw new UrlNotFoundException("Url not found");
        }

        String originalUrl = url.get().getOriginalUrl();
        return new OriginalUrlDTO(originalUrl);
    }

    public ShortenedUrlDTO shortenUrl(OriginalUrlDTO newUrlDto){
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

        return new ShortenedUrlDTO(shortCode);
    }
}
