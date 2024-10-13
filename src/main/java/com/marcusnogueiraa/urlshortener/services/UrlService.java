package com.marcusnogueiraa.urlshortener.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marcusnogueiraa.urlshortener.dtos.OriginalUrlDTO;
import com.marcusnogueiraa.urlshortener.dtos.ShortenedUrlDTO;
import com.marcusnogueiraa.urlshortener.dtos.StatsUrlDTO;
import com.marcusnogueiraa.urlshortener.entity.Url;
import com.marcusnogueiraa.urlshortener.exceptions.UrlNotFoundException;
import com.marcusnogueiraa.urlshortener.repository.UrlRepository;

@Service
public class UrlService {

    @Autowired
    private UrlRepository urlRepository;

    @Autowired
    private UrlShortenerService urlShortenerService;

    public OriginalUrlDTO findUrl(ShortenedUrlDTO shortenedUrl){
        Optional<Url> searchedUrl = urlRepository.findByShortenedUrl(shortenedUrl.getShortenedUrl());
        
        if (searchedUrl.isEmpty())
            throw new UrlNotFoundException("Url not found");

        Url url = searchedUrl.get();
        url.incrementAcessCount();
        urlRepository.save(url);

        String originalUrl = url.getOriginalUrl();
        return new OriginalUrlDTO(originalUrl);
    }

    public StatsUrlDTO getUrlStats(ShortenedUrlDTO shortenedUrl){
        Optional<Url> searchedUrl = urlRepository.findByShortenedUrl(shortenedUrl.getShortenedUrl());

        if (searchedUrl.isEmpty())
            throw new UrlNotFoundException("Url not found");
        
        Url url = searchedUrl.get();
        
        StatsUrlDTO statsUrl = new StatsUrlDTO(url);
        return statsUrl;
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
