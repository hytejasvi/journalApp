package com.hytejasvi.journalApp.service;

import com.hytejasvi.journalApp.Entity.JournalEntry;
import com.hytejasvi.journalApp.Entity.User;
import com.hytejasvi.journalApp.repository.UserRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Disabled
@SpringBootTest
public class UserServiceTests {

    @Autowired
    private UserRepository userRepository;

    @Disabled //to inform the compiler to skip this particular TC
    @Test
    public void testAdd() {
        assertNotNull(userRepository.findByUserName("Tejas"));
    }

    @ParameterizedTest//informign spring about a parameterized test method.
    @ValueSource(strings = {//can be anythig, strings or ints : based on teh parameter we want to pass
            "user1",
            "user2",
            "Tejas",
            "Tejas2"
    })
    public void testFindByUserName(String userName) {
        assertNotNull(userRepository.findByUserName(userName), "user not found: "+userName);
    }

    @ParameterizedTest
    @ArgumentsSource(JournalArgumentProvider.class)//providing our own argument source defined within our test class
    public void testSaveEntry(JournalEntry journalEntry, String userName ) {
        User user = userRepository.findByUserName(userName);
        assertNotNull(user);
        assertNotNull(journalEntry.getContent());
        assertNotNull(journalEntry.getTitle());
    }
}
