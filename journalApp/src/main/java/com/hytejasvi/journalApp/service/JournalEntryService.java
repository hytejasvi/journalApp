package com.hytejasvi.journalApp.service;

import com.hytejasvi.journalApp.Entity.JournalEntry;
import com.hytejasvi.journalApp.Entity.User;
import com.hytejasvi.journalApp.repository.JournalEntryRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component //Informing Spring, to scan this and keep it's object ready
@Slf4j
public class JournalEntryService {

    @Autowired //for the reference of a class below, spring will automatically instantiate the object.
    //if that particular class is not marked with one of the annotations, then spring might not scan it and
    //using below reference variable will lead to NPE
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;

    public void saveEntry(JournalEntry journalEntry, String userName) {
        try {
            User user = userService.findByUserName(userName);
            System.out.println("found user: "+userName);
            journalEntry.setLocalDateTime(LocalDateTime.now());
            JournalEntry savedEntry = journalEntryRepository.save(journalEntry);
            user.getJournalEntries().add(savedEntry);
            userService.saveUser(user);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public Optional<JournalEntry> getJournalById(ObjectId journalId) {
        return journalEntryRepository.findById(journalId);
    }

    //Method to delete a particular Entry by Id
    public void deleteJournalEntry(String userName, ObjectId journalId) {
        User user = userService.findByUserName(userName);
        /*List<JournalEntry> journalEntries = user.getJournalEntries();
        for (int i = 0; i < journalEntries.size(); i++) {
            if (journalEntries.get(i).getId().equals(journalId)) {
                journalEntries.remove(i); // Remove entry if the ID matches
                break; // Exit the loop once the entry is found and removed
            }
        }*/
        //lambda is a short form for above loop that checks if the journalId of the current
        // JournalEntry (represented by x) is equal to the journalId passed to the method.
        user.getJournalEntries().removeIf(x -> x.getId().equals(journalId));// delete entry from the user node
        userService.saveUser(user); // save the updated user with the entry deleted
        journalEntryRepository.deleteById(journalId); //delete the same journal in the journal db also.
    }

    //for an entry with the given Id, Updating details.
    public JournalEntry updateJournalForId(String userName, ObjectId journalId, JournalEntry newJournalEntry) throws Exception {
        JournalEntry existingEntry = getJournalById(userName, journalId);
        //JournalEntry existingEntry = getJournalById(journalId).orElse(null);
        if (existingEntry != null) {
            existingEntry.setContent((newJournalEntry.getContent() != null && !newJournalEntry.getContent().isEmpty())?
                    newJournalEntry.getContent(): existingEntry.getContent());
            existingEntry.setTitle((newJournalEntry.getTitle() != null && !newJournalEntry.getTitle().isEmpty()?
                    newJournalEntry.getTitle() : existingEntry.getTitle()));
            journalEntryRepository.save(existingEntry);
            return existingEntry;
        } else {
            log.error("unable to update journal Entry for username: {} and journalId: {}", userName, journalId);
            throw new Exception("Id dose not exist");
        }
    }

    public JournalEntry getJournalById(String userName, ObjectId journalId) {
        User user = userService.findByUserName(userName);
        if (user != null) {
            return user.getJournalEntries().stream()
                    .filter(x -> x.getId().equals(journalId))
                    .findFirst() // Return an Optional<JournalEntry>
                    .orElse(null); // If no entry found, return null
            /*List<JournalEntry> journalEntries = new ArrayList<>();
        for (JournalEntry entry : user.getJournalEntries()) {
            if (entry.getId().equals(journalId)) {
                return entry; // Return the first matching entry
            }
        }*///traditional approach to iterate over the list
        }
        return null;
    }
}
