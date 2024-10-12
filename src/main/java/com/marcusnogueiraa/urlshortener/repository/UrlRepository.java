package com.marcusnogueiraa.urlshortener.repository;

import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.marcusnogueiraa.urlshortener.entity.Url;

public interface UrlRepository extends MongoRepository<Url, String> {
    Optional<Url> findByOriginalUrl(String OriginalUrl);
    Optional<Url> findByShortenedUrl(String shortenedUrl);
    Boolean existsByShortenedUrl(String shortenedUrl);
}
