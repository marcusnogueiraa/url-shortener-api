package com.marcusnogueiraa.urlshortener.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  // Permitir CORS para todos os caminhos
            .allowedOrigins("*")  // Permitir todas as origens
            .allowedMethods("*")  // Permitir todos os métodos HTTP (GET, POST, PUT, DELETE, etc.)
            .allowedHeaders("*")  // Permitir todos os cabeçalhos
            .allowCredentials(false)  // Se não for necessário enviar cookies ou credenciais
            .maxAge(3600);  // Tempo máximo para o cache CORS (1 hora)
    }
}
