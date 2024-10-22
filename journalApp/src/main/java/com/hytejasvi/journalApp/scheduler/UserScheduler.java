package com.hytejasvi.journalApp.scheduler;

import com.hytejasvi.journalApp.Entity.JournalEntry;
import com.hytejasvi.journalApp.Entity.User;
import com.hytejasvi.journalApp.repository.UserRepositoryImpl;
import com.hytejasvi.journalApp.service.EmailService;
import com.hytejasvi.journalApp.service.SentimentAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Component
public class UserScheduler {

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepositoryImpl userRepository;

    @Autowired
    private SentimentAnalysisService sentimentAnalysisService;

    @Scheduled(cron = "0 0 9 * * SUN")
    public void fetchUsersAndSendMail() {
        List<User> users = userRepository.getUserForSentimentAnalysis();
        for (User user : users) {
            List<JournalEntry> journalEntries = user.getJournalEntries();
            List<String> filteredEntries = journalEntries.stream().filter(x -> x.getLocalDateTime().isAfter
                    (LocalDateTime.now().minus(7, ChronoUnit.DAYS))).map(x -> x.getContent()).toList();
            String entry = String.join(" ", filteredEntries);
            String sentiment = sentimentAnalysisService.getSentiment(entry);
            emailService.sendMail(user.getEmail(), "Sentiment for last 7 days", sentiment);
        }
    }
}
