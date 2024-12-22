package com.marcusnogueiraa.urlshortener.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.marcusnogueiraa.urlshortener.dtos.ErrorResponseDTO;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UrlNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleUrlNotFound(UrlNotFoundException exc, HttpServletRequest request){
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
            exc.getMessage(),
            HttpStatus.NOT_FOUND.value(),
            request.getPathInfo());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponseDTO> handleTypeMismatch(MethodArgumentTypeMismatchException ex, HttpServletRequest request) {
        String message = String.format("Invalid value for '%s': '%s'", ex.getName(), ex.getValue());
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
            message, 
            HttpStatus.BAD_REQUEST.value(), 
            request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleException(Exception exc, HttpServletRequest request){
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
            exc.getMessage(),
            HttpStatus.INTERNAL_SERVER_ERROR.value(), 
            request.getPathInfo());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}