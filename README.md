# URL Shortening API

The URL Shortening API is a project that provides a simple, efficient, and scalable way to shorten URLs, manage them, and gather statistics about their usage.

- See a README in Portuguese <a href="README-BR.md">here</a>.

## Features

- **Shorten URLs:** Converts a long URL into a short, unique identifier.
- **Retrieve Original URL:** Fetches the original URL using the shortened code.
- **URL Statistics:** Provides usage statistics such as the number of accesses for a given URL.
- **Delete URLs:** Allows users to delete a shortened URL.
- **Efficient Access Count Management:** Uses Redis to cache access counts, minimizing I/O operations on the database.

## Technology Stack

- **Java:** Version 17
- **Spring Boot:** Version 3.3.4
- **MongoDB:** Used as the primary database to store URL data.
- **Redis:** Used as a caching layer for efficient access count management.
- **Spring Cache:** Integrated for caching frequently accessed resources.

## URL Generation and Collision Probability

The `UrlShortenerService` generates short URLs using the following configurable parameters:
- **URL Length:** Configured via `app.url.length`. Default is `6`.
- **Character Base:** Configured via `app.url.base`. Default includes digits, uppercase, and lowercase letters (`0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz`).

### Collision Probability

- The total possible unique URLs for the default configuration is `62^6` (approximately 56.8 billion).
- The probability of a collision depends on the number of URLs generated:
  - After 1 million URLs: ~0.00088% chance of collision.
  - After 10 million URLs: ~0.088% chance of collision.

To handle collisions, the system:
1. Checks if the generated URL code already exists in the database.
2. Generates a new code if a collision occurs.

### URL Shortener Service Implementation:
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

## Application Properties

### Custom Properties:
```properties
# URL Shortener Properties
app.url.length=6
app.url.base=0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz
```

### Spring Properties:
```properties
# Application
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

## API Endpoints

### 1. **Shorten URL**
   - **POST** `/api/url`
   - **Request Body:**
     ```json
     {
       "url": "https://example.com"
     }
     ```
   - **Response:**
        - Status Code: `201 Created`
     ```json
     {
       "shortUrlCode": "abc123"
     }
     ```

### 2. **Retrieve Original URL**
   - **GET** `/api/url/{shortUrlCode}`
   - **Response:**
        - Status Code: `200 OK`
     ```json
     {
       "url": "https://example.com"
     }
     ```

### 3. **Get URL Statistics**
   - **GET** `/api/url/{shortUrlCode}/stats`
   - **Response:**
        - Status Code: `200 OK`
     ```json
     {
       "originalUrl": "https://example.com",
       "shortenedUrl": "abc123",
       "accessCount": 42
     }
     ```

### 4. **Delete URL**
   - **DELETE** `/api/url/{shortUrlCode}`
   - **Response:**
     * Status Code: `204 No Content`

## Exception Handling

Global exception handling is implemented using `@ControllerAdvice`. The API returns meaningful error messages in the following scenarios:
- **URL Not Found:** Returns `404 Not Found`.
- **Validation Errors:** Returns `400 Bad Request`.
- **Internal Server Errors:** Returns `500 Internal Server Error`.

### Example Error Response:
```json
{
  "message": "Url abc123 not found",
  "status": 404,
  "timestamp": "2024-12-22T12:00:00",
  "endpoint": "/api/url/abc123"
}
```

## Caching

- **Find URL:** Cached using the `url:findUrl` key.
- **Get URL Stats:** Cached using the `url:getUrlStats` key.
- **Increment Access Count:** Access counts are temporarily stored in Redis and persisted back to MongoDB when URL statistics are retrieved.

## Future Improvements

### Planned Features
1. **Containerization:**
   - Dockerize the application for better deployment and scalability.
2. **Aspect-Oriented Programming (AOP):**
   - Implement cross-cutting concerns such as logging and security using Spring AOP.
3. **Logging:**
   - Add detailed logging for debugging and analytics.
4. **Authentication & Authorization:**
   - Secure the API using JWT or OAuth2.
5. **Testing:**
   - Add unit and integration tests for higher code reliability.

---

Feel free to fork, clone, and contribute to the project! 🎉
