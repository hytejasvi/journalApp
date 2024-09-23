package com.hytejasvi.journalApp.controller;

import com.hytejasvi.journalApp.Entity.JournalEntry;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/journal")
//this means all the endpoints in this class will start with /journal followed by their individual param
public class JournalEntryController {

    private Map<Long, JournalEntry> journalEntries = new HashMap<>();

    //this will be resolved to url:port/journal/getAll
    @GetMapping("/getAll")
    public List<JournalEntry> getAll() {
        return new ArrayList<>(journalEntries.values());
    }

    @PostMapping()
    public void creatEntry(@RequestBody JournalEntry myEntry) {
        journalEntries.put(myEntry.getId(), myEntry);
    }

    @GetMapping("/id/{journalId}")
    public JournalEntry getEntry(@PathVariable long journalId) {
        return journalEntries.get(journalId);
    }

    @GetMapping("/id")
    public JournalEntry getEntry2(@RequestParam long journalId) {
        return journalEntries.get(journalId);
    }

    @DeleteMapping("/id/{journalId}")
    public JournalEntry deleteEntry(@PathVariable long journalId) {
        return journalEntries.remove(journalId);
    }

    @PutMapping("/id/{journalId}")
    public JournalEntry updateEntry(@PathVariable long journalId, @RequestBody JournalEntry updateEntry) {
        return journalEntries.put(journalId, updateEntry);
    }
}



/*
Notes:
@RequestBody --> tells the application that the request has some input data in the request body
@PathVariable --> Using this, we can send the input directly in teh url
    ex: get http://localhost:8090/journal/id/1 --> here, the last 1 is the pathVariable.
@RequestParam --> This means we send a key-value pair. we can send multiple key-value pairs also.
    ex: get http://localhost:8090/journal/id?journalId=1 --> here, the key is journalId and value is 1
    in the method, the variable name should be same as the one passed from UI / url.

 */