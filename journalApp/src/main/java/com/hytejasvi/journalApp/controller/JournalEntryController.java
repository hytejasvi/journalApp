package com.hytejasvi.journalApp.controller;

import com.hytejasvi.journalApp.Entity.JournalEntry;
import com.hytejasvi.journalApp.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<JournalEntry> getAll() {
        return journalEntryService.getAllEntries();
    }

    @PostMapping()
    public JournalEntry creatEntry(@RequestBody JournalEntry myEntry) {
        journalEntryService.saveEntry(myEntry);
        return myEntry;
    }

    @GetMapping("/id/{journalId}")
    public Optional<JournalEntry> getEntry(@PathVariable ObjectId journalId) {
        return journalEntryService.getJournalById(journalId);
    }

    @GetMapping("/id")
    public Optional<JournalEntry> getEntry2(@RequestParam ObjectId journalId) {
        return journalEntryService.getJournalById(journalId);
    }

    @DeleteMapping("/id/{journalId}")
    public boolean deleteEntry(@PathVariable ObjectId journalId) {
        journalEntryService.deleteJournalEntry(journalId);
        return true;
    }

    @PutMapping("/id/{journalId}")
    public JournalEntry updateEntry(@PathVariable ObjectId journalId, @RequestBody JournalEntry updateEntry)
            throws Exception {
        return journalEntryService.updateJournalForId(journalId, updateEntry);
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