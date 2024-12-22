package com.marcusnogueiraa.urlshortener.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.marcusnogueiraa.urlshortener.dtos.OriginalUrlDTO;
import com.marcusnogueiraa.urlshortener.dtos.ShortenedUrlDTO;
import com.marcusnogueiraa.urlshortener.dtos.UrlStatsDTO;
import com.marcusnogueiraa.urlshortener.entity.Url;
import com.marcusnogueiraa.urlshortener.exceptions.UrlNotFoundException;
import com.marcusnogueiraa.urlshortener.repository.UrlRepository;

@Service
public class UrlService {

    @Autowired
    private UrlRepository urlRepository;

    @Autowired
    private UrlShortenerService urlShortenerService;

    @Autowired
    private RedisService redisService;

    @Cacheable(value = "url:findUrl", key = "#shortUrlCode")
    public OriginalUrlDTO findUrl(String shortUrlCode){
        Optional<Url> searchedUrl = urlRepository.findByShortenedUrl(shortUrlCode);
        Url url = searchedUrl.orElseThrow(() -> new UrlNotFoundException(shortUrlCode));
        String originalUrl = url.getOriginalUrl();
        return new OriginalUrlDTO(originalUrl);
    }

    @Cacheable(value = "url:getUrlStats", key = "#shortUrlCode")
    public UrlStatsDTO getUrlStats(String shortUrlCode){
        Optional<Url> searchedUrl = urlRepository.findByShortenedUrl(shortUrlCode);
        Url url = searchedUrl.orElseThrow(() -> new UrlNotFoundException(shortUrlCode));
        persistAccessCount(url);
        UrlStatsDTO urlStats = new UrlStatsDTO(url);
        return urlStats;
    }

    public ShortenedUrlDTO shortenUrl(OriginalUrlDTO newUrlDto){
        String shortCode = getShortCode();

        Url url = new Url();
        url.setOriginalUrl(newUrlDto.url());
        url.setShortenedUrl(shortCode);

        urlRepository.save(url);

        return new ShortenedUrlDTO(shortCode);
    }

    @CacheEvict(value = "url:getUrlStats", key = "#shortUrlCode")
    public void incrementAccessCount(String shortUrlCode){
        redisService.incrementUrlAccessCount(shortUrlCode);
    }

    @CacheEvict(key = "#shortUrlCode")
    public void deleteShortUrl(String shortUrlCode){
        if (!urlRepository.existsByShortenedUrl(shortUrlCode)) 
            throw new UrlNotFoundException(shortUrlCode);
        urlRepository.deleteByShortenedUrl(shortUrlCode);
    }

    private void persistAccessCount(Url url){
        long accessCount = redisService.getUrlAccessCount(url.getShortenedUrl());
        redisService.clearUrlAccessCount(url.getShortenedUrl());
        url.addAccessCount(accessCount);
        urlRepository.save(url);
    }

    private String getShortCode(){
        Boolean exists;
        String shortCode;
        do {
            shortCode = urlShortenerService.generateShortUrl();
            exists = urlRepository.existsByShortenedUrl(shortCode);
        } while(exists);

        return shortCode;
    }
}
