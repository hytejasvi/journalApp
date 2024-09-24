package com.hytejasvi.journalApp.service;

import com.hytejasvi.journalApp.Entity.JournalEntry;
import com.hytejasvi.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component //Informing Spring, to scan this and keep it's object ready
public class JournalEntryService {

    @Autowired //for the reference of a class below, spring will automatically instantiate the object.
    //if that particular class is not marked with one of the annotations, then spring might not scan it and
    //using below reference variable will lead to NPE
    private JournalEntryRepository journalEntryRepository;

    public void saveEntry(JournalEntry journalEntry) {
        journalEntry.setDate();
        journalEntryRepository.save(journalEntry);
    }

    public List<JournalEntry> getAllEntries() {
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> getJournalById(ObjectId journalId) {
        return journalEntryRepository.findById(journalId);
    }

    //Method to delete a particular Entry by Id
    public void deleteJournalEntry(ObjectId journalId) {
        journalEntryRepository.deleteById(journalId);
    }

    //for an entry with the given Id, Updating details.
    public JournalEntry updateJournalForId(ObjectId journalId, JournalEntry newJournalEntry) throws Exception {
        JournalEntry existingEntry = getJournalById(journalId).orElse(null);
        if (existingEntry != null) {
            existingEntry.setContent((newJournalEntry.getContent() != null && !newJournalEntry.getContent().isEmpty())?
                    newJournalEntry.getContent(): existingEntry.getContent());
            existingEntry.setTitle((newJournalEntry.getTitle() != null && !newJournalEntry.getTitle().isEmpty()?
                    newJournalEntry.getTitle() : existingEntry.getTitle()));
        } else {
            throw new Exception("Id dose not exist");
        }
        journalEntryRepository.save(existingEntry);
        return existingEntry;
    }
}
