package com.marcusnogueiraa.urlshortener.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.lang.Throwable;

import com.marcusnogueiraa.urlshortener.dtos.OriginalUrlDTO;
import com.marcusnogueiraa.urlshortener.dtos.ShortenedUrlDTO;
import com.marcusnogueiraa.urlshortener.dtos.UrlStatsDTO;

@Aspect
@Component
public class LoggingAspect {
    
    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Around("execution(* com.marcusnogueiraa.urlshortener.services.*.*(..))")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long endTime = System.currentTimeMillis();
        logger.debug("Execution time for method {}: {}ms", joinPoint.getSignature(), endTime-startTime);
        return result;
    }

    @AfterReturning(
        pointcut = "execution(* com.marcusnogueiraa.urlshortener.services.UrlService.findUrl(..))",
        returning = "result"
    )
    public void logAfterFindUrl(OriginalUrlDTO result) {
        logger.info("Original URL fetched and saved in cache: {}", result.url());
    }

    @AfterReturning(
        pointcut = "execution(* com.marcusnogueiraa.urlshortener.services.UrlService.getUrlStats(..))",
        returning = "result"
    )
    public void logAfterFindUrlStats(UrlStatsDTO result) {
        logger.info("URL Stats fetched and saved in cache: {}", result.toString());
    }

    @AfterReturning(
        pointcut = "execution(* com.marcusnogueiraa.urlshortener.services.UrlService.shortenUrl(..))",
        returning = "result"
    )
    public void logAfterShortenUrl(ShortenedUrlDTO result) {
        logger.info("Shortened URL successfully created: {}", result.shortUrlCode());
    }

    @After("execution(* com.marcusnogueiraa.urlshortener.services.UrlService.deleteShortUrl(..))")
    public void logAfterDeleteShortUrl(JoinPoint joinPoint){
        Object[] args = joinPoint.getArgs();
        logger.info("Deleted Short Url: {}", args[0].toString());
    }

    @After("execution(* com.marcusnogueiraa.urlshortener.exceptions.GlobalExceptionHandler.handleException(..))")
    public void logExceptionFromControllerAdvice(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        logger.error("Unhandled exception: {}", args[0].toString());
    }
}
