package com.marcusnogueiraa.urlshortener.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
public class UrlRepositoryTest {

    @Autowired
    

    @Test
    public void findByOriginalUrl_shouldReturnUrl_whenOriginalUrlExists(){

    }
}
