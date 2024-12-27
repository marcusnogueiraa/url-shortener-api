package com.marcusnogueiraa.urlshortener.repositories;

import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.marcusnogueiraa.urlshortener.entities.Url;

@Repository
public interface UrlRepository extends MongoRepository<Url, String> {
    Optional<Url> findByOriginalUrl(String OriginalUrl);
    Optional<Url> findByShortenedUrl(String shortenedUrl);
    Boolean existsByShortenedUrl(String shortenedUrl);
    void deleteByShortenedUrl(String shortenedUrl);    
}
