package com.marcusnogueiraa.urlshortener.controller;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.marcusnogueiraa.urlshortener.dtos.OriginalUrlDTO;
import com.marcusnogueiraa.urlshortener.dtos.ShortenedUrlDTO;
import com.marcusnogueiraa.urlshortener.dtos.StatsUrlDTO;
import com.marcusnogueiraa.urlshortener.exceptions.UrlNotFoundException;
import com.marcusnogueiraa.urlshortener.services.UrlService;

@RestController
@RequestMapping("/api")
public class UrlController {

    @Autowired
    private UrlService urlService;

    @GetMapping("/url/{shortCode}")
    public ResponseEntity<OriginalUrlDTO> findUrl(@PathVariable("shortCode") ShortenedUrlDTO shortCode){
        try {
            OriginalUrlDTO originalUrl = urlService.findUrl(shortCode);
            return ResponseEntity.status(HttpStatus.OK).body(originalUrl);
        } catch (UrlNotFoundException err){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/url/{shortCode}/stats")
    public ResponseEntity<StatsUrlDTO> getUrlStats(@PathVariable("shortCode") ShortenedUrlDTO shortCode){
        try {
            StatsUrlDTO urlStats = urlService.getUrlStats(shortCode);
            return ResponseEntity.status(HttpStatus.OK).body(urlStats);
        } catch (UrlNotFoundException err){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping("/url")
    public ResponseEntity<ShortenedUrlDTO> shortenUrl(@RequestBody OriginalUrlDTO originalUrl) {
        ShortenedUrlDTO shortenedUrl = urlService.shortenUrl(originalUrl);
        return ResponseEntity.status(HttpStatus.CREATED).body(shortenedUrl);
    }

}