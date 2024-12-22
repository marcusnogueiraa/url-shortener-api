# API de Encurtamento de URLs

A API de Encurtamento de URLs é um projeto que oferece uma forma simples, eficiente e escalável de encurtar URLs, gerenciá-las e coletar estatísticas sobre o uso delas.

## Funcionalidades

- **Encurtar URLs:** Converte uma URL longa em um identificador curto e único.
- **Recuperar URL Original:** Obtém a URL original a partir de um código curto.
- **Estatísticas de URLs:** Fornece dados de uso, como o número de acessos a uma URL.
- **Excluir URLs:** Permite excluir uma URL encurtada.
- **Gerenciamento Eficiente de Acessos:** Usa Redis para cache de contadores de acessos, minimizando operações no banco de dados.

## Stack Tecnológico

- **Java:** Versão 17
- **Spring Boot:** Versão 3.3.4
- **MongoDB:** Banco de dados principal para armazenar URLs.
- **Redis:** Usado como camada de cache para gerenciamento eficiente de contadores de acesso.
- **Spring Cache:** Integrado para cachear recursos acessados frequentemente.

## Geração de URLs e Probabilidade de Colisão

O `UrlShortenerService` gera URLs curtas usando os seguintes parâmetros configuráveis:
- **Tamanho da URL:** Configurado por `app.url.length`. Padrão: `6`.
- **Base de Caracteres:** Configurado por `app.url.base`. Padrão: dígitos, letras maiúsculas e minúsculas (`0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz`).

### Probabilidade de Colisão

- O número total de URLs únicas possíveis com a configuração padrão é `62^6` (aproximadamente 56,8 bilhões).
- A probabilidade de colisão depende do número de URLs geradas:
  - Após 1 milhão de URLs: ~0,00088% de chance de colisão.
  - Após 10 milhões de URLs: ~0,088% de chance de colisão.

#### Como a API lida com colisões:
1. Verifica se o código gerado já existe no banco.
2. Gera um novo código caso uma colisão ocorra.

### Implementação do Serviço de Encurtamento:
```java
@Service
public class UrlShortenerService {

    @Value("${app.url.length}")
    private int urlLength;
    @Value("${app.url.base}")
    private String BASE;
    
    private static final SecureRandom random = new SecureRandom();

    public String generateShortUrl() {
        StringBuilder shortUrl = new StringBuilder(urlLength);
        for (int i = 0; i < urlLength; i++) {
            shortUrl.append(BASE.charAt(random.nextInt(BASE.length())));
        }
        return shortUrl.toString();
    }
}
```

## Propriedades da Aplicação

### Propriedades Customizadas:
```properties
# Configurações do Encurtador de URL
app.url.length=6
app.url.base=0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz
```

### Propriedades do Spring:
```properties
# Configurações da Aplicação
spring.application.name=urlshortener

# MongoDB
spring.data.mongodb.host=localhost
spring.data.mongodb.port=27017
spring.data.mongodb.database=url-shortener-db

# Redis
spring.data.redis.host=localhost
spring.data.redis.port=6379
spring.data.redis.timeout=6000

# Cache
spring.cache.type=redis
spring.cache.redis.time-to-live=3600000
```

## Endpoints da API

### 1. **Encurtar URL**
   - **POST** `/api/url`
   - **Exemplo de Requisição:**
     ```json
     {
       "url": "https://example.com"
     }
     ```
   - **Exemplo de Resposta:**
     ```json
     {
       "shortUrlCode": "abc123"
     }
     ```

### 2. **Recuperar URL Original**
   - **GET** `/api/url/{shortUrlCode}`
   - **Exemplo de Resposta:**
     ```json
     {
       "url": "https://example.com"
     }
     ```

### 3. **Obter Estatísticas da URL**
   - **GET** `/api/url/{shortUrlCode}/stats`
   - **Exemplo de Resposta:**
     ```json
     {
       "originalUrl": "https://example.com",
       "shortenedUrl": "abc123",
       "accessCount": 42
     }
     ```

### 4. **Excluir URL**
   - **DELETE** `/api/url/{shortUrlCode}`
   - **Resposta:**
     - Código de Status: `204 No Content`

## Tratamento de Exceções

O tratamento global de exceções foi implementado com `@ControllerAdvice`. A API retorna mensagens de erro claras nos seguintes cenários:
- **URL Não Encontrada:** Retorna `404 Not Found`.
- **Erros de Validação:** Retorna `400 Bad Request`.
- **Erros Internos:** Retorna `500 Internal Server Error`.

### Exemplo de Resposta de Erro:
```json
{
  "message": "Url abc123 not found",
  "status": 404,
  "timestamp": "2024-12-22T12:00:00",
  "endpoint": "/api/url/abc123"
}
```

## Cache

- **Buscar URL:** Cacheado com a chave `url:findUrl`.
- **Obter Estatísticas:** Cacheado com a chave `url:getUrlStats`.
- **Incrementar Contador de Acessos:** O contador de acessos é armazenado temporariamente no Redis e persistido no MongoDB quando as estatísticas da URL são consultadas.

## Melhorias Futuras

### Funcionalidades Planejadas
1. **Containerização:**
   - Dockerizar a aplicação para melhor escalabilidade e facilidade de deployment.
2. **Programação Orientada a Aspectos (AOP):**
   - Implementar preocupações transversais, como logs e segurança, usando Spring AOP.
3. **Sistema de Logs:**
   - Adicionar logs detalhados para depuração e análise.
4. **Autenticação e Autorização:**
   - Proteger a API com JWT ou OAuth2.
5. **Testes:**
   - Implementar testes unitários e de integração para maior confiabilidade do código.

---

Sinta-se à vontade para forkar, clonar e contribuir com o projeto! 🎉