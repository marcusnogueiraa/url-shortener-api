package com.marcusnogueiraa.urlshortener.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.marcusnogueiraa.urlshortener.dtos.OriginalUrlDTO;
import com.marcusnogueiraa.urlshortener.dtos.ShortenUrlDTO;
import com.marcusnogueiraa.urlshortener.dtos.ShortenedUrlDTO;
import com.marcusnogueiraa.urlshortener.dtos.UrlStatsDTO;
import com.marcusnogueiraa.urlshortener.services.UrlService;

@RestController
@RequestMapping("/api")
public class UrlController {

    @Autowired
    private UrlService urlService;

    @GetMapping("/url/{shortUrlCode}")
    public ResponseEntity<OriginalUrlDTO> findUrl(@PathVariable String shortUrlCode){
        OriginalUrlDTO originalUrl = urlService.findUrl(shortUrlCode);
        urlService.incrementAccessCount(shortUrlCode);
        return ResponseEntity.status(HttpStatus.OK).body(originalUrl);
    }

    @GetMapping("/url/{shortUrlCode}/stats")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<UrlStatsDTO> getUrlStats(@PathVariable String shortUrlCode){
        UrlStatsDTO urlStats = urlService.getUrlStats(shortUrlCode);
        return ResponseEntity.status(HttpStatus.OK).body(urlStats);    
    }

    @PostMapping("/url")
    public ResponseEntity<ShortenedUrlDTO> shortenUrl(@RequestBody ShortenUrlDTO originalUrl) {
        ShortenedUrlDTO shortenedUrl = urlService.shortenUrl(originalUrl);
        return ResponseEntity.status(HttpStatus.CREATED).body(shortenedUrl);
    }

    @DeleteMapping("/url/{shortUrlCode}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> deleteShortUrl(@PathVariable String shortUrlCode){
        urlService.deleteShortUrl(shortUrlCode);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}