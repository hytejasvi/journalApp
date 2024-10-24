package com.hytejasvi.journalApp.scheduler;

import com.hytejasvi.journalApp.Entity.JournalEntry;
import com.hytejasvi.journalApp.Entity.User;
import com.hytejasvi.journalApp.enums.Sentiment;
import com.hytejasvi.journalApp.repository.UserRepositoryImpl;
import com.hytejasvi.journalApp.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class UserScheduler {

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepositoryImpl userRepository;

    @Scheduled(cron = "0 0 9 * * SUN")
    public void fetchUsersAndSendMail() {
        List<User> users = userRepository.getUserForSentimentAnalysis();
        for (User user : users) {
            List<JournalEntry> journalEntries = user.getJournalEntries();
            List<Sentiment> sentiments = journalEntries.stream().filter(x -> x.getLocalDateTime().isAfter
                    (LocalDateTime.now().minus(7, ChronoUnit.DAYS))).map(x -> x.getSentiment()).collect(Collectors.toList());
            Map<Sentiment, Integer> sentimentCount = new HashMap<>();
            for (Sentiment sentiment : sentiments) {
                if (sentiment != null) {
                    sentimentCount.put(sentiment, sentimentCount.getOrDefault(sentiment, 0)+1);
                }
            }
            Sentiment mostFrequentSentiment = null;
            int maxCount = 0;
            for (Map.Entry<Sentiment, Integer> entry: sentimentCount.entrySet()) {
                if (entry.getValue() > maxCount) {
                    maxCount = entry.getValue();
                    mostFrequentSentiment = entry.getKey();
                }
            }
            if (mostFrequentSentiment != null) {
                emailService.sendMail(user.getEmail(), "Sentiment for last 7 days", mostFrequentSentiment.toString());
            }
        }
    }
}
