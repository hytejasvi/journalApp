package com.hytejasvi.journalApp.repository;

import com.hytejasvi.journalApp.Entity.JournalEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JournalEntryRepository extends MongoRepository<JournalEntry, ObjectId> {
//MongoRepository is an interface by springdata MongoDb,  which does standard CRUD operations.
    //as we are extending this, we can directly call the available methods from this.
}
