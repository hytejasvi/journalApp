package com.hytejasvi.journalApp.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserRepositoryImplTests {

    @Autowired
    private UserRepositoryImpl userRepository;

    @Test
    @Disabled("Tested")
    public void testGetUserForSentimentAnalysis() {
        Assertions.assertNotNull(userRepository.getUserForSentimentAnalysis());
    }
}
