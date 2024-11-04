package com.hytejasvi.journalApp.controller;

import com.hytejasvi.journalApp.Entity.JournalEntry;
import com.hytejasvi.journalApp.Entity.User;
import com.hytejasvi.journalApp.service.JournalEntryService;
import com.hytejasvi.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/journal")
//this means all the endpoints in this class will start with /journal followed by their individual param
public class JournalEntryController {

    @Autowired
    JournalEntryService journalEntryService;

    @Autowired
    UserService userService;

    //this will be resolved to url:port/journal/journal
    @GetMapping
    public ResponseEntity<List<JournalEntry>> getAllJournalEntriesOfUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userService.findByUserName(userName);
        System.out.println(": "+user);
        List<JournalEntry> allEntries = user.getJournalEntries();
        if (allEntries != null && !allEntries.isEmpty()) {
            return new ResponseEntity<>(allEntries, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //this will be resolved to url:port/journal/journal
    @PostMapping
    public ResponseEntity<JournalEntry> creatEntry(@RequestBody JournalEntry myEntry) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
            journalEntryService.saveEntry(myEntry, userName);
            return new ResponseEntity<JournalEntry>(myEntry, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<JournalEntry>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/id/{journalId}")
    public ResponseEntity<JournalEntry> getEntry(@PathVariable ObjectId journalId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        JournalEntry journalEntries = journalEntryService.getJournalById(username, journalId);
        if (journalEntries != null) {
            return new ResponseEntity<>(journalEntries, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/id/{journalId}")
    public ResponseEntity<JournalEntry> deleteEntry(@PathVariable ObjectId journalId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        journalEntryService.deleteJournalEntry(authentication.getName(), journalId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/id/{journalId}")
    public ResponseEntity<JournalEntry> updateEntry(@PathVariable ObjectId journalId,
                                                    @RequestBody JournalEntry updateEntry) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
            JournalEntry updatedEntry = journalEntryService.updateJournalForId(userName, journalId, updateEntry);
            return new ResponseEntity <> (updatedEntry,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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