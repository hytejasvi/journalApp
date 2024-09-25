package com.hytejasvi.journalApp.controller;

import com.hytejasvi.journalApp.Entity.JournalEntry;
import com.hytejasvi.journalApp.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/journal")
//this means all the endpoints in this class will start with /journal followed by their individual param
public class JournalEntryController {

    @Autowired
    JournalEntryService journalEntryService;

    //this will be resolved to url:port/journal/getAll
    @GetMapping("/getAll")
    public ResponseEntity<?> getAll() {
        List<JournalEntry> allEntries = journalEntryService.getAllEntries();
        if (allEntries != null && !allEntries.isEmpty()) {
            return new ResponseEntity<>(allEntries, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping()
    public ResponseEntity<JournalEntry> creatEntry(@RequestBody JournalEntry myEntry) {
        try {
            journalEntryService.saveEntry(myEntry);
            return new ResponseEntity<JournalEntry>(myEntry, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<JournalEntry>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/id/{journalId}")
    public ResponseEntity<?> getEntry(@PathVariable ObjectId journalId) {
        Optional<JournalEntry> journalEntry = journalEntryService.getJournalById(journalId);
        if (journalEntry.isPresent()) {
            return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
        }
        return new ResponseEntity<JournalEntry>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/id")
    public ResponseEntity<?> getEntry2(@RequestParam ObjectId journalId) {
        Optional<JournalEntry> journalEntry = journalEntryService.getJournalById(journalId);
        if (journalEntry.isPresent()) {
            return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/id/{journalId}")
    public ResponseEntity<JournalEntry> deleteEntry(@PathVariable ObjectId journalId) {
        journalEntryService.deleteJournalEntry(journalId);
        return new ResponseEntity<JournalEntry>(HttpStatus.OK);
    }

    @PutMapping("/id/{journalId}")
    public ResponseEntity<JournalEntry> updateEntry(@PathVariable ObjectId journalId,
                                                    @RequestBody JournalEntry updateEntry) {
        try {
            JournalEntry updatedEntry = journalEntryService.updateJournalForId(journalId, updateEntry);
            return new ResponseEntity <> (updatedEntry,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}



/*
Notes:
usual flow of data is as follows:
Ui --> Controller --> Service --> repository
@RequestBody --> tells the application that the request has some input data in the request body
@PathVariable --> Using this, we can send the input directly in teh url
    ex: get http://localhost:8090/journal/id/1 --> here, the last 1 is the pathVariable.
@RequestParam --> This means we send a key-value pair. we can send multiple key-value pairs also.
    ex: get http://localhost:8090/journal/id?journalId=1 --> here, the key is journalId and value is 1
    in the method, the variable name should be same as the one passed from UI / url.

 */