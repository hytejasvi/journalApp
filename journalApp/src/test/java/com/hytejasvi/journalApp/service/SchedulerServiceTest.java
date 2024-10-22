package com.hytejasvi.journalApp.service;

import com.hytejasvi.journalApp.scheduler.UserScheduler;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SchedulerServiceTest {

    @Autowired
    private UserScheduler userScheduler;

    @Disabled("Tested for sending the mail")
    @Test
    public void testUserScheduler() {
        userScheduler.fetchUsersAndSendMail();
    }
}
